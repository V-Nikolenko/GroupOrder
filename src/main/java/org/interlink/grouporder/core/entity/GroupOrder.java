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
    private List<MemberOrder> members = new ArrayList<>();

    public GroupOrder(String code) {
        this.code = code;
    }


    public void addMemberToGroupOrder(MemberOrder member) {
        if (member != null) {
            members.add(member);
        }
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
