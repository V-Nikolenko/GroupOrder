package org.interlink.grouporder.misteram.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberOrderDTO {
    private String name;
    private String email;
    private String url;
    private List<ItemDTO> items;
    private int fullPrice;
}
