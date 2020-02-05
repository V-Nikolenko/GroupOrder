package org.interlink.grouporder.core.repository;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupOrderRepository extends JpaRepository<GroupOrder, Integer> {

    @Modifying
    @Query(value = "INSERT INTO group_order (code, restaurant_id, restaurant_name, restaurant_url) " +
                   "VALUES  (:code, :restaurantId, :restaurantName, :restaurantUrl)", nativeQuery = true)
    void addGroupOrder(@Param("groupOrderCode") String code,
                       @Param("restaurantId") String restaurantId,
                       @Param("restaurantName") String restaurantName,
                       @Param("restaurantUrl") String restaurantUrl);

    @Query("SELECT g FROM group_order g" +
            "WHERE g.code = :code")
    GroupOrder getGroupOrder(@Param("code") String code);

    @Query("select code from  group_order")
    List<String> findAllCodes();
}
