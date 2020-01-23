package org.interlink.grouporder.misteram;

import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.core.entity.Product;
import org.interlink.grouporder.misteram.entity.Order;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class MisterAmMapper {

    private MisterAmMapper() {
    }

    private static final ModelMapper mapper = new ModelMapper();

    public static <T> T map(Object object, Class<T> objectClass) {
        return mapper.map(object, objectClass);
    }

    public static MemberOrder map(Order source, MemberOrder destination) {
        List<Product> products = source.getItems().stream().map(item -> map(item, Product.class)).collect(Collectors.toList());
        destination.setName(source.getName());
        destination.setProducts(products);
        destination.setUrl(source.getUrl());

        return destination;
    }
}
