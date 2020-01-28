package org.interlink.grouporder.core.data;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.core.entity.Product;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DataStorageTest {

    @Test
    public void addGroupOrderTest() {
        Map<String, GroupOrder> expectedResult = new LinkedHashMap<>();

        DataStorage.addGroupOrder("12345", null);
        expectedResult.put("12345", null);

        assertEquals(expectedResult, DataStorage.getOrders());
    }

    @Test
    public void removeGroupOrderTest() {
        Map<String, GroupOrder> expectedResult = new LinkedHashMap<>();
        DataStorage.getOrders().put("12345", null);

        DataStorage.removeGroupOrder("12345");

        assertEquals(expectedResult, DataStorage.getOrders());
    }

    @Test
    public void getGroupOrderTest() {
        Product item1Act = new Product(1, "pizza", new BigDecimal("200.0"), 1);
        Product item2Act = new Product(2, "burger", new BigDecimal("50.0"), 2);
        List<Product> productsVasya = new ArrayList<>();
        productsVasya.add(item1Act);
        productsVasya.add(item2Act);

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

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void When_KeyNotExistInDataStorage_Expect_IllegalArgumentException() {
        DataStorage.getOrders().put("12345", null);
        DataStorage.getGroupOrder("12345");
    }

    @Test
    public void addMemberToOrderTest() {
        Map<String, GroupOrder> expectedResult = new LinkedHashMap<>();
        Product item1Act = new Product(1, "pizza", new BigDecimal("200.0"), 1);
        Product item2Act = new Product(2, "burger", new BigDecimal("50.0"), 2);
        List<Product> productsVasya = new ArrayList<>();
        productsVasya.add(item1Act);
        productsVasya.add(item2Act);
        MemberOrder memberOrder = new MemberOrder();
        memberOrder.setName("Vasya");
        memberOrder.setUrl("misterAm");
        memberOrder.setProducts(productsVasya);
        List<MemberOrder> memberOrdersList = new ArrayList<>();
        memberOrdersList.add(memberOrder);
        GroupOrder groupOrder = new GroupOrder("12345");
        groupOrder.setMembers(memberOrdersList);
        DataStorage.getOrders().put("12345", groupOrder);

        expectedResult.put("12345", groupOrder);
        DataStorage.getGroupOrder("12345").addMemberToGroupOrder(memberOrder);

        assertEquals(DataStorage.getOrders(), expectedResult);
    }

    @Test
    public void isContainsTest() {
        Product item1Act = new Product(1, "pizza", new BigDecimal("200.0"), 1);
        Product item2Act = new Product(2, "burger", new BigDecimal("50.0"), 2);
        List<Product> productsVasya = new ArrayList<>();
        productsVasya.add(item1Act);
        productsVasya.add(item2Act);
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
}
