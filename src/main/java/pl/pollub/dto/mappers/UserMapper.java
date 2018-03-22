package pl.pollub.dto.mappers;

import pl.pollub.domain.User;
import pl.pollub.dto.request.UserRequest;
import pl.pollub.dto.response.UserResponse;

public interface UserMapper {

    UserResponse mapToResponse(User user);

    UserResponse mapUsernameToResponse(String username);

    User mapToEntity(UserRequest userRequest);
}
