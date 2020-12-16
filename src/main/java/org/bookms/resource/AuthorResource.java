package org.bookms.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import org.bookms.exception.AuthorNotFoundException;
import org.bookms.internal.AllAuthorAction;
import org.bookms.internal.FindAuthorByIdAction;
import org.bookms.internal.SaveAuthorAction;
import org.bookms.model.Author;
import org.bookms.response.AuthorBookSaveResponse;
import org.bookms.response.AuthorResponse;
import org.bookms.service.AuthorService;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Slf4j
@Path("/author")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private final AuthorService authorService;
    private final FindAuthorByIdAction findAuthorByIdAction;
    private final AllAuthorAction allAuthorAction;
    private final SaveAuthorAction saveAuthorAction;


    @Inject
    public AuthorResource(FindAuthorByIdAction findAuthorByIdAction, AllAuthorAction allAuthorAction, SaveAuthorAction saveAuthorAction, AuthorService authorService) {
        this.findAuthorByIdAction = findAuthorByIdAction;
        this.allAuthorAction = allAuthorAction;
        this.saveAuthorAction = saveAuthorAction;
        this.authorService = authorService;
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Response findAuthor(@PathParam("id") int id) throws AuthorNotFoundException {
        AuthorResponse author = findAuthorByIdAction.apply(id);
        if (author == null) {
            throw new AuthorNotFoundException();
        }

        return Response.ok(author).build();
    }

    @GET
    @Timed
    @UnitOfWork
    public List<Author> findAllAuthors() {

        return authorService.findAll();
    }

    @POST
    @Timed
    @UnitOfWork
    public AuthorBookSaveResponse save(@Valid Author author){

        return saveAuthorAction.apply(author);
    }
}
