package org.interlink.grouporder.core.repository;

import org.interlink.grouporder.core.entity.MemberOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MemberOrderRepository extends JpaRepository<MemberOrder, Integer> {

    @Query("SELECT m.products FROM MemberOrder m WHERE m.groupOrder.id IN (" +
            "SELECT g.id FROM GroupOrder g WHERE g.code = :code)")
    String findAllProducts(@Param("code") String code);

    @Query("SELECT m FROM MemberOrder m WHERE m.groupOrder.id IN (" +
            "SELECT g.id FROM GroupOrder g WHERE g.code = :code)")
    List<MemberOrder> findAllMembers(@Param("code") String code);
}
