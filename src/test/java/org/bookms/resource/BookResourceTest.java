package org.bookms.resource;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.bookms.internal.*;
import org.bookms.model.Author;
import org.bookms.model.Book;
import org.bookms.response.AuthorBookSaveResponse;
import org.bookms.response.AuthorResponse;
import org.bookms.response.BookResponse;
import org.bookms.service.AuthorService;
import org.bookms.service.BookService;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BookResourceTest {

    private static final BookService bookService = mock(BookService.class);
    private static final FindBookForAuthorAction findBookForAuthorAction = mock(FindBookForAuthorAction.class);
    private static final AllBooksAction allBooksAction= mock(AllBooksAction.class);
    private static final SaveBookAction saveBookAction= mock(SaveBookAction.class);
    private Book book;
    private BookResponse bookResponse;
    private AuthorBookSaveResponse authorBookSaveResponse;

    @ClassRule
    public static final ResourceTestRule RULE=
            ResourceTestRule
                    .builder()
                    .addResource(new BookResource(bookService,findBookForAuthorAction,allBooksAction,saveBookAction))
                    .build();

    @Before
    public void setUp() {
        book = new Book();
        book.setName("Book Name");
        book.setId(1);
        authorBookSaveResponse=new AuthorBookSaveResponse();
        authorBookSaveResponse.setName("Book");
        authorBookSaveResponse.setMessage("Book saved successfully");

    }

    @After
    public void tearDown() {
        reset(bookService);
    }

    @Test
    public void bookForAuthorId() {
        Book bookObj1 = new Book();
        Book bookObj2 = new Book();
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookObj1);
        bookList.add(bookObj2);
        bookResponse=new BookResponse();
        bookResponse.setBooks(bookList);

        when(findBookForAuthorAction.apply(1)).thenReturn(bookResponse);
        Response response = RULE.target("/book/1")
                .request(MediaType.APPLICATION_JSON).get(Response.class);
        assertThat(response.getStatus()).isEqualTo(200);
        verify(findBookForAuthorAction).apply(1);
    }

    @Test
    public void findAllBooksTest(){
        Book bookObj1 = new Book();
        Book bookObj2 = new Book();
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookObj1);
        bookList.add(bookObj2);
        when(bookService.getListOfBooks()).thenReturn(bookList);
        final List<Book> response = RULE.target("/book/findAll")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Book>>() {
                });
        verify(bookService).getListOfBooks();
        assertThat(response).containsAll(bookList);
    }

    @Test
    public void saveBook(){
        when(bookService.save(any(Book.class),any(Integer.class))).thenReturn(authorBookSaveResponse);
        final Response response=RULE.target("/book/save/1")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(book,MediaType.APPLICATION_JSON));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(bookService).save(book,1);
    }
}
