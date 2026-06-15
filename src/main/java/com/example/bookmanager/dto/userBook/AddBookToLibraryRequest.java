package com.example.bookmanager.dto.userBook;

import com.example.bookmanager.entity.enums.ReadingStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBookToLibraryRequest {
    @NotNull
    private Long bookId;
    private ReadingStatus status;
    @Min(1) @Max(5)
    private Integer rating;
}
