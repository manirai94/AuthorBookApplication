package org.bookms.internal;

import com.google.inject.Inject;
import org.bookms.model.Book;
import org.bookms.response.BookResponse;
import org.bookms.service.BookService;

import java.util.List;
import java.util.function.Function;

public class FindBookForAuthorAction implements Function<Integer, BookResponse> {
    private final BookService bookService;

    @Inject
    public FindBookForAuthorAction(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public BookResponse apply(Integer id) {
        BookResponse bookResponse = null;
        List<Book> book = bookService.findBookForAuthor(id);
        if (book.size()>0) {
            bookResponse = new BookResponse();
            bookResponse.setBooks(book);
        }

        return bookResponse;
    }
}
