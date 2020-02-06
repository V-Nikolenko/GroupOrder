package org.interlink.grouporder.core.service;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.repository.GroupOrderRepository;
import org.interlink.grouporder.core.utils.OrderCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupOrderService {
    private GroupOrderRepository groupOrderRepository;

    @Autowired
    public GroupOrderService(GroupOrderRepository groupOrderRepository) {
        this.groupOrderRepository = groupOrderRepository;
    }

    public void saveGroupOrder(GroupOrder groupOrder){
        this.groupOrderRepository.save(groupOrder);
    }

    public GroupOrder getGroupOrder(String code) {
        return this.groupOrderRepository.getGroupOrder(code);
    }

    public boolean isContainsCode(String code) {
        return this.groupOrderRepository.isContainsCode(code);
    }

    public String generateUniqueCode() {
        String code;
        do {
            code = OrderCodeGenerator.generateCode();
        } while (isContainsCode(code));

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
