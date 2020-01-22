package org.interlink.grouporder.misteram;

import org.modelmapper.ModelMapper;

import java.util.Objects;

public class MisterAmMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static <T> T map(Objects object, Class<T> objectClass) {
        return Objects.isNull(object) ? null : mapper.map(object, objectClass);
    }
}
