package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.config.security.AuthenticationRequest;
import org.example.data.entity.User;
import org.example.exceptions.UserProcessingException;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    protected final static String USER_EXCEPTION = "User with such email doesn't exist";

    private final UserRepository userRepository;

    //TODO review logic
    public String generateUsername(User user) {
        String username = user.getFirstname() + " " + user.getLastname();
        long matchCount = userRepository.countByFullNameStartingWith(username);
        if (matchCount == 0) {
            return username;
        }
        if (user.getId() == null) {
            return username + matchCount;
        }
        return userRepository.findById(user.getId()).map(User::getFullName)
              .orElse(username + matchCount);
    }

    public void changePassword(AuthenticationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            userRepository.updatePasswordByEmail(request.getEmail(), request.getPassword());
        } else {
            throw new UserProcessingException(USER_EXCEPTION);
        }
    }
}
