package com.example.bookmanager.service;

import com.example.bookmanager.dto.book.BookResponse;
import com.example.bookmanager.dto.book.CreateBookRequest;
import com.example.bookmanager.dto.book.UpdateBookRequest;
import com.example.bookmanager.entity.Author;
import com.example.bookmanager.entity.Book;
import com.example.bookmanager.entity.Category;
import com.example.bookmanager.exception.ResourceNotFoundException;
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

    public BookResponse getBookById(Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
        return bookMapper.toDto(book);
    }

    public BookResponse createBook(CreateBookRequest createBookRequest){
        Book book = bookMapper.toEntity(createBookRequest);
        Set<Author> authors = new HashSet<>(authorRepository.findAllById(createBookRequest.getAuthorIds()));
        if(authors.size() != createBookRequest.getAuthorIds().size()){
            throw new ResourceNotFoundException("One or more authors not found");
        }

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(createBookRequest.getCategoryIds()));
        if(categories.size() != createBookRequest.getCategoryIds().size()){
            throw new ResourceNotFoundException("one or more categories not found");
        }

        book.setAuthors(authors);
        book.setCategories(categories);

        return bookMapper.toDto(bookRepository.save(book));
    }

    public BookResponse updateBook(Long id, UpdateBookRequest updateBookRequest){
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        if (updateBookRequest.getTitle() != null){
            book.setTitle(updateBookRequest.getTitle());
        }
        if (updateBookRequest.getDescription() != null){
            book.setDescription(updateBookRequest.getDescription());
        }
        if (updateBookRequest.getPublicationYear() != null){
            book.setPublicationYear(updateBookRequest.getPublicationYear());
        }

        if (updateBookRequest.getAuthorIds() != null){
            Set<Author> authors = new HashSet<>(authorRepository.findAllById(updateBookRequest.getAuthorIds()));

            if(authors.size() != updateBookRequest.getAuthorIds().size()){
                throw new ResourceNotFoundException("One or more authors not found");
            }

            book.setAuthors(authors);
        }

        if (updateBookRequest.getCategoryIds() != null){
            Set<Category> categories = new HashSet<>(categoryRepository.findAllById(updateBookRequest.getCategoryIds()));

            if(categories.size() != updateBookRequest.getCategoryIds().size()){
                throw new ResourceNotFoundException("One or more categories not found");
            }

            book.setCategories(categories);
        }

        return bookMapper.toDto(bookRepository.save(book));
    }

    public void deleteBook(Long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        bookRepository.delete(book);
    }
}
