package com.pavel.jbsrm.common.utill;

import org.modelmapper.ModelMapper;

public class ObjectMapperUtills {
    private static ModelMapper modelMapper = new ModelMapper();

    public static <D, E> D mapTo(E dto, Class<D> entity) {
        return modelMapper.map(dto, entity);
    }

    public static void mapTo (Object source, Object destination) {
        modelMapper.map(source, destination);
    }
}
