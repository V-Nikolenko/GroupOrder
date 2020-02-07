package org.interlink.grouporder.splitbill.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MemberDTO {
    private String name;
    private BigDecimal price;
    private int count = 1;
}
