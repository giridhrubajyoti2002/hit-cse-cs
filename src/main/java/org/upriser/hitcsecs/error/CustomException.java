package org.upriser.hitcsecs.error;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@NoArgsConstructor
public class CustomException extends Exception{
    private HttpStatus httpStatus;

    public CustomException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public CustomException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
