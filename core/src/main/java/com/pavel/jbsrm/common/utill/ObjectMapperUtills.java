package com.pavel.jbsrm.common.utill;

import org.modelmapper.ModelMapper;

import java.util.function.Consumer;
import java.util.function.Function;

public class ObjectMapperUtills {
    private static ModelMapper modelMapper;
    private static final ObjectMapperUtills INSTANCE = new ObjectMapperUtills();

    private ObjectMapperUtills() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public static <D> D mapTo(Object dto, Class<D> entity) {
        return modelMapper.map(dto, entity);
    }

    public static void mapTo (Object source, Object destination) {
        modelMapper.map(source, destination);
    }
}
