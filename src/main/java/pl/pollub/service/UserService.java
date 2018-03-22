package pl.pollub.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.pollub.domain.User;

public interface UserService extends UserDetailsService {

    User registerNewUser(User user);

    User getUserById(Long id);

    User getUserByUsername(String username);
}
