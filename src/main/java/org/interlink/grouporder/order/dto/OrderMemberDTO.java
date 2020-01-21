package org.interlink.grouporder.order.dto;

import lombok.Getter;
import lombok.Setter;
import org.interlink.grouporder.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderMemberDTO {

    private String name;
    private BigDecimal fullPrice;
    private List<Product> items;
}
