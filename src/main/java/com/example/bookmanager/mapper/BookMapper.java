package com.example.bookmanager.mapper;

import com.example.bookmanager.dto.book.BookResponse;
import com.example.bookmanager.dto.book.CreateBookRequest;
import com.example.bookmanager.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookResponse toDto(Book book){
        BookResponse bookResponse = new BookResponse();

        bookResponse.setId(book.getId());
        bookResponse.setTitle(book.getTitle());
        bookResponse.setDescription(book.getDescription());
        bookResponse.setPublicationYear(book.getPublicationYear());

        return bookResponse;
    }

    public Book toEntity(CreateBookRequest createBookRequest){
        Book book = new Book();

        book.setTitle(createBookRequest.getTitle());
        book.setDescription(createBookRequest.getDescription());
        book.setPublicationYear(createBookRequest.getPublicationYear());

        return book;
    }
}
