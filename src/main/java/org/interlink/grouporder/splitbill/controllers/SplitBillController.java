package org.interlink.grouporder.splitbill.controllers;


import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.core.handler.ExceptionsHandler;
import org.interlink.grouporder.core.service.GroupOrderService;
import org.interlink.grouporder.core.service.MemberOrderService;
import org.interlink.grouporder.misteram.MisterAmMapper;
import org.interlink.grouporder.splitbill.SplitBillMapper;
import org.interlink.grouporder.splitbill.entity.OrderCheckDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SplitBillController {

    private GroupOrderService groupOrderService;
    private MemberOrderService memberOrderService;

    @Autowired
    public SplitBillController(GroupOrderService groupOrderService, MemberOrderService memberOrderService) {
        this.groupOrderService = groupOrderService;
        this.memberOrderService = memberOrderService;
    }

    @GetMapping("/orders/{code}/group-order-debt")
    public ResponseEntity formGroupOrder(@PathVariable("code") String code) {
        try {
            GroupOrder groupOrder = groupOrderService.getGroupOrder(code);
            List<MemberOrder> members = memberOrderService.findAllMembers(code);

            return ResponseEntity.ok(SplitBillMapper.map(groupOrder, members, new OrderCheckDTO()));
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }
}