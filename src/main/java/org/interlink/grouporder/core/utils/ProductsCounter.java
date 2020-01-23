package org.interlink.grouporder.core.utils;

import org.interlink.grouporder.core.entity.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsCounter {

    public List<Product> getAllGroupingProducts(List<Product> list) {
        List<Product> productDistinctList;
        productDistinctList = sortedListProducts(list);

        sortedListProducts(list).forEach(item -> {
            countTheSameItems(item.getId(), item, productDistinctList);
        });

        for (Product product : productDistinctList) {
            int nextItem = productDistinctList.indexOf(product) + 1;
            int itemsSize = productDistinctList.size();
            if (!(nextItem > itemsSize - 1)) {
                if (product.getId() == (productDistinctList.get(productDistinctList.indexOf(product) + 1)).getId()) {
                    return getAllGroupingProducts(productDistinctList);
                }
            }
        }
        return productDistinctList;
    }

    private void countTheSameItems(int id, Product products, List<Product> sortedProductList) {
        int nextItem = sortedProductList.indexOf(products) + 1;
        int itemsSize = sortedProductList.size();

        if (!(nextItem > (itemsSize - 1))) {
            int idNextItem = sortedProductList.get(nextItem).getId();
            if (id == (idNextItem)) {
                int d = products.getCount();
                products.setCount(d + sortedProductList.get(nextItem).getCount());
                sortedProductList.remove(sortedProductList.get(nextItem));
            }
        }
    }

    private List<Product> sortedListProducts(List<Product> list) {
        return list.stream()
                .sorted(Comparator.comparing(Product::getId))
                .collect(Collectors.toList());
    }
}
