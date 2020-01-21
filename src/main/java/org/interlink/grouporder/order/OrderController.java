package org.interlink.grouporder.order;


import org.interlink.grouporder.entity.Order;
import org.interlink.grouporder.entity.OrderStorage;
import org.interlink.grouporder.utils.OrderCodeGenerator;
import org.interlink.grouporder.utils.TimerOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @PostMapping
    public ResponseEntity createNewOrder(@RequestHeader Integer restaurant) {
        Order order = new Order(restaurant);

        OrderCodeGenerator orderCodeGenerator = new OrderCodeGenerator();
        String key = orderCodeGenerator.generateOrderCode();
        TimerOrder.runTimer(key);
        OrderStorage.addOrder(key, order);

        return ResponseEntity.ok(key);
    }

    @PostMapping("/{key}")
    public ResponseEntity connectToOrder(@PathVariable("key") String key) {
        if (OrderStorage.isContains(key)) {
            return ResponseEntity.ok("Connection success!");
        } else {
            return ResponseEntity.badRequest().body("Connection is failed. Order with key [" + key + "] is not found!");
        }
    }
}

