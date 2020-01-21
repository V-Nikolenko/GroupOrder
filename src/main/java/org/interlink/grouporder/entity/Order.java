package org.interlink.grouporder.entity;

import org.interlink.grouporder.exceptions.IncorrectRestaurantException;

import java.util.LinkedList;
import java.util.List;

public class Order {
    private List<OrderMember> orderMembers = new LinkedList<>();
    private List<Product> completeOrder = new LinkedList<>();

    public void addMemberToList(OrderMember member) {
        if (orderMembers.isEmpty() || member.getRestaurant() == orderMembers.get(0).getRestaurant()) {
            orderMembers.add(member);
        } else {
            throw new IncorrectRestaurantException("Invalid restaurant");
        }
    }

    public List<OrderMember> getOrderMembers() {
        return orderMembers;
    }

    public List<Product> getAllProducts() {
        for (OrderMember member : orderMembers) {
            completeOrder.addAll(member.getProducts());
        }
        return completeOrder;
    }
}
