package br.com.madr.utils.message;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.ConstraintViolationException;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@RegisterForReflection
public class MessageService implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
    private List<MessageServiceError> errors;

    public MessageService(String message) {
        super();
        this.message = message;
    }

    public MessageService(String message, List<MessageServiceError> errors) {
        super();
        this.message = message;
        this.errors = errors;
    }

    public MessageService(ConstraintViolationException cve){
        super();
        this.errors = cve.getConstraintViolations().stream().map(
                validation -> new MessageServiceError(
                        validation.getMessage(),
                        validation.getPropertyPath().toString()
                )
        ).collect(Collectors.toList());
        this.message = MessageBundle.getMessage("message.parametrosnaoinformados");
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MessageServiceError> getErrors() {
        return errors;
    }

    public void setErrors(List<MessageServiceError> errors) {
        this.errors = errors;
    }
}
