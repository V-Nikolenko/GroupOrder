package org.interlink.grouporder.core.data;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.core.entity.Product;
import org.interlink.grouporder.core.exceptions.BadRequestException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class DataStorageTest {
    private static final Product ITEM_FIRST = new Product(1, "pizza", new BigDecimal("200.0"), 1);
    private static final Product ITEM_SECOND = new Product(2, "burger", new BigDecimal("50.0"), 2);

    @Test(expectedExceptions = BadRequestException.class)
    public void When_addNullGroupOrderToDataStorage_Expect_BadRequestException() {
        DataStorage.addGroupOrder("null", null);
    }

    @Test
    public void When_addNotNullGroupOrderToDataStorage_Expect_ResultAsTrue() {
        List<Product> productsVasya = new ArrayList<>();
        Map<String, GroupOrder> expectedResult = new LinkedHashMap<>();

        MemberOrder memberOrder = new MemberOrder();
        memberOrder.setName("Vasya");
        memberOrder.setUrl("misterAm");
        memberOrder.setProducts(productsVasya);
        List<MemberOrder> memberOrdersList = new ArrayList<>();
        memberOrdersList.add(memberOrder);

        GroupOrder groupOrder = new GroupOrder("12345");
        groupOrder.setMembers(memberOrdersList);

        DataStorage.addGroupOrder("12345", groupOrder);
        expectedResult.put("12345", groupOrder);

        assertEquals(expectedResult, DataStorage.getOrders());
    }

    @Test(expectedExceptions = BadRequestException.class)
    public void When_KeyIsNullRemoveGroupOrder_Expect_BadRequestException() {
        DataStorage.removeGroupOrder(null);
    }

    @Test
    public void When_KeyIsNotNullRemoveGroupOrder_Expect_RemoveGroupOrder() {
        Map<String, GroupOrder> expectedResult = new LinkedHashMap<>();
        DataStorage.getOrders().put("12345", null);

        DataStorage.removeGroupOrder("12345");

        assertEquals(expectedResult, DataStorage.getOrders());
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void When_KeyNotExistInDataStorage_Expect_IllegalArgumentException() {
        DataStorage.getOrders().put("12345", null);
        DataStorage.getGroupOrder("12345");
    }

    @Test
    public void When_KeyExistInDataStorage_Expect_ResultAsTrue() {
        List<Product> productsVasya = new ArrayList<>();
        productsVasya.add(ITEM_FIRST);
        productsVasya.add(ITEM_SECOND);

        MemberOrder memberOrder = new MemberOrder();
        memberOrder.setName("Vasya");
        memberOrder.setUrl("misterAm");
        memberOrder.setProducts(productsVasya);
        List<MemberOrder> memberOrdersList = new ArrayList<>();
        memberOrdersList.add(memberOrder);

        GroupOrder groupOrder = new GroupOrder("12345");
        groupOrder.setMembers(memberOrdersList);

        DataStorage.getOrders().put("12345", groupOrder);
        GroupOrder actualResult = DataStorage.getGroupOrder("12345");


        assertEquals(groupOrder, actualResult);
    }

    @Test
    public void When_KeyContainsKeyInDataStorage_Expect_ResultAsTrue() {
        List<Product> productsVasya = new ArrayList<>();
        productsVasya.add(ITEM_FIRST);
        productsVasya.add(ITEM_SECOND);
        MemberOrder memberOrder = new MemberOrder();
        memberOrder.setName("Vasya");
        memberOrder.setUrl("misterAm");
        memberOrder.setProducts(productsVasya);
        List<MemberOrder> memberOrdersList = new ArrayList<>();
        memberOrdersList.add(memberOrder);
        GroupOrder groupOrder = new GroupOrder("12345");
        groupOrder.setMembers(memberOrdersList);
        DataStorage.getOrders().put("12345", groupOrder);

        assertTrue(DataStorage.isContains("12345"));
    }

    @Test
    public void When_KeyContainsKeyInDataStorage_Expect_ResultAsFalse() {
        List<Product> productsVasya = new ArrayList<>();
        productsVasya.add(ITEM_FIRST);
        productsVasya.add(ITEM_SECOND);
        MemberOrder memberOrder = new MemberOrder();
        memberOrder.setName("Vasya");
        memberOrder.setUrl("misterAm");
        memberOrder.setProducts(productsVasya);
        List<MemberOrder> memberOrdersList = new ArrayList<>();
        memberOrdersList.add(memberOrder);
        GroupOrder groupOrder = new GroupOrder("12345");
        groupOrder.setMembers(memberOrdersList);
        DataStorage.getOrders().put("12345", groupOrder);

        assertFalse(DataStorage.isContains("54321"));
    }
}
