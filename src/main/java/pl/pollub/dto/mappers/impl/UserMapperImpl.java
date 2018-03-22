package pl.pollub.dto.mappers.impl;

import org.springframework.stereotype.Component;
import pl.pollub.domain.User;
import pl.pollub.dto.mappers.UserMapper;
import pl.pollub.dto.request.UserRequest;
import pl.pollub.dto.response.UserResponse;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                           .id(user.getId())
                           .username(user.getUsername())
                           .email(user.getEmail())
                           .enabled(user.getEnabled())
                           .isBanned(user.getIsBanned())
                           .createdDate(user.getCreatedDate())
                           .role(user.getRole())
                           .build();
    }

    @Override
    public UserResponse mapUsernameToResponse(String username) {
        return UserResponse.builder()
                           .username(username)
                           .build();
    }

    @Override
    public User mapToEntity(UserRequest request) {
        return User.builder()
                   .username(request.getUsername())
                   .password(request.getPassword())
                   .email(request.getEmail())
                   .build();
    }
}
