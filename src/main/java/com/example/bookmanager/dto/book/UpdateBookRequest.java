package com.example.bookmanager.dto.book;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateBookRequest {
    private String title;
    private String description;
    private Integer publicationYear;
    private Set<Long> authorIds;
    private Set<Long> categoryIds;
}
