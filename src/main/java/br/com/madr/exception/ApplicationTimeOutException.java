package br.com.madr.exception;

import br.com.madr.utils.message.MessageBundle;
import br.com.madr.utils.message.MessageService;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.concurrent.TimeoutException;

@Provider
public class ApplicationTimeOutException implements ExceptionMapper<TimeoutException> {
    @Override
    public Response toResponse(TimeoutException exception) {
        return Response.status(Response.Status.REQUEST_TIMEOUT).entity(new MessageService(
                MessageBundle.getMessage("message.timeout")
        )).build();
    }
}
