package ru.moore.lesson10.services.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
