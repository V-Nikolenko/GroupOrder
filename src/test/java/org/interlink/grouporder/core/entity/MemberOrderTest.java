package org.interlink.grouporder.core.entity;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.*;

public class MemberOrderTest {

    MemberOrder memberOrder;
    private static final String MEMBER_NAME = "TestFood";
    private static final int RESTAURANT = 213;
    private static final BigDecimal PRICE = new BigDecimal("14.10");
    private List<Product> products = new LinkedList<>();


    @Test
    public void When_OrderMemberHasCorrectParameters_Expect_ResultsAsEquals() {
        memberOrder = new MemberOrder(MEMBER_NAME, RESTAURANT, products, PRICE);

        assertEquals(memberOrder.getName(), MEMBER_NAME);
        assertEquals(memberOrder.getRestaurant(), RESTAURANT);
        assertEquals(memberOrder.getProducts(), products);
        assertEquals(memberOrder.getPrice(), PRICE);
    }

    @Test
    public void When_OrderMemberHasIncorrectName_Expect_ResultsAsNotNullButAsEmptyString() {
        memberOrder = new MemberOrder(null, RESTAURANT, products, PRICE);
    }

    @Test
    public void When_OrderMemberHasIncorrectRestaurant_Expect_ResultsAsNotNullButAsEmptyString() {
        memberOrder = new MemberOrder(MEMBER_NAME, RESTAURANT, products, PRICE);
    }

    @Test
    public void When_OrderMemberHasIncorrectProducts_Expect_ResultsAsNotNullButAsEmptyString() {
        memberOrder = new MemberOrder(MEMBER_NAME, RESTAURANT, products, PRICE);
    }

    @Test
    public void When_OrderMemberHasIncorrectPrice_Expect_ResultsAsNotNullButAsEmptyString() {
        memberOrder = new MemberOrder(MEMBER_NAME, RESTAURANT, products, PRICE);
    }
}