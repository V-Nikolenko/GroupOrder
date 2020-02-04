package org.interlink.grouporder.core.repository;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupOrderRepository extends JpaRepository<GroupOrder, Integer> {

    @Modifying
    @Query(value = "INSERT INTO member_order (group_order_code, name, email, restaurant_id, products, order_price) " +
            "VALUES  (:groupOrderCode, :name, :email, :restaurantId, :orderPrice)", nativeQuery = true)
    void addMemberToGroupOrder(@Param("groupOrderCode") String groupOrderCode,
                               @Param("name") String name,
                               @Param("email") String email,
                               @Param("products") String products,
                               @Param("restaurantId") int restaurantId,
                               @Param("orderPrice") int orderPrice);

    @Modifying
    @Query("DELETE FROM member_order " +
            "WHERE member_order.email = :email " +
            "AND group_urder_code = :groupOrderCode")
    void removeMemberFromOrder(@Param("groupOrderCode") String groupOrderCode,
                               @Param("email") String email);

    @Query("SELECT products " +
            "FROM member_order" +
            "WHERE member_order.group_order_code = :groupOrderCode")
    List<String> findAllProducts(@Param("groupOrderCode") String groupOrderCode);
}
