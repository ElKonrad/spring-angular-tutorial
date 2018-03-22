package pl.pollub.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pollub.domain.User;
import pl.pollub.domain.enums.Role;
import pl.pollub.exception.auth.EmailExistsException;
import pl.pollub.exception.auth.UserNotFoundException;
import pl.pollub.exception.auth.UserUsernameExistException;
import pl.pollub.repository.UserRepository;
import pl.pollub.security.AppPrincipal;
import pl.pollub.service.UserService;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = Optional.ofNullable(userRepository.findByUsername(username))
                            .orElseThrow(() -> new UsernameNotFoundException(username));
        return AppPrincipal.builder()
                           .user(user)
                           .build();
    }

    @Override
    @Transactional
    public User registerNewUser(User user) {

        if (ifUserWithSpecifiedEmailExist(user.getEmail())) {
            throw new EmailExistsException(user.getEmail());
        } else if (ifUserWithSpecifiedUsernameExist(user.getUsername())) {
            throw new UserUsernameExistException(user.getUsername());
        }

        return userRepository.save(User.builder()
                                       .username(user.getUsername())
                                       .password(passwordEncoder.encode(user.getPassword()))
                                       .email(user.getEmail())
                                       .role(Role.ROLE_USER)
                                       .enabled(true)
                                       .isBanned(false)
                                       .createdDate(new Date())
                                       .build());
    }


    @Override
    public User getUserById(Long id) {

        return Optional.ofNullable(userRepository.findOne(id))
                       .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User getUserByUsername(String username) {

        return Optional.ofNullable(userRepository.findByUsername(username))
                       .orElseThrow(() -> new UserNotFoundException(username));
    }

    private Boolean ifUserWithSpecifiedEmailExist(String email) {

        return userRepository.countUsersByEmail(email) > 0 ? true : false;
    }

    private Boolean ifUserWithSpecifiedUsernameExist(String username) {

        return userRepository.countUsersByUsername(username) > 0 ? true : false;
    }
}
