package com.example.bookmanager;

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
import com.example.bookmanager.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserBookRepository userBookRepository;
    @Mock
    private LibraryMapper libraryMapper;
    @InjectMocks
    private LibraryService libraryService;
    @Captor
    private ArgumentCaptor<UserBook> captor;

    @Test
    public void addBookToLibrary_validData_returnsResponse(){
        // GIVEN
        Long userId = 1L;
        Long bookId = 1L;

        AddBookToLibraryRequest request = new AddBookToLibraryRequest();
        request.setBookId(bookId);

        User user = new User();
        user.setId(userId);

        Book book = new Book();
        book.setId(bookId);

        LibraryBookResponse response = new LibraryBookResponse();
        response.setBookId(bookId);

        UserBook savedBook = new UserBook();
        savedBook.setBook(book);
        savedBook.setStatus(request.getStatus());
        savedBook.setRating(request.getRating());

        // WHEN
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userBookRepository.existsByUserIdAndBookId(userId,bookId)).thenReturn(false);
        when(userBookRepository.save(any(UserBook.class))).thenReturn(savedBook);
        when(libraryMapper.toDto(any(UserBook.class))).thenReturn(response);

        LibraryBookResponse result = libraryService.addBookToLibrary(userId, request);


        // THEN
        assertNotNull(result);
        assertEquals(bookId, result.getBookId());

    }

    @Test
    public void addBookToLibrary_userNotFound_throwsException(){
        // GIVEN
        Long userId = 1L;
        Long bookId = 1L;

        AddBookToLibraryRequest request = new AddBookToLibraryRequest();
        request.setBookId(bookId);

        // WHEN
        when(userRepository.findById(userId)).thenReturn(Optional.empty());


        // THEN
        assertThrows(ResourceNotFoundException.class, () -> {
            libraryService.addBookToLibrary(userId, request);
        });

    }

    @Test
    public void addBookToLibrary_bookNotFound_throwsException(){
        // GIVEN
        Long userId = 1L;
        Long bookId = 1L;

        AddBookToLibraryRequest request = new AddBookToLibraryRequest();
        request.setBookId(bookId);

        User user = new User();
        user.setId(userId);

        Book book = new Book();
        book.setId(bookId);

        // WHEN
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());


        // THEN
        assertThrows(ResourceNotFoundException.class, () -> {
            libraryService.addBookToLibrary(userId, request);
        });

    }

    @Test
    public void addBookToLibrary_bookAlreadyExists_throwsException(){
        // GIVEN
        Long userId = 1L;
        Long bookId = 1L;

        AddBookToLibraryRequest request = new AddBookToLibraryRequest();
        request.setBookId(bookId);

        User user = new User();
        user.setId(userId);

        Book book = new Book();
        book.setId(bookId);

        // WHEN
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userBookRepository.existsByUserIdAndBookId(userId,bookId)).thenReturn(true);

        // THEN
        assertThrows(ResourceAlreadyExistException.class, () -> {
           libraryService.addBookToLibrary(userId, request);
        });

    }

    @Test
    public void addBookToLibrary_nullStatus_setsDefaultStatus(){
        // GIVEN
        Long userId = 1L;
        Long bookId = 1L;

        AddBookToLibraryRequest request = new AddBookToLibraryRequest();
        request.setStatus(null);
        request.setBookId(bookId);

        User user = new User();
        user.setId(userId);

        Book book = new Book();
        book.setId(bookId);

        // WHEN
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(userBookRepository.existsByUserIdAndBookId(userId,bookId)).thenReturn(false);
        libraryService.addBookToLibrary(userId,request);

        // THEN
        verify(userBookRepository).save(captor.capture());
        UserBook captured = captor.getValue();
        assertEquals(ReadingStatus.TO_READ, captured.getStatus());
    }

    @Test
    public void updateLibraryEntry_entryNotFound_throwsException(){
        // GIVEN
        Long userId= 1L;
        Long bookId= 1L;

        UpdateLibraryEntryRequest request = new UpdateLibraryEntryRequest();

        // WHEN
        when(userBookRepository.findByUserIdAndBookId(userId, bookId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(ResourceNotFoundException.class, () -> {
            libraryService.updateLibraryEntry(userId, bookId, request);
        });

    }

    @Test
    public void updateLibraryEntry_onlyRatingProvided_updatesRatingKeepsStatus(){
        // GIVEN
        Long userId= 1L;
        Long bookId= 1L;

        UserBook existingEntry = new UserBook();
        existingEntry.setStatus(ReadingStatus.READING);
        existingEntry.setRating(3);

        UpdateLibraryEntryRequest request = new UpdateLibraryEntryRequest();
        request.setRating(5);

        //WHEN
        when(userBookRepository.findByUserIdAndBookId(userId, bookId)).thenReturn(Optional.of(existingEntry));
        libraryService.updateLibraryEntry(userId, bookId, request);

        // THEN
        assertEquals(5, existingEntry.getRating());
        assertEquals(ReadingStatus.READING, existingEntry.getStatus());
    }

    @Test
    public void updateLibraryEntry_onlyStatusProvided_updatesStatusKeepsRating(){
        // GIVEN
        Long userId= 1L;
        Long bookId= 1L;

        UserBook existingEntry = new UserBook();
        existingEntry.setStatus(ReadingStatus.READING);
        existingEntry.setRating(3);

        UpdateLibraryEntryRequest request = new UpdateLibraryEntryRequest();
        request.setStatus(ReadingStatus.READ);

        //WHEN
        when(userBookRepository.findByUserIdAndBookId(userId, bookId)).thenReturn(Optional.of(existingEntry));
        libraryService.updateLibraryEntry(userId, bookId, request);

        // THEN
        assertEquals(3, existingEntry.getRating());
        assertEquals(ReadingStatus.READ, existingEntry.getStatus());
    }

    @Test
    public void updateLibraryEntry_bothFieldsProvided_updatesBoth(){
        // GIVEN
        Long userId= 1L;
        Long bookId= 1L;

        UserBook existingEntry = new UserBook();
        existingEntry.setStatus(ReadingStatus.READING);
        existingEntry.setRating(3);

        UpdateLibraryEntryRequest request = new UpdateLibraryEntryRequest();
        request.setStatus(ReadingStatus.READ);
        request.setRating(5);

        // WHEN

        when(userBookRepository.findByUserIdAndBookId(userId, bookId)).thenReturn(Optional.of(existingEntry));
        libraryService.updateLibraryEntry(userId, bookId, request);

        // THEN
        assertEquals(5, existingEntry.getRating());
        assertEquals(ReadingStatus.READ, existingEntry.getStatus());
    }

    @Test
    public void updateLibraryEntry_neitherFieldProvided_keepBothUnchanged(){
        // GIVEN
        Long userId= 1L;
        Long bookId= 1L;

        UserBook existingEntry = new UserBook();
        existingEntry.setStatus(ReadingStatus.READING);
        existingEntry.setRating(3);

        UpdateLibraryEntryRequest request = new UpdateLibraryEntryRequest();

        // WHEN

        when(userBookRepository.findByUserIdAndBookId(userId, bookId)).thenReturn(Optional.of(existingEntry));
        libraryService.updateLibraryEntry(userId, bookId, request);

        // THEN
        assertEquals(3, existingEntry.getRating());
        assertEquals(ReadingStatus.READING, existingEntry.getStatus());
    }

    @Test
    public void deleteBookFromLibrary_resourceNotFound_throwsException(){
        Long userId= 1L;
        Long bookId= 1L;

        // WHEN
        when(userBookRepository.findByUserIdAndBookId(userId, bookId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(ResourceNotFoundException.class, () -> {
            libraryService.deleteBookFromLibrary(userId,bookId);
        } );
    }

    @Test
    public void deleteBookFromLibrary_resourceFound_deleteEntry(){
        Long userId= 1L;
        Long bookId= 1L;

        UserBook existingEntry = new UserBook();

        // WHEN
        when(userBookRepository.findByUserIdAndBookId(userId, bookId)).thenReturn(Optional.of(existingEntry));
        libraryService.deleteBookFromLibrary(userId, bookId);

        // THEN
        verify(userBookRepository).delete(existingEntry);
    }
}
