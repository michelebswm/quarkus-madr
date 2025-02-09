package br.com.madr.utils.message;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.io.Serial;
import java.io.Serializable;

@RegisterForReflection
public class MessageServiceError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
    private Integer code;
    private String field;

    public MessageServiceError() {
    }

    public MessageServiceError(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public MessageServiceError(String message, Integer code, String field) {
        this.message = message;
        this.code = code;
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
