package com.example.bookmanager.mapper;

import com.example.bookmanager.dto.book.BookResponse;
import com.example.bookmanager.dto.book.CreateBookRequest;
import com.example.bookmanager.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;
    private final CategoryMapper categoryMapper;

    public Book toEntity(CreateBookRequest createBookRequest){
        Book book = new Book();

        book.setTitle(createBookRequest.getTitle());
        book.setDescription(createBookRequest.getDescription());
        book.setPublicationYear(createBookRequest.getPublicationYear());

        return book;
    }

    public BookResponse toDto(Book book){
        BookResponse bookResponse = new BookResponse();

        bookResponse.setId(book.getId());
        bookResponse.setTitle(book.getTitle());
        bookResponse.setDescription(book.getDescription());
        bookResponse.setPublicationYear(book.getPublicationYear());
        bookResponse.setAuthors(book.getAuthors().stream().map(authorMapper::toDto).collect(Collectors.toSet()));
        bookResponse.setCategories(book.getCategories().stream().map(categoryMapper::toDto).collect(Collectors.toSet()));

        return bookResponse;
    }
}
