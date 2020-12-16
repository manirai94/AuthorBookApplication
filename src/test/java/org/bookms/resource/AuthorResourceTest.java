package org.bookms.resource;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.bookms.internal.AllAuthorAction;
import org.bookms.internal.FindAuthorByIdAction;
import org.bookms.internal.SaveAuthorAction;
import org.bookms.model.Author;
import org.bookms.response.AuthorBookSaveResponse;
import org.bookms.response.AuthorResponse;
import org.bookms.service.AuthorService;
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
public class AuthorResourceTest {

    private static final AuthorService authorService = mock(AuthorService.class);
    private AuthorResponse authorResponse;
    private static final FindAuthorByIdAction findAuthorByIdAction = mock(FindAuthorByIdAction.class);
    private static final AllAuthorAction allAuthorAction= mock(AllAuthorAction.class);
    private static final SaveAuthorAction saveAuthorAction= mock(SaveAuthorAction.class);
    private ArgumentCaptor<Author> authorCaptor = ArgumentCaptor.forClass(Author.class);
    private Author author;
    private AuthorBookSaveResponse authorBookSaveResponse;

    public AuthorResourceTest() {
    }

    @ClassRule
    public static final ResourceTestRule RULE=
            ResourceTestRule
                    .builder()
                    .addResource(new AuthorResource(findAuthorByIdAction,allAuthorAction,saveAuthorAction,authorService))
                    .build();

    @Before
    public void setUp() {
    author = new Author();
    author.setName("Full Name");
    author.setId(1);
    authorResponse=new AuthorResponse();
    authorResponse.setName("Full Name");
    authorResponse.setId(1);
        authorBookSaveResponse= new AuthorBookSaveResponse();
        authorBookSaveResponse.setName("Author");
        authorBookSaveResponse.setMessage("Author Created Success");

    }

    @After
    public void tearDown() {
        reset(authorService);
    }

@Test
    public void authorById() {
        when(findAuthorByIdAction.apply(1)).thenReturn(authorResponse);
      Response authorResponseObj = RULE.target("/author/1")
            .request(MediaType.APPLICATION_JSON)
            .get(Response.class);

      assertThat(authorResponseObj.getStatus()).isEqualTo(200);
        verify(findAuthorByIdAction).apply(1);
    }

    @Test
    public void authors() {
        Author authorObj1 = new Author();
        Author authorObj2 = new Author();
        List<Author> authorList = new ArrayList<>();
        authorList.add(authorObj1);
        authorList.add(authorObj2);

      when(authorService.findAll()).thenReturn(authorList);
        final List<Author> response = RULE.target("/author")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Author>>() {
                });
        verify(authorService).findAll();
        assertThat(response).containsAll(authorList);

    }

    @Test
       public void saveAuthor(){
        when(saveAuthorAction.apply(any(Author.class))).thenReturn(authorBookSaveResponse);
        Response response = RULE.target("/author")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(author, MediaType.APPLICATION_JSON));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(saveAuthorAction).apply(authorCaptor.capture());
}
}
