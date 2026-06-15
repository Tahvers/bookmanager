package com.example.bookmanager.dto.userBook;

import com.example.bookmanager.entity.enums.ReadingStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class LibraryBookResponse {
    private Long bookId;
    private String title;
    private Set<String> authors;
    private ReadingStatus status;
    private Integer rating;
    private LocalDate addedDate;
}
