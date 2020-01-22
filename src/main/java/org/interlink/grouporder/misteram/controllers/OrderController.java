package org.interlink.grouporder.misteram.controllers;


import com.fasterxml.jackson.annotation.JsonView;
import org.interlink.grouporder.core.data.DataStorage;
import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.entity.view.GroupOrderView;
import org.interlink.grouporder.core.utils.OrderCodeGenerator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
