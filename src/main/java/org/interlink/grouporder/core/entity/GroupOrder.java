package org.interlink.grouporder.core.entity;

import java.util.ArrayList;
import java.util.List;

public class GroupOrder {
    private String code;
    private String internetShopURL;
    private List<MemberOrder> members;

    public GroupOrder(String code, String internetShopURL, List<MemberOrder> members) {
        this.code = code;
        this.internetShopURL = internetShopURL;
        this.members = members;
    }

    public void addMemberToGroupOrder(MemberOrder member) {
        members.add(member);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        for (MemberOrder member : members) {
            products.addAll(member.getProducts());
        }
        return products;
    }
}
