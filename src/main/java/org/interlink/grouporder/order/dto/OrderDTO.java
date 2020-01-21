package org.interlink.grouporder.order.dto;

import lombok.Getter;
import lombok.Setter;
import org.interlink.grouporder.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderDTO {

    private String name;
    private BigDecimal fullPrice;
    private int companyId;

    private List<Product> items;
}
