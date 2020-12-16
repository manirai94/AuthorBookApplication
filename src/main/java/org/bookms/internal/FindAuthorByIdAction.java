package org.bookms.internal;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.bookms.model.Author;
import org.bookms.response.AuthorResponse;
import org.bookms.service.AuthorService;

import java.util.function.Function;

@Slf4j
public class FindAuthorByIdAction implements Function<Integer, AuthorResponse> {

    private final AuthorService authorService;
    private static final Logger LOGGER = Logger.getLogger(FindAuthorByIdAction.class);

    @Inject
    public FindAuthorByIdAction(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public AuthorResponse apply(Integer id) {
        AuthorResponse authorResponse = null;
        Author author = authorService.findById(id);
        if (author != null) {
            authorResponse = new AuthorResponse();
            authorResponse.setId(author.getId());
            authorResponse.setName(author.getName());
        }

        return authorResponse;
    }
}
