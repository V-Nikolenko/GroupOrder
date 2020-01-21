package org.interlink.grouporder.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {

    private String name;
    private int companyId;
    private int dishId;
    private int optionId;
    private String optionValue;
    private String measure;
    private String measureType;
    private String packagePrice;
    private int maxCountPositionInPackage;

}
