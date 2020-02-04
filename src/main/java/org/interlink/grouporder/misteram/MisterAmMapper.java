package org.interlink.grouporder.misteram;

import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.misteram.entity.MemberOrderDTO;
import org.modelmapper.ModelMapper;

public class MisterAmMapper {

    private MisterAmMapper() {
    }

    private static final ModelMapper mapper = new ModelMapper();

    public static <T> T map(Object object, Class<T> objectClass) {
        return mapper.map(object, objectClass);
    }

    public static MemberOrder map(MemberOrderDTO source, MemberOrder destination) {

        destination.setName(source.getName());
        destination.setEmail(source.getEmail());
        destination.setProducts(source.getItems());
        destination.setRestaurantId(source.getCompanyId());
        destination.setOrderPrice(source.getFullPrice());

        return destination;
    }
}
