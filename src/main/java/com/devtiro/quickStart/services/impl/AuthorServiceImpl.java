package com.devtiro.quickStart.services.impl;

import com.devtiro.quickStart.domain.entities.AuthorEntity;
import com.devtiro.quickStart.repositories.AuthorRepository;
import com.devtiro.quickStart.services.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
