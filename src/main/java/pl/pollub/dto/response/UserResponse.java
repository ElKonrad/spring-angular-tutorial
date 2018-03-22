package pl.pollub.dto.response;

import lombok.Builder;
import lombok.Getter;
import pl.pollub.domain.enums.Role;

import java.util.Date;

@Builder
@Getter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private Boolean enabled;
    private Boolean isBanned;
    private Date createdDate;
}
