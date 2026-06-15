package com.example.bookmanager.mapper;

import com.example.bookmanager.dto.userBook.LibraryBookResponse;
import com.example.bookmanager.entity.Author;
import com.example.bookmanager.entity.UserBook;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class LibraryMapper {

    public LibraryBookResponse toDto(UserBook userBook){
        LibraryBookResponse libraryBookResponse = new LibraryBookResponse();

        libraryBookResponse.setBookId(userBook.getBook().getId());
        libraryBookResponse.setTitle(userBook.getBook().getTitle());
        libraryBookResponse.setAuthors(userBook.getBook().getAuthors().stream().map(Author::getName).collect(Collectors.toSet()));
        libraryBookResponse.setStatus(userBook.getStatus());
        libraryBookResponse.setRating(userBook.getRating());
        libraryBookResponse.setAddedDate(userBook.getAddedDate());

        return libraryBookResponse;
    }
}
