package org.interlink.grouporder.misteram.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class MemberOrderDTO {
    private String name;
    private String email;
    private String url;

    private List<ItemDTO> items; // По идее должен быть json строкой (с)Андрей
    private int companyId;
    private BigDecimal fullPrice;
}
