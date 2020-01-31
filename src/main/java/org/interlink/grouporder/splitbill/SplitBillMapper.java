package org.interlink.grouporder.splitbill;

import lombok.Getter;
import lombok.Setter;
import org.interlink.grouporder.core.entity.GroupOrder;
import org.interlink.grouporder.core.entity.MemberOrder;
import org.interlink.grouporder.splitbill.entity.MemberDTO;
import org.interlink.grouporder.splitbill.entity.OrderCheckDTO;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SplitBillMapper {

    private SplitBillMapper() {
    }

    public static OrderCheckDTO map(GroupOrder source, OrderCheckDTO destination) {

        List<MemberDTO> items = source.getMembers().values().stream()
                .map(member -> map(member, new MemberDTO()))
                .collect(Collectors.toList());

        destination.setFullPrice(source.getFullPrice());
        destination.setInternetShopURL(source.getInternetShopURL());
        destination.setItems(items);

        return destination;
    }

    public static MemberDTO map(MemberOrder source, MemberDTO destination) {
        destination.setName(source.getName());
        destination.setEmail(source.getEmail());
        destination.setPrice(source.getFullPrice());
        destination.setCount(1);

        return destination;
    }



}
