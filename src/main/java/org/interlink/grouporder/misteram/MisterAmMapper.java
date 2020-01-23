package org.interlink.grouporder.misteram;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.misteram.entity.Item;
import org.interlink.grouporder.misteram.entity.Order;
import org.modelmapper.ModelMapper;

import java.security.acl.Group;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MisterAmMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static <T> T map(Object object, Class<T> objectClass) {
        return mapper.map(object, objectClass);
    }

    public static Order map(GroupOrder source, Order destination){
        List<Item> items = source.getAllProducts().stream()
                .map(product -> MisterAmMapper.map(product, Item.class))
                .collect(Collectors.toList());
        destination.setItems(items);
        destination.setName(source.getCode());
        destination.setUrl(source.getInternetShopURL());

        return destination;
    }
}
