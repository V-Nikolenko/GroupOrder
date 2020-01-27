package ord.interlink.grouporder.core.utils;

import org.interlink.grouporder.core.entity.Product;
import org.interlink.grouporder.core.utils.ProductsCounter;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductsCounterTest {

    @Test
    public void getAllGroupingProductsTest() {
        ProductsCounter productsCounter = new ProductsCounter();
        List<Product> listProductsAct = new ArrayList<>();
        List<Product> expectedResult = new ArrayList<>();

        Product item1Act = new Product(1, "pizza", new BigDecimal("200.0"), 1);
        Product item2Act = new Product(2, "burger", new BigDecimal("50.0"), 2);
        Product item3Act = new Product(3, "soup", new BigDecimal("20.0"), 1);
        Product item4Act = new Product(3, "soup", new BigDecimal("20.0"), 1);
        Product item5Act = new Product(4, "ffffff", new BigDecimal("20.0"), 5);
        Product item7Act = new Product(4, "ffffff", new BigDecimal("20.0"), 1);
        Product item8Act = new Product(4, "ffffff", new BigDecimal("20.0"), 1);

        Product item1Exp = new Product(1, "pizza", new BigDecimal("200.0"), 1);
        Product item2Exp = new Product(2, "burger", new BigDecimal("50.0"), 2);
        Product item3Exp = new Product(3, "soup", new BigDecimal("20.0"), 2);
        Product item5Exp = new Product(4, "ffffff", new BigDecimal("20.0"), 7);

        listProductsAct.add(item1Act);
        listProductsAct.add(item2Act);
        listProductsAct.add(item3Act);
        listProductsAct.add(item4Act);
        listProductsAct.add(item5Act);
        listProductsAct.add(item7Act);
        listProductsAct.add(item8Act);

        expectedResult.add(item1Exp);
        expectedResult.add(item2Exp);
        expectedResult.add(item3Exp);
        expectedResult.add(item5Exp);

        List<Product> actualResult = productsCounter.getAllGroupingProducts(listProductsAct);
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
}
