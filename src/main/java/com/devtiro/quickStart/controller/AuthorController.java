package com.devtiro.quickStart.controller;

import com.devtiro.quickStart.domain.dto.AuthorDTO;
import com.devtiro.quickStart.domain.entities.AuthorEntity;
import com.devtiro.quickStart.mappers.Mapper;
import com.devtiro.quickStart.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private AuthorService authorService;

    private Mapper<AuthorEntity, AuthorDTO> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDTO> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    };

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
            // convert the DTO we are getting into an entity, save it and then return it
            // by converting it back to an author DTO
            AuthorEntity authorEntity = authorMapper.mapFrom(authorDTO);
            AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
            return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }
}
