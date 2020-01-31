package org.interlink.grouporder.core.entity;

import org.interlink.grouporder.core.exceptions.BadRequestException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;

public class GroupOrderTest {
    private static final Product ITEM_FIRST = new Product(1, "pizza", new BigDecimal("200.0"), 1);
    private static final Product ITEM_SECOND = new Product(2, "burger", new BigDecimal("50.0"), 2);
    private static final Product ITEM_THIRD = new Product(3, "baton", new BigDecimal("10.0"), 2);
    private static final List<Product> PRODUCTS_VASYA = List.of(ITEM_FIRST, ITEM_SECOND);
    private static final List<Product> PRODUCTS_KOLYA = List.of(ITEM_FIRST, ITEM_SECOND, ITEM_THIRD);
    private static final List<Product> PRODUCTS_MISHA = Collections.emptyList();

    private GroupOrder groupOrder;
    private MemberOrder memberOrder1;
    private MemberOrder memberOrder2;
    private MemberOrder memberOrder3;

    @BeforeMethod
    private void fillGroupOrder() {
        memberOrder1 = new MemberOrder("Vasya", "qweqweqwe", "qweqweqweqwe", PRODUCTS_VASYA, 2000);
        memberOrder2 = new MemberOrder("Kolya", "asdfasdf", "ghkjghjfghj", PRODUCTS_KOLYA, 1000);
        memberOrder3 = new MemberOrder("Misha", "zxcvzxcv", "uioluiolu", PRODUCTS_MISHA, 0);

        groupOrder = new GroupOrder("12345");
    }

    @Test
    public void When_AddValidMemberToGroupOrder_Expect_ResultIsEmptyAsFalse() {
        groupOrder.addMemberToGroupOrder(memberOrder1);

        assertFalse(groupOrder.getMembers().isEmpty());
    }

    @Test(expectedExceptions = BadRequestException.class)
    public void When_AddNullMemberToGroupOrder_Expect_ResultAsBadRequestException() {
        groupOrder.addMemberToGroupOrder(null);
    }

    @Test
    public void When_AddSameValidMemberToGroupOrder_Expect_ReplaceOldMemberNewOne() {
        groupOrder.addMemberToGroupOrder(memberOrder1);
        groupOrder.addMemberToGroupOrder(memberOrder1);

        assertEquals(groupOrder.getMembers().size(), 1);
    }

    @Test
    public void When_AddNotSameValidMemberToGroupOrder_Expect_ResultAsAddNewMember() {
        groupOrder.addMemberToGroupOrder(memberOrder1);
        groupOrder.addMemberToGroupOrder(memberOrder2);

        assertEquals(groupOrder.getMembers().size(), 2);
    }

    @Test
    public void When_GetAllProductsFromValidMember_Expect_ResultIsEmptyAsFalse() {
        groupOrder.addMemberToGroupOrder(memberOrder1);

        assertFalse(groupOrder.getAllProducts().isEmpty());
    }

    @Test
    public void When_GetAllProductsFromEmptyProductList_Expect_ResultIsEmptyAsTrue() {
        groupOrder.addMemberToGroupOrder(memberOrder3);

        assertTrue(groupOrder.getAllProducts().isEmpty());
    }

    @Test
    public void When_GetAllProductsFromMultipleMembers_Expect_ResultOfProductsIsThree() {
        groupOrder.addMemberToGroupOrder(memberOrder1);
        groupOrder.addMemberToGroupOrder(memberOrder2);

        assertEquals(groupOrder.getAllProducts().size(), 3);
    }

    @Test
    public void When_GetFullPriceFromMultipleMembers_Expect_ResultAsTrue() {
        groupOrder.addMemberToGroupOrder(memberOrder1);
        groupOrder.addMemberToGroupOrder(memberOrder2);

        assertEquals(groupOrder.getFullPrice(), 3000);
    }

    @Test
    public void When_RemoveMemberFromGroupOrder_Expect_ResultIsEmptyAsTrue() {
        groupOrder.addMemberToGroupOrder(memberOrder1);

        groupOrder.removeMemberFromOrder(memberOrder1);

        assertTrue(groupOrder.getMembers().isEmpty());
    }

}
