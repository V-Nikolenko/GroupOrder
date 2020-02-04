package org.interlink.grouporder.splitbill.controllers;


import org.interlink.grouporder.core.handler.ExceptionsHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SplitBillController {

    @GetMapping("/orders/{code}/group-order-debt")
    public ResponseEntity formGroupOrder(@PathVariable("code") String code) {
        try {
            return ResponseEntity.ok("Yep!!1!");
        } catch (Exception e) {
            return ExceptionsHandler.handleException(e);
        }
    }
}