package rent.properly.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<ValidationError> validationErrors;

    public ErrorResponse(LocalDateTime timestamp, String message, int status, String error, String path) {
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
        this.error = error;
        this.path = path;
        this.validationErrors = null;
    }

    @Data
    @AllArgsConstructor
    public static class ValidationError {
        private String field;
        private String message;
    }
}
