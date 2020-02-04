package org.interlink.grouporder.core.repository;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupOrderRepository extends JpaRepository<GroupOrder, Integer> {

    @Modifying
    @Query(value = "INSERT INTO group_order (group_order_code, restaurant_id, restaurant_name, restaurant_url) " +
                    "VALUES  (:groupOrderCode, :restaurantId, :restaurantName, :restaurantUrl)", nativeQuery = true)
    void addGroupOrder(@Param("groupOrderCode") String groupOrderCode,
                       @Param("restaurantId") String restaurantId,
                       @Param("restaurantName") String restaurantName,
                       @Param("restaurantUrl") String restaurantUrl);
}
