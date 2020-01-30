package org.interlink.grouporder.core.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.interlink.grouporder.core.entity.view.GroupOrderView;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MemberOrder {
    @JsonView(GroupOrderView.Basic.class)
    private String name;
    private String email;
    private String url;
    private List<Product> products = new ArrayList<>();
    private int fullPrice;

    public MemberOrder(String name, String email, String url, List<Product> products, int fullPrice) {
        this.name = name;
        this.email = email;
        this.url = url;
        this.products = products;
        this.fullPrice = fullPrice;
    }

    public MemberOrder() {
    }
}
