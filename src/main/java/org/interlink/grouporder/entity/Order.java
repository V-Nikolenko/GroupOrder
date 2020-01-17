package org.interlink.grouporder.entity;

import java.util.LinkedList;
import java.util.List;

public class Order {
    private List<OrderMember> orderMembers = new LinkedList<>();
    private List<Product> completeOrder = new LinkedList<>();

    public void addMemberToList(OrderMember member) {
        orderMembers.add(member);
    }

    public List<Product> getAllProducts() {
        for (OrderMember member : orderMembers) {
            completeOrder.addAll(member.getProducts());
        }
        return completeOrder;
    }
}
