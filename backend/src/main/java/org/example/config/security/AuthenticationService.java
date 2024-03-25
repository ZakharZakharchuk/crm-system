package org.example.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.data.dto.RegistrationRequest;
import org.example.data.entity.Role;
import org.example.data.entity.Trainee;
import org.example.data.entity.Trainer;
import org.example.data.entity.User;
import org.example.data.entity.token.Token;
import org.example.data.entity.token.TokenType;
import org.example.repository.TokenRepository;
import org.example.repository.TraineeRepository;
import org.example.repository.TrainerRepository;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthenticationResponse register(RegistrationRequest request) {
        User savedUser;
        if (request.getRole().equals(Role.TRAINEE)) {
            Trainee trainee = new Trainee();
            trainee.setFirstname(request.getFirstname())
                  .setLastname(request.getLastname())
                  .setEmail(request.getEmail())
                  .setPassword(passwordEncoder.encode(request.getPassword()))
                  .setRole(request.getRole());
            trainee.setFullName(userService.generateUsername(trainee));
            savedUser = traineeRepository.save(trainee);
        } else {
            Trainer trainer = new Trainer();
            trainer.setFirstname(request.getFirstname())
                  .setLastname(request.getLastname())
                  .setEmail(request.getEmail())
                  .setPassword(passwordEncoder.encode(request.getPassword()))
                  .setRole(request.getRole());
            trainer.setFullName(userService.generateUsername(trainer));
            savedUser = trainerRepository.save(trainer);
        }
        String jwtToken = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
              .accessToken(jwtToken)
              .refreshToken(refreshToken)
              .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
              )
        );
        User user = userRepository.findByEmail(request.getEmail())
              .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
              .accessToken(jwtToken)
              .refreshToken(refreshToken)
              .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
              .user(user)
              .token(jwtToken)
              .tokenType(TokenType.BEARER)
              .expired(false)
              .revoked(false)
              .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userUsername;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userUsername = jwtService.extractUsername(refreshToken);
        if (userUsername != null) {
            User user = this.userRepository.findByEmail(userUsername)
                  .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                AuthenticationResponse authResponse = AuthenticationResponse.builder()
                      .accessToken(accessToken)
                      .refreshToken(refreshToken)
                      .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
