package pl.pollub.exception;

import org.springframework.http.HttpStatus;

public abstract class CommonException extends RuntimeException {

    protected Object parameter;

    public CommonException(Object parameter) {
        this.parameter = parameter;
    }

    public abstract HttpStatus getHttpReturnStatus();

    public abstract String getMessage();
}
