package pl.pollub.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.pollub.domain.User;

@Component
public class PrincipalProviderImpl implements PrincipalProvider {
    @Override
    public String getLoggedUsername() {
        return isUserLogged() ? getPrincipal().getUsername() : null;
    }

    @Override
    public User getLoggedUser() {
        return getPrincipal().getUser();
    }

    @Override
    public AppPrincipal getPrincipal() {
        return (AppPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public boolean isUserLogged() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken);
    }
}
