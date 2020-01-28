package org.interlink.grouporder.core.entity;

import org.interlink.grouporder.core.data.DataStorage;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class GroupOrderTest {
    private static final Product ITEM_FIRST = new Product(1, "pizza", new BigDecimal("200.0"), 1);
    private static final Product ITEM_SECOND = new Product(2, "burger", new BigDecimal("50.0"), 2);

    private void fillGroupOrder(List<Product> products, MemberOrder memberOrder, GroupOrder groupOrder) {
        memberOrder.setName("Vasya");
        memberOrder.setUrl("misterAm");
        memberOrder.setProducts(products);
        List<MemberOrder> memberOrdersList = new ArrayList<>();
        memberOrdersList.add(memberOrder);
        groupOrder.setMembers(memberOrdersList);
    }


    @Test
    public void When_MemberNotExistInInGroupOrder_Expect_AddInGroupOrder() {
        List<Product> productsVasya = new ArrayList<>();
        Map<String, GroupOrder> expectedResult = new LinkedHashMap<>();
        MemberOrder memberOrder1 = new MemberOrder();
        MemberOrder memberOrder2 = new MemberOrder();
        GroupOrder groupOrder = new GroupOrder("12345");

        fillGroupOrder(productsVasya, memberOrder1, groupOrder);

        DataStorage.getOrders().put("12345", groupOrder);
        expectedResult.put("12345", groupOrder);
        groupOrder.addMemberToGroupOrder(memberOrder2);

        assertEquals(DataStorage.getOrders(), expectedResult);
    }

    @Test
    public void When_MemberExistInInGroupOrder_Expect_AddInGroupOrder() {
        List<Product> productsVasya = new ArrayList<>();
        Map<String, GroupOrder> expectedResult = new LinkedHashMap<>();
        MemberOrder memberOrder1 = new MemberOrder();
        MemberOrder memberOrder2 = new MemberOrder();
        GroupOrder groupOrder = new GroupOrder("12345");

        fillGroupOrder(productsVasya, memberOrder1, groupOrder);
        fillGroupOrder(productsVasya, memberOrder2, groupOrder);

        DataStorage.getOrders().put("12345", groupOrder);
        expectedResult.put("12345", groupOrder);
        groupOrder.addMemberToGroupOrder(memberOrder2);

        assertEquals(DataStorage.getOrders(), expectedResult);
    }

    @Test
    public void When_MembersIsEmpty_Expect_ReturnEmptyListProducts() {

    }
    @Test
    public void When_MembersIsNotEmpty_Expect_ReturnEmptyListProducts() {
        GroupOrder groupOrder = new GroupOrder("12345");
        List<Product> listProductsAct = new ArrayList<>();
        List<Product> expectedResult = new ArrayList<>();

        Product item1Exp = new Product(3, "soup", new BigDecimal("20.0"), 1);
        Product item2Exp = new Product(4, "ffffff", new BigDecimal("20.0"), 7);

        Product item4Act = new Product(3, "soup", new BigDecimal("20.0"), 1);
        Product item5Act = new Product(4, "ffffff", new BigDecimal("20.0"), 5);
        Product item7Act = new Product(4, "ffffff", new BigDecimal("20.0"), 1);
        Product item8Act = new Product(4, "ffffff", new BigDecimal("20.0"), 1);

        expectedResult.add(item1Exp);
        expectedResult.add(item2Exp);
        listProductsAct.add(item4Act);
        listProductsAct.add(item5Act);
        listProductsAct.add(item7Act);
        listProductsAct.add(item8Act);

        MemberOrder memberOrder = new MemberOrder();
        memberOrder.setName("Vasya");
        memberOrder.setUrl("misterAm");
        memberOrder.setProducts(listProductsAct);

        groupOrder.getMembers().add(memberOrder);
        List<Product> actualResult = groupOrder.getAllProducts();

        int i = 0;
        for (Product prod1 : actualResult) {
            for (i = i; i < expectedResult.size(); i++) {
                assertEquals(prod1.getId(), expectedResult.get(i).getId());
                assertEquals(prod1.getName(), expectedResult.get(i).getName());
                assertEquals(prod1.getCount(), expectedResult.get(i).getCount());
                return;
            }
        }
    }

    @Test
    public void Expect_ReturnNotNull_When_CallGetAllProducts() {
        GroupOrder groupOrder = new GroupOrder("12345");

        List<Product> actualResult = groupOrder.getAllProducts();

        assertNotNull(actualResult);
    }

    @Test
    public void Expect_ReturnProductCounter_When_CallGetAllProducts() {
        GroupOrder groupOrder = new GroupOrder("12345");

        List<Product> actualResult = groupOrder.getAllProducts();
        boolean expectedResult = actualResult instanceof List;

        assertTrue(expectedResult);
    }
}
