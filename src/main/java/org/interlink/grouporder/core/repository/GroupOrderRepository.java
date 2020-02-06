package org.interlink.grouporder.core.repository;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface GroupOrderRepository extends JpaRepository<GroupOrder, Integer> {

    @Query("SELECT g FROM GroupOrder g WHERE g.code = :code")
    GroupOrder getGroupOrder(@Param("code") String code);

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM GroupOrder g WHERE g.code = :code" )
    boolean isContainsCode(@Param("code") String code);
}