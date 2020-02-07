package org.interlink.grouporder.core.service;

import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.core.repository.MemberOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MemberOrderService {

    private MemberOrderRepository memberOrderRepository;

    @Autowired
    public MemberOrderService(MemberOrderRepository memberOrderRepository) {
        this.memberOrderRepository = memberOrderRepository;
    }

    public void saveMemberToGroupOrder(MemberOrder memberOrder) {
        this.memberOrderRepository.save(memberOrder);
    }

    public void deleteMemberFromOrder(int id) {
        this.memberOrderRepository.deleteById(id);
    }

    public Object findAllProducts(String code) {
        return this.memberOrderRepository.findAllProducts(code);
    }

    public List<MemberOrder> findAllMembers(String code) {
        return this.memberOrderRepository.findAllMembers(code);
    }

    public BigDecimal sumFullPrice(String code){return this.memberOrderRepository.sumAllOrderPricesFromAllMembers(code);}
}
