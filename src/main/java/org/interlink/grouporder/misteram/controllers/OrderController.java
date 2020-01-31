package org.interlink.grouporder.misteram.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import org.interlink.grouporder.core.data.DataStorage;
import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.core.entity.view.GroupOrderView;
import org.interlink.grouporder.core.handler.ExceptionsHandler;
import org.interlink.grouporder.misteram.entity.FullOrderItemsDTO;
import org.interlink.grouporder.misteram.entity.MemberOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.interlink.grouporder.misteram.MisterAmMapper.map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    @JsonView(GroupOrderView.Basic.class)
    public ResponseEntity createGroupOrder() {
        try {
            String code = DataStorage.addGroupOrder();
            return ResponseEntity.ok(code);
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @PostMapping("{code}/connect")
    public ResponseEntity connectToGroupOrder(@PathVariable("code") String code) {
        try {
            if (DataStorage.isContains(code)) {
                return ResponseEntity.ok("Success!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @JsonView(GroupOrderView.Basic.class)
    @PostMapping("/{code}/add-member-order")
    public ResponseEntity addMemberToOrder(@PathVariable("code") String code, @RequestBody MemberOrderDTO newMemberOrderDTO) {
        try {
            MemberOrder memberOrder = map(newMemberOrderDTO, new MemberOrder());

            DataStorage.getGroupOrder(code).addMemberToGroupOrder(memberOrder);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            e.printStackTrace();
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
            return ResponseEntity.ok(map(groupOrder, new FullOrderItemsDTO()));
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @PostMapping("/{code}/remove-from-order")
    public ResponseEntity removeMemberFromOrder(@PathVariable("code") String code, @RequestBody MemberOrderDTO newMemberOrderDTO) {
        try {
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @PostMapping("/{code}/lock-group-order")
    public ResponseEntity LockGroupOrder(@PathVariable("code") String code) {
        try {
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }
}
