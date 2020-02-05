package org.interlink.grouporder.misteram;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.core.entity.Product;
import org.interlink.grouporder.misteram.entity.FullOrderItemsDTO;
import org.interlink.grouporder.misteram.entity.ItemDTO;

import org.interlink.grouporder.misteram.entity.MemberOrderDTO;
import org.interlink.grouporder.misteram.entity.OrderLinkDTO;
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

    public static MemberOrder map(MemberOrderDTO source, MemberOrder destination) {
        List<Product> products = source.getItems().stream()
                .map(item -> map(item, Product.class))
                .collect(Collectors.toList());

        destination.setName(source.getName());
        destination.setEmail(source.getEmail());
        destination.setProducts(products);
        destination.setUrl(source.getUrl());
        destination.setCompanyId(source.getCompanyId());
        destination.setFullPrice(source.getFullPrice());

        return destination;
    }

    public static FullOrderItemsDTO map(GroupOrder source, FullOrderItemsDTO destination) {
        List<ItemDTO> items = source.getAllProducts().stream()
                .map(product -> map(product, ItemDTO.class))
                .collect(Collectors.toList());

        destination.setFullPrice(source.getFullPrice());
        destination.setItems(items);

        return destination;
    }

    public static OrderLinkDTO map(GroupOrder source, OrderLinkDTO destination) {
        String orderLink = source.getRestaurantUrl() + "?code=" + source.getCode();
        destination.setRestaurantUrl(orderLink);
        destination.setRestaurantName(source.getRestaurantName());
        return destination;
    }
}
