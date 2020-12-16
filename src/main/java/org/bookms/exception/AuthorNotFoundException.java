package org.bookms.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthorNotFoundException extends Exception implements
        ExceptionMapper<AuthorNotFoundException>
{


    public AuthorNotFoundException() {
        super("No Author found with given id !!");
    }

    public AuthorNotFoundException(String string) {
        super(string);
    }

    @Override
    public Response toResponse(AuthorNotFoundException exception)
    {
        CustomExceptionResponse customExceptionResponse = new CustomExceptionResponse(404,"Exception in get author","author not valid");
        return Response.status(404).entity(customExceptionResponse)
                .type(MediaType.APPLICATION_JSON).build();

    }

}