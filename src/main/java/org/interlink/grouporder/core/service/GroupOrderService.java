package org.interlink.grouporder.core.service;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.repository.GroupOrderRepository;
import org.interlink.grouporder.core.utils.OrderCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupOrderService {
    private GroupOrderRepository groupOrderRepository;

    @Autowired
    public GroupOrderService(GroupOrderRepository groupOrderRepository) {
        this.groupOrderRepository = groupOrderRepository;
    }

//    public void addGroupOrder(String code, String restaurantId, String restaurantName, String restaurantUrl){
//
//        this.groupOrderRepository.addGroupOrder(code, restaurantId, restaurantName, restaurantUrl);
//    }
//
//    public GroupOrder getGroupOrder(String code) {
//        return this.groupOrderRepository.getGroupOrder(code);
//    }
//
//    public List<String> findAllCodes() {
//        return this.groupOrderRepository.findAllCodes();
//    }

    public String generateUniqueCode() {
        String code;
//        do {
            code = OrderCodeGenerator.generateCode();
//        } while (findAllCodes().contains(code));

        return code;
    }

    public void unlock(GroupOrder groupOrder) {
        groupOrder.setLocked(false);
        groupOrderRepository.save(groupOrder);
    }
    public void lock(GroupOrder groupOrder) {
        groupOrder.setLocked(true);
        groupOrderRepository.save(groupOrder);
    }
}
