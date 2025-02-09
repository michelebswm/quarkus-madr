package br.com.madr.exception;

import br.com.madr.utils.message.MessageBundle;
import br.com.madr.utils.message.MessageService;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApplicationParserException implements ExceptionMapper<InvalidFormatException> {
    @Override
    public Response toResponse(InvalidFormatException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(
                new MessageService(MessageBundle.getMessage("message.parsererror"))
        ).build();
    }
}
