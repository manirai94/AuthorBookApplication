package org.bookms.service;

import org.bookms.exception.AuthorNotFoundException;
import org.bookms.model.Author;

import javax.ws.rs.core.Response;
import java.util.List;

public interface AuthorService {

    Author findById(int id);

    List<Author> findAll();

    Author create(Author author);
}
