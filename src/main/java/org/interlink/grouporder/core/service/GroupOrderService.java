package org.interlink.grouporder.core.service;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.repository.GroupOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupOrderService {
    private GroupOrderRepository groupOrderRepository;

    @Autowired
    public GroupOrderService(GroupOrderRepository groupOrderRepository) {
        this.groupOrderRepository = groupOrderRepository;
    }

    public void addGroupOrder(String code, String restaurantId, String restaurantName, String restaurantUrl){
        this.groupOrderRepository.addGroupOrder(code, restaurantId, restaurantName, restaurantUrl);
    }

    public GroupOrder getGroupOrder(String code) {
        return this.groupOrderRepository.getGroupOrder(code);
    }
}
