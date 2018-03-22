package pl.pollub.dto.request;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Data
@Builder
public class UserRequest {

    @Pattern(regexp = "^[a-z0-9_-]{6,32}$", message = "javax.validation.constraints.username.message")
    private String username;

    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})", message = "javax.validation.constraints.password.message")
    private String password;

    @Email
    @NotBlank
    private String email;
}
