package org.interlink.grouporder.misteram.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.core.entity.Product;
import org.interlink.grouporder.core.entity.view.GroupOrderView;
import org.interlink.grouporder.core.handler.ExceptionsHandler;
import org.interlink.grouporder.core.service.GroupOrderService;
import org.interlink.grouporder.core.service.MemberOrderService;
import org.interlink.grouporder.misteram.MisterAmMapper;
import org.interlink.grouporder.misteram.entity.GroupOrderDTO;
import org.interlink.grouporder.misteram.entity.MemberOrderDTO;
import org.interlink.grouporder.misteram.entity.ShowOrderDTO;
import org.interlink.grouporder.misteram.entity.StringResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
            GroupOrder groupOrder = MisterAmMapper.map(code, new GroupOrder(), newGroupOrderDTO);
            groupOrderService.saveGroupOrder(groupOrder);

            return ResponseEntity.ok(code);
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @PostMapping("{code}/connect")
    public ResponseEntity connectToGroupOrder(@PathVariable("code") String code) {
        try {
            if (!groupOrderService.getGroupOrder(code).isLocked()) {
                GroupOrder groupOrder = groupOrderService.getGroupOrder(code);
                String url = groupOrder.getRestaurantUrl() + "?code=" + code;
                String name = groupOrder.getRestaurantName();
                return ResponseEntity.ok(MisterAmMapper.map(url, name, new StringResultDTO()));
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Unable connect to group order because order is locked");
            }
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @JsonView(GroupOrderView.Basic.class)
    @PostMapping("/{code}/add-member-order")
    public ResponseEntity addMemberToOrder(@PathVariable("code") String code, @RequestBody MemberOrderDTO newMemberOrderDTO) {
        try {
            if (!groupOrderService.getGroupOrder(code).isLocked()) {
                MemberOrder memberOrder = MisterAmMapper.map(new MemberOrder(), newMemberOrderDTO);
                GroupOrder groupOrder = groupOrderService.getGroupOrder(code);
                memberOrder.setGroupOrder(groupOrder);

                memberOrderService.saveMemberToGroupOrder(memberOrder);

                return ResponseEntity.ok(memberOrder.getId());
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Unable to add new member because order is locked");
            }
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @GetMapping("/{code}/show-group-order")
    public ResponseEntity showGroupOrder(@PathVariable("code") String code) {
        try {
            List<MemberOrder> members = memberOrderService.findAllMembers(code);
            BigDecimal fullPrice = memberOrderService.sumFullPrice(code);

            ShowOrderDTO newShowOrderDTO = new ShowOrderDTO();
            newShowOrderDTO.setFullPrice(fullPrice);
            newShowOrderDTO.setMembers(members);

            return ResponseEntity.ok(newShowOrderDTO);
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @GetMapping("/{code}/form-group-order")
    public ResponseEntity formGroupOrder(@PathVariable("code") String code) {
        try {
            List<MemberOrder> members = memberOrderService.findAllMembers(code);
            BigDecimal fullPrice = memberOrderService.sumFullPrice(code);

            ShowOrderDTO newShowOrderDTO = new ShowOrderDTO();
            newShowOrderDTO.setFullPrice(fullPrice);
            newShowOrderDTO.setMembers(members);

            return ResponseEntity.ok(newShowOrderDTO);
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @DeleteMapping("/{code}/{id}")
    public ResponseEntity removeMemberFromOrder(@PathVariable("code") String code, @PathVariable("id") Integer id) {
        try {
            if (!groupOrderService.getGroupOrder(code).isLocked()) {
                memberOrderService.deleteMemberFromOrder(id);
                return ResponseEntity.ok("Success");
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Cannot delete member because order is locked");
            }
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @PutMapping("/{code}/lock-group-order")
    public ResponseEntity lockGroupOrder(@PathVariable("code") String code) {
        try {
            GroupOrder groupOrder = groupOrderService.getGroupOrder(code);

            groupOrderService.lock(groupOrder);

            return ResponseEntity.ok("Order " + code + " is locked!");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }

    @DeleteMapping("/{code}/lock-group-order")
    public ResponseEntity unlockGroupOrder(@PathVariable("code") String code) {
        try {
            GroupOrder groupOrder = groupOrderService.getGroupOrder(code);

            groupOrderService.unlock(groupOrder);

            return ResponseEntity.ok("Order " + code + " is unlocked!");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }
}
