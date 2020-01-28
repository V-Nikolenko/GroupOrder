package org.interlink.grouporder.misteram.entity;

import java.util.List;

public class FullOrderItemsDTO {
    private List<ItemDTO> items;

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
