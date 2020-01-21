package org.interlink.grouporder.order.dto;

import lombok.Getter;
import lombok.Setter;
import org.interlink.grouporder.entity.OrderMember;

import java.util.List;

@Getter
@Setter
public class OrderMemberDTO {

    private List<OrderMember> members;
}
