package org.interlink.grouporder.order;


import org.interlink.grouporder.entity.Order;
import org.interlink.grouporder.entity.OrderStorage;
import org.interlink.grouporder.utils.JsonEncoder;
import org.interlink.grouporder.utils.OrderCodeGenerator;
import org.interlink.grouporder.utils.TimerOrder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @PostMapping
    public String createNewOrder(@RequestHeader Integer restaurant) {
        Order order = new Order(restaurant);

        OrderCodeGenerator orderCodeGenerator = new OrderCodeGenerator();

        String key = orderCodeGenerator.generateOrderCode();

        TimerOrder.runTimer(key);

        OrderStorage.addOrder(key, order);

        return JsonEncoder.encode(key);
    }

    @PostMapping("/{key}")
    public String connectToOrder(@PathVariable("key") String key) {
        return JsonEncoder.encode(OrderStorage.isContains(key));
    }

}

