package org.interlink.grouporder.order;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

    @PostMapping
    public void createNewOrder(@RequestBody String json){
        System.out.println(json);
    }
}
