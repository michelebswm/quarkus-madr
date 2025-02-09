package br.com.madr.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;

@Provider
public class ApplicationServiceExceptionMapper implements ExceptionMapper<ApplicationServiceException> {
    @Override
    public Response toResponse(ApplicationServiceException exception) {
        ResponseError responseError = new ResponseError(
                Instant.now(),
                exception.getStatusCode(),
                exception.getMessage(),
                exception.getStackTrace()
        );
        return Response.status(exception.getStatusCode()).entity(responseError).build();
    }
}
