package pl.pollub.security;

import pl.pollub.domain.User;

public interface PrincipalProvider {
    AppPrincipal getPrincipal();

    User getLoggedUser();

    boolean isUserLogged();

    String getLoggedUsername();
}
