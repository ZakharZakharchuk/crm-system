package org.example.config.security;

import java.time.Clock;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LockInfo {
    private static final int LOCKING_TIME_IN_MINUTES = 5;
    private static final int MAX_ATTEMPTS = 3;

    private String ipAddress;
    private LocalDateTime lastLoginDatetime;
    private int loginAttempts;

    public boolean isLockTimeExpired() {
        return LocalDateTime.now(Clock.systemUTC()).isAfter(getLockedUntilDateTime());
    }

    public LocalDateTime getLockedUntilDateTime() {
        return lastLoginDatetime.plusMinutes(LOCKING_TIME_IN_MINUTES);
    }

    boolean isReachedMaxLoginAttempts() {
        return loginAttempts >= MAX_ATTEMPTS;
    }

    void updateLoginAttemptsAndLoginDateTime() {
        loginAttempts++;
        this.lastLoginDatetime = LocalDateTime.now(Clock.systemUTC());
    }
}
