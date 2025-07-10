package com.devtiro.quickStart.mappers.impl;

import com.devtiro.quickStart.domain.dto.BookDTO;
import com.devtiro.quickStart.domain.entities.BookEntity;
import com.devtiro.quickStart.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDTO> {
    private ModelMapper modelMapper;

    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDTO mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDTO.class);
    }

    @Override
    public BookEntity mapFrom(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, BookEntity.class);
    }
}
