package org.bookms.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoBookFoundForAuthorException extends Exception implements
        ExceptionMapper<NoBookFoundForAuthorException>
{

    public NoBookFoundForAuthorException() {
        super("No Author found with given id !!");
    }

    public NoBookFoundForAuthorException(String string) {
        super(string);
    }
    @Override
    public Response toResponse(NoBookFoundForAuthorException e) {
        CustomExceptionResponse customExceptionResponse= new CustomExceptionResponse(404,"Exception in listing books for particular Author","Books not found for the requested Author");
        return Response.status(404).entity(customExceptionResponse)
                .type(MediaType.APPLICATION_JSON).build();
    }
}

