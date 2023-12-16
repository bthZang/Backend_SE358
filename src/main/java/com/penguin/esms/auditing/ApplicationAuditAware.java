package com.penguin.esms.auditing;

import com.penguin.esms.components.staff.StaffEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<StaffEntity> {
    @Override
    public Optional<StaffEntity> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }

        StaffEntity userPrincipal = (StaffEntity) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal);
    }
}