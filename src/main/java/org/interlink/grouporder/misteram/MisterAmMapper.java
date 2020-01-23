package org.interlink.grouporder.misteram;

import org.modelmapper.ModelMapper;

public class MisterAmMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static <T> T map(Object object, Class<T> objectClass) {
        return
//                Object.isNull(object) ? null :
                mapper.map(object, objectClass);
    }
}
