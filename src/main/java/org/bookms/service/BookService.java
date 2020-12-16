package org.bookms.service;

import org.bookms.model.Book;
import org.bookms.response.AuthorBookSaveResponse;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findBookForAuthor(int id);

    Book findById(int id);

    AuthorBookSaveResponse save(Book bookObj, int id);

    List<Book> getListOfBooks();
}
