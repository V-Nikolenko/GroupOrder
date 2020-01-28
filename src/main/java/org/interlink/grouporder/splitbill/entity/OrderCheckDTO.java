package org.interlink.grouporder.splitbill.entity;

import java.util.List;

public class OrderCheckDTO {
    private String getInternetShopURL;
    private int fullPrice;
    private List<MemberDTO> items;

    public String getInternetShopURL() {
        return getInternetShopURL;
    }

    public void setInternetShopURL(String getInternetShopURL) {
        this.getInternetShopURL = getInternetShopURL;
    }

    public int getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(int fullPrice) {
        this.fullPrice = fullPrice;
    }

    public List<MemberDTO> getItems() {
        return items;
    }

    public void setItems(List<MemberDTO> items) {
        this.items = items;
    }
}
