package org.interlink.grouporder.misteram;

import org.modelmapper.ModelMapper;

import java.util.Objects;

public class MisterAmMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static <T> T map(Object object, Class<T> objectClass) {
        return mapper.map(object, objectClass);
    }

}
