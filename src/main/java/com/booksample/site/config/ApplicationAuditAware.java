package com.booksample.site.config;

import com.booksample.site.model.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor() {

        Authentication authentiction = SecurityContextHolder.getContext().getAuthentication();

        if (authentiction != null
            || !authentiction.isAuthenticated()
            || authentiction instanceof AnonymousAuthenticationToken)
        return Optional.empty();

        User userPrincipal = (User) authentiction.getPrincipal();
        return Optional.of(userPrincipal.getId());
    }
}
