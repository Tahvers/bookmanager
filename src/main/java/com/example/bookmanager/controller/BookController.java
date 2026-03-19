package com.example.bookmanager.controller;

import com.example.bookmanager.dto.book.BookResponse;
import com.example.bookmanager.dto.book.CreateBookRequest;
import com.example.bookmanager.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public BookResponse createBook(@Valid @RequestBody CreateBookRequest createBookRequest){
        return bookService.createBook(createBookRequest);
    }

    @GetMapping
    public List<BookResponse> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable Long id){
        return bookService.getBookById(id);
    }
}
