package com.example.bookmanager.dto.book;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

//Esta clase es para el admin, no para el user

@Getter
@Setter
public class CreateBookRequest {

    private String title;
    private String description;
    private Integer publicationYear;
    private Set<Long> authorIds;
    private Set<Long> categoryIds;
}
