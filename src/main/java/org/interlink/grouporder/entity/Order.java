package org.interlink.grouporder.entity;

import org.interlink.grouporder.exceptions.IncorrectRestaurantException;

import java.util.LinkedList;
import java.util.List;

public class Order {
    private List<OrderMember> orderMembers = new LinkedList<>();
    private List<Product> completeOrder = new LinkedList<>();
    private String restaurant;

    public Order(String restaurant) {
        this.restaurant = restaurant;
    }

    public void addMemberToList(OrderMember member) {
        if (member.getRestaurant().equals(restaurant)) {
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
