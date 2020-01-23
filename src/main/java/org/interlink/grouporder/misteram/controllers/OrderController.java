package org.interlink.grouporder.misteram.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import org.interlink.grouporder.core.data.DataStorage;
import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.core.entity.view.GroupOrderView;
import org.interlink.grouporder.core.handler.ExceptionsHandler;
import org.interlink.grouporder.core.utils.OrderCodeGenerator;
import org.interlink.grouporder.misteram.MisterAmMapper;
import org.interlink.grouporder.misteram.entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    @JsonView(GroupOrderView.Basic.class)
    public GroupOrder createGroupOrder() {

        String code = OrderCodeGenerator.generateCode();
        GroupOrder groupOrder = new GroupOrder(code);
        DataStorage.addGroupOrder(code, groupOrder);

        return groupOrder;
    }

    @JsonView(GroupOrderView.Basic.class)
    @PostMapping("/{code}/add-order")
    public ResponseEntity addMemberToOrder(@PathVariable("code") String code, @RequestBody Order newOrder) {
        try {
            MemberOrder memberOrder = MisterAmMapper.map(newOrder, MemberOrder.class);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }


    @GetMapping("/{code}/show-group-order")
    public ResponseEntity showGroupOrder(@PathVariable("code") String code) {
        try {
            GroupOrder groupOrder = DataStorage.getGroupOrder(code);
            return ResponseEntity.ok(groupOrder);
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @GetMapping("/{code}/form-group-order")
    public ResponseEntity formGroupOrder(@PathVariable("code") String code) {
        try {
            GroupOrder groupOrder = DataStorage.getGroupOrder(code);
            return ResponseEntity.ok(MisterAmMapper.map(groupOrder, new Order()));
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }
}
