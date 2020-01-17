package org.interlink.grouporder.order;


import org.interlink.grouporder.entity.Order;
import org.interlink.grouporder.utils.OrderCodeGenerator;
import org.interlink.grouporder.utils.OrderStorage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    @PostMapping
    public String createNewOrder(@RequestBody String json) {
        Order order = new Order();
        OrderStorage orderStorage = new OrderStorage();
        OrderCodeGenerator orderCodeGenerator = new OrderCodeGenerator();

        String key = orderCodeGenerator.generateOrderCode();

        orderStorage.addOrder(key, order);

        return key;
    }
}

