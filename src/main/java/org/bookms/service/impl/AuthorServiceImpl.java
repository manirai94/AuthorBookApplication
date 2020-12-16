package org.bookms.service.impl;

import com.google.inject.Inject;
import org.bookms.dao.AuthorDAO;
import org.bookms.exception.AuthorNotFoundException;
import org.bookms.model.Author;
import org.bookms.service.AuthorService;

import javax.ws.rs.core.Response;
import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDAO;

    @Inject
    public AuthorServiceImpl(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    public Author findById(int id) {
        return authorDAO.findById(id);

    }

    @Override
    public List<Author> findAll() {
        return authorDAO.findAll();
    }

    @Override
    public Author create(Author author) {
        return authorDAO.create(author);
    }
}
