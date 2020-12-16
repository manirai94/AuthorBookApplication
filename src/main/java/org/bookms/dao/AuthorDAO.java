package org.bookms.dao;


import org.bookms.model.Author;

import java.util.List;

public interface AuthorDAO {

    Author findById(int id);

    List<Author> findAll();

    Author create(Author author);
}
