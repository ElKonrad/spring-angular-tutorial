package pl.pollub.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.pollub.domain.User;
import pl.pollub.dto.mappers.UserMapper;
import pl.pollub.dto.request.UserRequest;
import pl.pollub.dto.response.UserResponse;
import pl.pollub.security.PrincipalProvider;
import pl.pollub.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "/api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private PrincipalProvider principalProvider;
    private UserMapper userMapper;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResponse> saveUser(@RequestBody @Valid @NotNull UserRequest request) {

        User user = userService.registerNewUser(userMapper.mapToEntity(request));
        return new ResponseEntity<>(userMapper.mapToResponse(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponse> getUserInfo(@PathVariable String username) {

        User user = userService.getUserByUsername(username);
        return new ResponseEntity<>(userMapper.mapToResponse(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/authentication", method = RequestMethod.GET)
    public ResponseEntity<Boolean> isAuthenticated() {

        boolean isUserLogged = principalProvider.isUserLogged();
        return new ResponseEntity<>(isUserLogged, HttpStatus.OK);
    }

    @RequestMapping(value = "/loggedUsername", method = RequestMethod.GET)
    public ResponseEntity<UserResponse> getLoggedUsername() {

        String username = principalProvider.getLoggedUsername();
        return new ResponseEntity<>(userMapper.mapUsernameToResponse(username), HttpStatus.OK);
    }
}
