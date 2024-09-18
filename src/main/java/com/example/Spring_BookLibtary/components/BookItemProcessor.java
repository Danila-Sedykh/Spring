package com.example.Spring_BookLibtary.components;

import com.example.Spring_BookLibtary.models.Book;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class BookItemProcessor implements ItemProcessor<Book,Book> {

    @Override
    public Book process(Book book) throws Exception {
        return book;
    }
}
