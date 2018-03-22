package pl.pollub.exception.auth;

import org.springframework.http.HttpStatus;
import pl.pollub.exception.CommonException;

public class UserUsernameExistException extends CommonException {

    public UserUsernameExistException(Object parameter) {
        super(parameter);
    }

    @Override
    public HttpStatus getHttpReturnStatus() {
        return HttpStatus.CONFLICT;
    }

    @Override
    public String getMessage() {
        return "There is an account with that username: [" + parameter + "]";
    }
}
