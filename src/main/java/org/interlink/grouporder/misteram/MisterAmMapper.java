package org.interlink.grouporder.misteram;

import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.misteram.entity.MemberOrderDTO;
import org.interlink.grouporder.misteram.entity.StringResultDTO;
import org.modelmapper.ModelMapper;

public class MisterAmMapper {

    private MisterAmMapper() {
    }

    private static final ModelMapper mapper = new ModelMapper();

    public static <T> T map(Object object, Class<T> objectClass) {
        return mapper.map(object, objectClass);
    }

    public static StringResultDTO map(String result, StringResultDTO destination) {
        destination.setResult(result);

        return destination;
    }

}
