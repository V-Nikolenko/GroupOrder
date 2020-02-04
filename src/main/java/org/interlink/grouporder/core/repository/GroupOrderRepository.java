package org.interlink.grouporder.core.repository;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupOrderRepository extends JpaRepository<GroupOrder, Integer> {
    @Modifying
    @Query("INSERT INTO member_order (group_order_code, name, email, restaurant_id, products, order_price) " +
            "VALUES  (:groupOrderCode, :name, :email, :restaurantId, :orderPrice)", nativeQuery = true)
    void addMemberToGroupOrder(@Param("groupOrderCode") String groupOrderCode,
                               @Param("name") String name,
                               @Param("email") String email,
                               @Param("products") String products,
                               @Param("restaurantId") int restaurantId,
                               @Param("orderPrice") int orderPrice);
}
