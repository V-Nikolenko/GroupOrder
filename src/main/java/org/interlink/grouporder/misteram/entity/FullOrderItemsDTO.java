package org.interlink.grouporder.misteram.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FullOrderItemsDTO {
    private List<ItemDTO> items;
    private int fullPrice;
}
