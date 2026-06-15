package com.example.bookmanager.service;

import com.example.bookmanager.dto.userBook.AddBookToLibraryRequest;
import com.example.bookmanager.dto.userBook.LibraryBookResponse;
import com.example.bookmanager.dto.userBook.UpdateLibraryEntryRequest;
import com.example.bookmanager.entity.Book;
import com.example.bookmanager.entity.User;
import com.example.bookmanager.entity.UserBook;
import com.example.bookmanager.entity.enums.ReadingStatus;
import com.example.bookmanager.exception.ResourceAlreadyExistException;
import com.example.bookmanager.exception.ResourceNotFoundException;
import com.example.bookmanager.mapper.LibraryMapper;
import com.example.bookmanager.repository.BookRepository;
import com.example.bookmanager.repository.UserBookRepository;
import com.example.bookmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserBookRepository userBookRepository;
    private final LibraryMapper libraryMapper;

    public LibraryBookResponse addBookToLibrary(Long userId , AddBookToLibraryRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new ResourceNotFoundException(("Book not found with id: " + request.getBookId())));

        if (userBookRepository.existsByUserIdAndBookId(userId, request.getBookId())){
            throw new ResourceAlreadyExistException("Book already exists in user library");
        }

        UserBook userBook = new UserBook();
        userBook.setUser(user);
        userBook.setBook(book);

        if (request.getStatus() == null){
            userBook.setStatus(ReadingStatus.TO_READ);
        } else {
            userBook.setStatus(request.getStatus());
        }

        userBook.setRating(request.getRating());

        UserBook savedBook = userBookRepository.save(userBook);

        return libraryMapper.toDto(savedBook);
    }

    public LibraryBookResponse updateLibraryEntry(Long userId, Long bookId, UpdateLibraryEntryRequest updateLibraryEntryRequest){
        UserBook userBook = userBookRepository.findByUserIdAndBookId(userId, bookId).orElseThrow(() -> new ResourceNotFoundException("Library entry not found"));

        if (updateLibraryEntryRequest.getRating() != null){
            userBook.setRating(updateLibraryEntryRequest.getRating());
        }
        if (updateLibraryEntryRequest.getStatus() != null){
            userBook.setStatus(updateLibraryEntryRequest.getStatus());
        }

        UserBook savedLibraryEntry = userBookRepository.save(userBook);

        return libraryMapper.toDto(savedLibraryEntry);
    }

    public List<LibraryBookResponse> getUserBooks(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        List<LibraryBookResponse> list = userBookRepository.findByUserId(id).stream().map(libraryMapper::toDto).collect(Collectors.toList());

        return list;
    }

    public void deleteBookFromLibrary(Long userId, Long bookId){
        UserBook userBook = userBookRepository.findByUserIdAndBookId(userId, bookId).orElseThrow(() -> new ResourceNotFoundException("Library entry not found"));

        userBookRepository.delete(userBook);
    }
}
