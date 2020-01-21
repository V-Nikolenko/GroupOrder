package org.interlink.grouporder.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDTO {

    //User information
    private String name;
    private BigDecimal fullPrice;
    private int companyId;

    // Product information
    private int dishId;
    private int optionId;
    private String optionValue;
    private String measure;
    private String measureType;
    private String packagePrice;
    private int maxCountPositionInPackage;

}
