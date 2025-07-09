package be.be_new.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTS(1005, "User not exists", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    NOT_FOUND_DATA(400, "Not found data", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    PAYMENT_NOT_COMPLETED(1007, "Payment not completed", HttpStatus.BAD_REQUEST),
    REGISTRATION_FULL(1008, "Registration for test schedule is full", HttpStatus.BAD_REQUEST),
    CANCELLED_REGISFORM(1009, "RegisForm is cancelled", HttpStatus.BAD_REQUEST),
    CAN_NOT_UPDATE(1011, "RegisForm is paid, can not update", HttpStatus.BAD_REQUEST),
    NOT_PAY(1010, "Registration form has not been paid yet", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
