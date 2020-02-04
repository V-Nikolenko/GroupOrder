package org.interlink.grouporder.core.repository;

import org.interlink.grouporder.core.entity.GroupOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupOrderRepository extends JpaRepository<GroupOrder, Integer> {
}
