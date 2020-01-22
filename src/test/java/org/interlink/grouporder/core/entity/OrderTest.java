package org.interlink.grouporder.core.entity;

import org.interlink.grouporder.exceptions.IncorrectRestaurantException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.*;

public class OrderTest {

    private Order order;
    private static final String MEMBER_NAME = "TestFood";
    private static final BigDecimal PRICE = new BigDecimal("14.10");
    private static final Product PRODUCT = new Product(1,1, "", "", "", "", 0 );

    @BeforeMethod
    public void setUp() {
        order = new Order(231);
    }


    @Test
    public void When_OrderHasMembers_Expect_GetOrderMemberResultAsNotNullAndNotEmpty() {
        List<Product> products = new LinkedList<>();
        products.add(PRODUCT);

        MemberOrder memberOrder = new MemberOrder(MEMBER_NAME, 231, products, PRICE);
        order.addMemberToList(memberOrder);

        assertNotNull(order.getOrderMembers());
        assertFalse(order.getOrderMembers().isEmpty());
    }

    @Test
    public void When_OrderDoNotHasAnyMembers_Expect_GetOrderMemberResultAsNotNullButAsEmpty() {
        assertNotNull(order.getOrderMembers());
        assertTrue(order.getOrderMembers().isEmpty());
    }

    @Test(expectedExceptions = IncorrectRestaurantException.class)
    public void When_OrderMembersRestaurantValueIsIncorrect_Expect_AddOrderMemberResultAsException() {
        List<Product> products = new LinkedList<>();
        products.add(PRODUCT);
        MemberOrder incorrectMember = new MemberOrder(MEMBER_NAME, 132, products, PRICE);

        order.addMemberToList(incorrectMember);
    }


    @Test
    public void When_OrderMemberHasProducts_Expect_GetAllProductsResultAsNotNullAndNotEmpty() {
        List<Product> products = new LinkedList<>();
        products.add(PRODUCT);
        MemberOrder memberOrder = new MemberOrder(MEMBER_NAME, 231, products, PRICE);
        order.addMemberToList(memberOrder);

        assertNotNull(order.getAllProducts());
        assertFalse(order.getAllProducts().isEmpty());
    }

    @Test
    public void When_OrderMemberDoNotHasAnyProducts_Expect_GetAllProductsResultAsNotNullButAsEmpty() {
        List<Product> products = Collections.emptyList();
        MemberOrder memberOrder = new MemberOrder(MEMBER_NAME, 231, products, PRICE);
        order.addMemberToList(memberOrder);

        assertNotNull(order.getAllProducts());
        assertTrue(order.getAllProducts().isEmpty());
    }

    @Test
    public void When_OrderMemberProductsAreNull_Expect_GetAllProductsResultAsEmpty() {
        MemberOrder memberOrder = new MemberOrder(MEMBER_NAME, 231, null, PRICE);
        order.addMemberToList(memberOrder);

        assertTrue(order.getAllProducts().isEmpty());
    }

}