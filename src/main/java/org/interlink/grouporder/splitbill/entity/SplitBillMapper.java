package org.interlink.grouporder.splitbill.entity;

import org.modelmapper.ModelMapper;

public class SplitBillMapper {

    private SplitBillMapper() {
    }

    private static final ModelMapper mapper = new ModelMapper();

    public static <T> T map(Object object, Class<T> objectClass) {
        return mapper.map(object, objectClass);
    }

}
