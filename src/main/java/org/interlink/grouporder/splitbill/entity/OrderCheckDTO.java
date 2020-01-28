package org.interlink.grouporder.splitbill.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderCheckDTO {
    private String internetShopURL;
    private int fullPrice;
    private List<MemberDTO> items;
}
