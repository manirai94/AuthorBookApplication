package org.bookms.dao.impl;

import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.bookms.dao.AuthorDAO;
import org.bookms.model.Author;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class AuthorDAOImpl extends AbstractDAO<Author> implements AuthorDAO {

    @Inject
    public AuthorDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Author findById(int id) {
        return get(id);
    }

    @Override
    public List<Author> findAll() {
        return list((Query<Author>) namedQuery("Author.findAllAuthors"));
    }

    @Override
    public Author create(Author author) {
        return persist(author);
    }
}
