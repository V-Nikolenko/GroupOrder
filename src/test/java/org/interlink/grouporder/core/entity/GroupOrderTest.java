package org.interlink.grouporder.core.entity;

import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class GroupOrderTest {

    @Test
    public void getAllProductsTest() {
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
