package com.example.bookmanager.dto.book;

import com.example.bookmanager.dto.author.AuthorResponse;
import com.example.bookmanager.dto.category.CategoryResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookResponse {
    private Long id;
    private String title;
    private String description;
    private Integer publicationYear;
    private Set<AuthorResponse> authors;
    private Set<CategoryResponse> categories;
}
