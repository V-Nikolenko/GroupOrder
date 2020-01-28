package org.interlink.grouporder.core.utils;

import org.interlink.grouporder.core.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductsCounter {

    private ProductsCounter() {
    }

    public static List<Product> getAllGroupingProducts(List<Product> list) {
        return list.stream().collect(
                Collectors.groupingBy(Product::getId))
                .values()
                .stream().map(products -> products
                        .stream()
                        .reduce((product, sameProduct) ->
                                new Product(product.getId(), product.getName(), product.getPrice(),
                                        product.getCount() + sameProduct.getCount())))
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
