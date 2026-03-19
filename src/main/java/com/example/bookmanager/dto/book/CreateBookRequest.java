package com.example.bookmanager.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

//Esta clase es para el admin, no para el user

@Getter
@Setter
public class CreateBookRequest {

    @NotBlank
    private String title;
    private String description;
    @NotNull
    private Integer publicationYear;
    @NotEmpty
    private Set<Long> authorIds;
    @NotEmpty
    private Set<Long> categoryIds;
}
