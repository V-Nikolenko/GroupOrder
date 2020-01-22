package org.interlink.grouporder.core.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.interlink.grouporder.core.entity.view.GroupOrderView;

import java.util.ArrayList;
import java.util.List;

public class GroupOrder {
    @JsonView(GroupOrderView.Basic.class)
    private String code;
    @JsonView(GroupOrderView.Extended.class)
    private String internetShopURL;
    @JsonView(GroupOrderView.Extended.class)
    private List<MemberOrder> members;

    public GroupOrder(String code) {
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInternetShopURL() {
        return internetShopURL;
    }

    public void setInternetShopURL(String internetShopURL) {
        this.internetShopURL = internetShopURL;
    }

    public List<MemberOrder> getMembers() {
        return members;
    }

    public void setMembers(List<MemberOrder> members) {
        this.members = members;
    }
}
