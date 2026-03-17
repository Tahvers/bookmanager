package com.example.bookmanager.service;

import com.example.bookmanager.dto.book.BookResponse;
import com.example.bookmanager.dto.book.CreateBookRequest;
import com.example.bookmanager.entity.Author;
import com.example.bookmanager.entity.Book;
import com.example.bookmanager.entity.Category;
import com.example.bookmanager.mapper.BookMapper;
import com.example.bookmanager.repository.AuthorRepository;
import com.example.bookmanager.repository.BookRepository;
import com.example.bookmanager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;


    public List<BookResponse> getAllBooks(){
        return bookRepository.findAll().stream().map(bookMapper::toDto).collect(Collectors.toList());
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    public BookResponse createBook(CreateBookRequest createBookRequest){
        Book book = bookMapper.toEntity(createBookRequest);
        Set<Author> authors = new HashSet<>(authorRepository.findAllById(createBookRequest.getAuthorIds()));
        if(authors.size() != createBookRequest.getAuthorIds().size()){
            throw new RuntimeException("Some authors not found");
        }

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(createBookRequest.getCategoryIds()));
        if(categories.size() != createBookRequest.getCategoryIds().size()){
            throw new RuntimeException("Some categories not found");
        }

        book.setAuthors(authors);
        book.setCategories(categories);

        Book savedBook = bookRepository.save(book);

        return bookMapper.toDto(savedBook);
    }
}
