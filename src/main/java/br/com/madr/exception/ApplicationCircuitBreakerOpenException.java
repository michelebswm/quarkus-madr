package br.com.madr.exception;

import br.com.madr.utils.message.MessageBundle;
import br.com.madr.utils.message.MessageService;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;

@Provider
public class ApplicationCircuitBreakerOpenException implements ExceptionMapper<CircuitBreakerOpenException> {

    @Override
    public Response toResponse(CircuitBreakerOpenException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                new MessageService(MessageBundle.getMessage("message.circuitbreaker"))
        ).build();
    }
}
