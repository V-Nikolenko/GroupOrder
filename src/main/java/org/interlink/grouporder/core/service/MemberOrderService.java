package org.interlink.grouporder.core.service;

import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.core.repository.GroupOrderRepository;
import org.interlink.grouporder.core.repository.MemberOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberOrderService {

    private MemberOrderRepository memberOrderRepository;

    @Autowired
    public MemberOrderService(MemberOrderRepository memberOrderRepository) {
        this.memberOrderRepository = memberOrderRepository;
    }
//
//    public void addMemberToGroupOrder(String code, String name, String email, String products, int restaurantID, int orderPrice) {
//        this.memberOrderRepository.addMemberToGroupOrder(code, name, email, products, restaurantID, orderPrice);
//    }
//
//    public void removeMemberFromOrder(String code, String email){
//        this.memberOrderRepository.removeMemberFromOrder(code, email);
//    }
//
//    public List<String> findAllProducts(String code) {
//        return this.memberOrderRepository.findAllProducts(code);
//    }
//
//    public List<MemberOrder> findAllMembers(String code) {
//        return this.memberOrderRepository.findAllMembers(code);
//    }
}
