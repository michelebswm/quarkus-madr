package br.com.madr.utils.message;

import java.io.Serial;
import java.io.Serializable;

public class MessageResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
