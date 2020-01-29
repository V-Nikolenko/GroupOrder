package org.interlink.grouporder.core.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.interlink.grouporder.core.entity.view.GroupOrderView;
import org.interlink.grouporder.core.utils.ProductsCounter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GroupOrder {

    @JsonView(GroupOrderView.Basic.class)
    private String code;
    @JsonView(GroupOrderView.Extended.class)
    private String internetShopURL;
    @JsonView(GroupOrderView.Extended.class)
    private String fullPrice;
    @JsonView(GroupOrderView.Extended.class)
    private List<MemberOrder> members = new ArrayList<>();
    private boolean activeOrderStatus = true;

    public GroupOrder(String code) {
        this.code = code;
    }

    private boolean isMemberInGroupOrder(MemberOrder member) {
        return members.stream()
                .anyMatch(oldMember -> oldMember.getName().equals(member.getName()));
    }

    public void addMemberToGroupOrder(MemberOrder member) {
        if (isMemberInGroupOrder(member)) {
            members.stream()
                    .filter(oldMember -> oldMember.getName().equals(member.getName()))
                    .findFirst()
                    .ifPresent(oldMember -> oldMember = member);
        } else {
            members.add(member);
        }

        this.fullPrice = orderFullPrice();
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        for (MemberOrder member : members) {
            products.addAll(member.getProducts());
        }
        return ProductsCounter.getAllGroupingProducts(products);
    }

    private String orderFullPrice() {
        DecimalFormat df = new DecimalFormat("#0.00");
        BigDecimal fullOrderPrice = new BigDecimal("0");

        for (MemberOrder member : members) {
            fullOrderPrice.add(new BigDecimal(member.getFullPrice()));
        }
        return df.format(fullOrderPrice);
    }
}
