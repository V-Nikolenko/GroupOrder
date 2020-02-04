package org.interlink.grouporder.misteram.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import org.interlink.grouporder.core.entity.view.GroupOrderView;
import org.interlink.grouporder.core.handler.ExceptionsHandler;
import org.interlink.grouporder.misteram.entity.GroupOrderDTO;
import org.interlink.grouporder.misteram.entity.MemberOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    @JsonView(GroupOrderView.Basic.class)
    public ResponseEntity createGroupOrder(@RequestBody GroupOrderDTO newGroupOrderDTO) {
        try {
            String restaurantName = newGroupOrderDTO.getRestaurantName();
            String restaurantUrl = newGroupOrderDTO.getRestaurantUrl();

            return ResponseEntity.ok("Death end!");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @PostMapping("{code}/connect")
    public ResponseEntity connectToGroupOrder(@PathVariable("code") String code) {
        try {
            String orderLink = "someUrl" + "?code=" + "someCode";
            return ResponseEntity.ok(orderLink);
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @JsonView(GroupOrderView.Basic.class)
    @PostMapping("/{code}/add-member-order")
    public ResponseEntity addMemberToOrder(@PathVariable("code") String code, @RequestBody MemberOrderDTO newMemberOrderDTO) {
        try {
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @GetMapping("/{code}/show-group-order")
    public ResponseEntity showGroupOrder(@PathVariable("code") String code) {
        try {
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @GetMapping("/{code}/form-group-order")
    public ResponseEntity formGroupOrder(@PathVariable("code") String code) {
        try {
            return ResponseEntity.ok("Success");
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

    @PutMapping("/{code}/lock-group-order")
    public ResponseEntity lockGroupOrder(@PathVariable("code") String code) {
        try {
            return ResponseEntity.ok("Order " + code + " is locked!");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @DeleteMapping("/{code}/lock-group-order")
    public ResponseEntity unlockGroupOrder(@PathVariable("code") String code) {
        try {
            return ResponseEntity.ok("Order " + code + " is unlocked!");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }
}
