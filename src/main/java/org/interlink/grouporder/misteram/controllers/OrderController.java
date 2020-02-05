package org.interlink.grouporder.misteram.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.core.entity.view.GroupOrderView;
import org.interlink.grouporder.core.handler.ExceptionsHandler;
import org.interlink.grouporder.core.service.GroupOrderService;
import org.interlink.grouporder.core.service.MemberOrderService;
import org.interlink.grouporder.misteram.MisterAmMapper;
import org.interlink.grouporder.misteram.entity.GroupOrderDTO;
import org.interlink.grouporder.misteram.entity.MemberOrderDTO;
import org.interlink.grouporder.misteram.entity.StringResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private GroupOrderService groupOrderService;
    private MemberOrderService memberOrderService;

    @Autowired
    public OrderController(GroupOrderService groupOrderService, MemberOrderService memberOrderService) {
        this.groupOrderService = groupOrderService;
        this.memberOrderService = memberOrderService;
    }

    @PostMapping
    @JsonView(GroupOrderView.Basic.class)
    public ResponseEntity createGroupOrder(@RequestBody GroupOrderDTO newGroupOrderDTO) {
        try {
            String code = groupOrderService.generateUniqueCode();

            String restaurantId = newGroupOrderDTO.getRestaurantId();
            String restaurantName = newGroupOrderDTO.getRestaurantName();
            String restaurantUrl = newGroupOrderDTO.getRestaurantUrl();

            groupOrderService.addGroupOrder(code, restaurantId, restaurantName, restaurantUrl);

            return ResponseEntity.ok(code);
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @PostMapping("{code}/connect")
    public ResponseEntity connectToGroupOrder(@PathVariable("code") String code) {
        try {
            GroupOrder groupOrder = groupOrderService.getGroupOrder(code);
            String orderLink = groupOrder.getRestaurantUrl() + "?code=" + code;

            return ResponseEntity.ok(MisterAmMapper.map(orderLink, new StringResultDTO()));
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @JsonView(GroupOrderView.Basic.class)
    @PostMapping("/{code}/add-member-order")
    public ResponseEntity addMemberToOrder(@PathVariable("code") String code, @RequestBody MemberOrderDTO newMemberOrderDTO) {
        try {

            String name = newMemberOrderDTO.getName();
            String email = newMemberOrderDTO.getEmail();
            String url = newMemberOrderDTO.getUrl();
            String items = newMemberOrderDTO.getItems();
            int companyId = newMemberOrderDTO.getCompanyId();
            int fullPrice = newMemberOrderDTO.getFullPrice();

            memberOrderService.addMemberToGroupOrder(name, email, url, items, companyId, fullPrice);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @GetMapping("/{code}/show-group-order")
    public ResponseEntity showGroupOrder(@PathVariable("code") String code) {
        try {
            List<MemberOrder> members = memberOrderService.findAllMembers(code);

            return ResponseEntity.ok(members);
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @GetMapping("/{code}/form-group-order")
    public ResponseEntity formGroupOrder(@PathVariable("code") String code) {
        try {
            List<MemberOrder> members = memberOrderService.findAllMembers(code);

            return ResponseEntity.ok(members);
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @PostMapping("/{code}/remove-from-order")
    public ResponseEntity removeMemberFromOrder(@PathVariable("code") String code, @RequestBody MemberOrderDTO newMemberOrderDTO) {
        try {
            String email = newMemberOrderDTO.getEmail();

            memberOrderService.removeMemberFromOrder(code, email);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @PutMapping("/{code}/lock-group-order")
    public ResponseEntity lockGroupOrder(@PathVariable("code") String code) {
        try {
            GroupOrder groupOrder = groupOrderService.getGroupOrder(code);

            groupOrderService.unlock(groupOrder);

            return ResponseEntity.ok("Order " + code + " is unlocked!");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @DeleteMapping("/{code}/lock-group-order")
    public ResponseEntity unlockGroupOrder(@PathVariable("code") String code) {
        try {
            GroupOrder groupOrder = groupOrderService.getGroupOrder(code);

            groupOrderService.lock(groupOrder);

            return ResponseEntity.ok("Order " + code + " is locked!");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }
}
