package org.bookms.dao;


import org.bookms.model.Book;

import java.util.List;

public interface BookDAO {


    List<Book> findBookForAuthor(int id);

    Book findById(int id);

    Book save(Book bookObj);

    List<Book> getListOfBooks();
}
