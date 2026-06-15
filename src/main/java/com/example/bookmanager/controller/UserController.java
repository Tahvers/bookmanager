package com.example.bookmanager.controller;

import com.example.bookmanager.dto.userBook.LibraryBookResponse;
import com.example.bookmanager.dto.userBook.UpdateLibraryEntryRequest;
import com.example.bookmanager.service.LibraryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final LibraryService libraryService;

    @GetMapping("/{userId}/library")
    public List<LibraryBookResponse> getUserBooks(@PathVariable Long userId) {
        return libraryService.getUserBooks(userId);
    }

    @PutMapping("/{userId}/library/{bookId}")
    public LibraryBookResponse updateLibraryEntry(@PathVariable Long userId, @PathVariable Long bookId, @Valid @RequestBody UpdateLibraryEntryRequest request) {
        return libraryService.updateLibraryEntry(userId, bookId, request);
    }

    @DeleteMapping("/{userId}/library/{bookId}")
    public ResponseEntity<Void> deleteBookFromLibrary(@PathVariable Long userId, @PathVariable Long bookId) {
        libraryService.deleteBookFromLibrary(userId, bookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
