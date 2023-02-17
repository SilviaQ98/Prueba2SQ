package com.distribuida.servicios;

import com.distribuida.db.Book;
import java.util.List;

public interface BookRespository {

    List<Book> getBooks();
    Book getBookById(Integer id);
    void creteBook(Book book);
    void updateBook(Integer id,Book book);
    void delete(Integer id);

}
