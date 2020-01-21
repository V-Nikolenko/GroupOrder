package org.interlink.grouporder.order.dto;

import lombok.Getter;
import lombok.Setter;
import org.interlink.grouporder.entity.Product;

import java.util.List;

@Getter
@Setter
public class ProductsDTO {

    private List<Product> products;
}
