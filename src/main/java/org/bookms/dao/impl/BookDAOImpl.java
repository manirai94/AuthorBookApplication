package org.bookms.dao.impl;

import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import lombok.extern.slf4j.Slf4j;
import org.bookms.dao.BookDAO;
import org.bookms.model.Book;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

@Slf4j
public class BookDAOImpl extends AbstractDAO<Book> implements BookDAO {

    @Inject
    public BookDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Book> findBookForAuthor(int id) {
        return list((Query<Book>) namedQuery("Book.findBookByAuthorId").setParameter("id", id));
    }

    public Book findById(int id) {
        return get(id);
    }

    public Book save(Book bookObj) {
        return persist(bookObj);
    }

    public List<Book> getListOfBooks() {
        return list((Query<Book>) namedQuery("Book.getListOfBooks"));
    }
}
