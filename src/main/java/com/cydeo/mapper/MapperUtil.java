package com.cydeo.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MapperUtil {

    private final ModelMapper modelMapper;

    public <T> T convert(Object source, Class<T> destinationType) {
        return modelMapper.map(source, destinationType);
    }

//    public <T> T convert(Object source, T convertedObject) {
//        return modelMapper.map(source, (Type) convertedObject.getClass());
//    }

}
