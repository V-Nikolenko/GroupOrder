package org.interlink.grouporder.core.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.interlink.grouporder.core.entity.view.GroupOrderView;
import org.interlink.grouporder.core.exceptions.BadRequestException;
import org.interlink.grouporder.core.utils.ProductsCounter;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class GroupOrder {

    @JsonView(GroupOrderView.Basic.class)
    private String code;
    @JsonView(GroupOrderView.Extended.class)
    private String internetShopURL;
    @JsonView(GroupOrderView.Extended.class)
    private int fullPrice;
    @JsonView(GroupOrderView.Extended.class)
    private Map<String, MemberOrder> members = new LinkedHashMap<>();

    public GroupOrder(String code) {
        this.code = code;
    }

    private boolean isMemberInGroupOrder(MemberOrder member) {
        return members.containsKey(member.getEmail());
    }

    public void addMemberToGroupOrder(MemberOrder member) {
        if (member == null) {
            throw new BadRequestException("Error! Method AddMemberToGroupOrder parameter 'MemberOrder' is null");
        }
        if (isMemberInGroupOrder(member)) {
            members.replace(member.getEmail(), member);
        } else {
            members.put(member.getEmail(), member);
        }

        fullPrice = orderFullPrice();
    }

    public void removeMemberFromOrder(MemberOrder memberOrder) {
        if (isMemberInGroupOrder(memberOrder)) {
            members.remove(memberOrder.getEmail());
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = members.values().stream()
                .map(MemberOrder::getProducts)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return ProductsCounter.getAllGroupingProducts(products);
    }

    private int orderFullPrice() {
        return members.values().stream()
                .mapToInt(MemberOrder::getFullPrice)
                .sum();
    }
}
