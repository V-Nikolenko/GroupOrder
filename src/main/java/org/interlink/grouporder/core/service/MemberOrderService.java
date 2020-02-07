package org.interlink.grouporder.core.service;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.core.repository.MemberOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.util.List;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
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
}
