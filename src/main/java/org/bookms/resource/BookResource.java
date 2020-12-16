package org.bookms.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import org.bookms.exception.AuthorNotFoundException;
import org.bookms.exception.NoBookFoundForAuthorException;
import org.bookms.internal.*;
import org.bookms.model.Book;
import org.bookms.response.AuthorBookSaveResponse;
import org.bookms.response.AuthorResponse;
import org.bookms.response.BookResponse;
import org.bookms.service.BookService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Slf4j
@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    private final BookService bookService;
    private final FindBookForAuthorAction findBookForAuthorAction;
    private final AllBooksAction allBooksAction;
    private final SaveBookAction saveBookAction;


    @Inject
    public BookResource(BookService bookService, FindBookForAuthorAction findBookForAuthorAction, AllBooksAction allBooksAction, SaveBookAction saveBookAction) {
        this.bookService = bookService;
        this.findBookForAuthorAction = findBookForAuthorAction;
        this.allBooksAction = allBooksAction;
        this.saveBookAction = saveBookAction;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public Response findBookOfAuthor(@PathParam("id") int id) throws NoBookFoundForAuthorException {
        BookResponse book = findBookForAuthorAction.apply(id);
        if (book == null) {
            throw new NoBookFoundForAuthorException();
        }

        return Response.ok(book).build();
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/findAll")
    public List<Book> findAllBooks() {

        return bookService.getListOfBooks();
    }

    @POST
    @Timed
    @UnitOfWork
    @Path("/save/{id}")
    public Response save(@PathParam("id") int id, Book book) throws AuthorNotFoundException {
        AuthorBookSaveResponse bookObj = bookService.save(book, id);
        if (bookObj == null) {
            throw new AuthorNotFoundException();
        }
        return Response.ok(bookObj).build();

    }
}
