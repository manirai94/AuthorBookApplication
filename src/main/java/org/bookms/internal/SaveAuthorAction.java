package org.bookms.internal;

import com.google.inject.Inject;
import org.bookms.model.Author;
import org.bookms.response.AuthorBookSaveResponse;
import org.bookms.service.AuthorService;

import java.util.function.Function;

public class SaveAuthorAction implements Function<Author, AuthorBookSaveResponse> {

    private final AuthorService authorService;

    @Inject
    public SaveAuthorAction(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public AuthorBookSaveResponse apply(Author author) {
        AuthorBookSaveResponse authorBookSaveResponse = new AuthorBookSaveResponse();
        Author author_created= authorService.create(author);
        authorBookSaveResponse.setName(author_created.getName());
        authorBookSaveResponse.setMessage("Author Created Successfully");

        return authorBookSaveResponse;
    }
}
