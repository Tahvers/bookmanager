package com.example.bookmanager.dto.userBook;

import com.example.bookmanager.entity.enums.ReadingStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLibraryEntryRequest {
    private ReadingStatus status;
    @Max(5) @Min(1)
    private Integer rating;
}
