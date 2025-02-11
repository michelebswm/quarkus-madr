package br.com.madr.exception;

import br.com.madr.utils.message.MessageServiceError;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class ResponseError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant timestamp;
    private Integer status;
    private String message;
    private List<MessageServiceError> errors;
    private String[] stack;


    public ResponseError(Instant timestamp, Integer status, String message, StackTraceElement[] stackTrace) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.stack = Arrays.stream(stackTrace).map(StackTraceElement::toString).toArray(String[]::new);
    }

    public ResponseError(Instant timestamp, Integer status, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    public ResponseError(Instant timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public ResponseError(Instant timestamp, Integer status, String message, List<MessageServiceError> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getStack() {
        return stack;
    }

    public void setStack(String[] stack) {
        this.stack = stack;
    }

    public List<MessageServiceError> getErrors() {
        return errors;
    }

    public void setErrors(List<MessageServiceError> errors) {
        this.errors = errors;
    }
}
