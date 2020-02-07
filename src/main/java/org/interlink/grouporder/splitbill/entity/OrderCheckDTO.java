package org.interlink.grouporder.splitbill.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderCheckDTO {
    private int companyId;
    private BigDecimal fullPrice;
    private List<MemberDTO> items;
}
