package org.interlink.grouporder.order;


import com.google.gson.JsonNull;
import org.interlink.grouporder.entity.Order;
import org.interlink.grouporder.entity.Product;
import org.interlink.grouporder.utils.JsonEncoder;
import org.interlink.grouporder.utils.OrderCodeGenerator;
import org.interlink.grouporder.utils.OrderStorage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {
    private String key;

    @PostMapping
    public void createNewOrder(@RequestBody String json) {
        Order order = new Order();
        OrderStorage orderStorage = new OrderStorage();
        OrderCodeGenerator orderCodeGenerator = new OrderCodeGenerator();

        key = orderCodeGenerator.generateOrderCode();

        orderStorage.addOrder(key, order);
    }

    @GetMapping
    public String sendOrderCode() {
        return key;
    }
}

