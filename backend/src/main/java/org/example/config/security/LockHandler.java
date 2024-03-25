package org.example.config.security;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Component;

@Component
public class LockHandler {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String LOCKED_RESP_MSG_FORMAT = "Locked until: %s (UTC+0) due to exceeded the limit of login attempts: %s";
    private final Map<String, LockInfo> lockInfoCache = new HashMap<>();

    private boolean isUserLocked(String ipAddress) {
        System.out.println(lockInfoCache.size());
        boolean isLocked = false;

        if (lockInfoCache.containsKey(ipAddress)) {
            LockInfo lockInfo = lockInfoCache.get(ipAddress);

            if (lockInfo.isReachedMaxLoginAttempts() && lockInfo.isLockTimeExpired()) {
                lockInfoCache.remove(ipAddress);
            } else {
                isLocked = lockInfo.isReachedMaxLoginAttempts();
            }
        }

        return isLocked;
    }

    public void removeLockInfoFromCache(String ipAddress) {
        lockInfoCache.remove(ipAddress);
    }

    public void throwExceptionWhenLocked(String ipAddress) {
        if (isUserLocked(ipAddress)) {
            throw new LockedException(createResponseMessage(ipAddress));
        }
    }

    public void lockTooMuchRequests(String ipAddress) {
        LockInfo lockInfo = getLockInfo(ipAddress);
        lockInfo.updateLoginAttemptsAndLoginDateTime();
        lockInfoCache.put(ipAddress, lockInfo);

        if (lockInfo.isReachedMaxLoginAttempts()) {
            throw new LockedException(createResponseMessage(ipAddress));
        }
    }

    public LockInfo getLockInfo(String ipAddress) {
        return lockInfoCache.containsKey(ipAddress) ?
              lockInfoCache.get(ipAddress)
              : new LockInfo(ipAddress, LocalDateTime.now(Clock.systemUTC()), 0);
    }

    private String createResponseMessage(String ipAddress) {
        LockInfo lockInfo = lockInfoCache.get(ipAddress);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String formattedTime = lockInfo.getLockedUntilDateTime().format(formatter);

        return String.format(LOCKED_RESP_MSG_FORMAT, formattedTime, lockInfo.getLoginAttempts());
    }
}
