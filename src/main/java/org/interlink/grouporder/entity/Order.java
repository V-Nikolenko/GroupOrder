package org.interlink.grouporder.entity;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Order {
    private List<Product> products = new LinkedList<>();
    private String orderCode;
    private BigDecimal totalPrice;

    public Order( String orderCode, BigDecimal totalPrice) {
        this.orderCode = orderCode;
        this.totalPrice = totalPrice;
    }

    public List<Product> getAllProducts() {
        return products;
    }


    public String getOrderCode() {
        return orderCode;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void addNewProduct(Product product){
        this.products.add(product);
    }

}
