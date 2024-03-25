package org.example.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.example.data.dto.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final String NOT_AUTHORIZED_RESP_MSG_FORMAT = "Bad credentials. Login attempt: %s";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";

    private final AuthenticationService service;
    private final LockHandler lockHandler;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
          @RequestBody RegistrationRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
          @RequestBody AuthenticationRequest request, HttpServletRequest httpServletRequest
    ) {
        String userIp = getUserIpAddress(httpServletRequest);
        lockHandler.throwExceptionWhenLocked(userIp);
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (AuthenticationException e) {
            lockHandler.lockTooMuchRequests(userIp);
            throw new BadCredentialsException(createNotAuthorizedResponse(userIp));
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    private String getUserIpAddress(HttpServletRequest request) {
        String xfHeader = request.getHeader(X_FORWARDED_FOR);
        return xfHeader == null ? request.getRemoteAddr() : xfHeader.split(",")[0];
    }

    private String createNotAuthorizedResponse(String userIp) {
        return String.format(NOT_AUTHORIZED_RESP_MSG_FORMAT,
              lockHandler.getLockInfo(userIp).getLoginAttempts());
    }
}
