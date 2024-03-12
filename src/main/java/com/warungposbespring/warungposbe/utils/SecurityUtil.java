package com.warungposbespring.warungposbe.utils;

import com.warungposbespring.warungposbe.config.AppAuthentication;
import com.warungposbespring.warungposbe.dto.AuthUserResponse;
import com.warungposbespring.warungposbe.dto.UserForJwtResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    private static final Long defaultMaxToleranceInMinutes = 10L;

    public static AppAuthentication getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AppAuthentication) {
            return (AppAuthentication) authentication;
        }
        return null;
    }

    public static UserForJwtResponse getAuthUser() {
        AppAuthentication authentication = getAuth();
        if (authentication != null) {
            return authentication.getDetails();
        } else  {
            throw new AppException("not authenticated");
        }
    }
}
