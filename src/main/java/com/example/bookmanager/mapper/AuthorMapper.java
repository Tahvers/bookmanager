package com.example.bookmanager.mapper;

import com.example.bookmanager.dto.author.AuthorResponse;
import com.example.bookmanager.dto.author.CreateAuthorRequest;
import com.example.bookmanager.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author toEntity (CreateAuthorRequest createAuthorRequest){
        Author author = new Author();

        author.setName(createAuthorRequest.getName());

        return author;
    }

    public AuthorResponse toDto(Author author){
        AuthorResponse authorResponse = new AuthorResponse();

        authorResponse.setId(author.getId());
        authorResponse.setName(author.getName());

        return authorResponse;
    }
}
