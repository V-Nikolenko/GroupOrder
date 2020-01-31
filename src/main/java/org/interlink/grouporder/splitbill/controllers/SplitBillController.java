package org.interlink.grouporder.splitbill.controllers;


import org.interlink.grouporder.core.data.DataStorage;
import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.handler.ExceptionsHandler;
import org.interlink.grouporder.splitbill.entity.OrderCheckDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.interlink.grouporder.splitbill.SplitBillMapper.map;

@RestController
public class SplitBillController {

    @GetMapping("/orders/{code}/group-order-debt")
    public ResponseEntity formGroupOrder(@PathVariable("code") String code) {
        try {
            GroupOrder groupOrder = DataStorage.getGroupOrder(code);
            return ResponseEntity.ok(map(groupOrder, new OrderCheckDTO()));
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }
}