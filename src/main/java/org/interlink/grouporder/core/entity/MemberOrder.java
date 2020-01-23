package org.interlink.grouporder.core.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.interlink.grouporder.core.entity.view.GroupOrderView;

import java.util.ArrayList;
import java.util.List;

public class MemberOrder {
    @JsonView(GroupOrderView.Basic.class)
    private String name;
    private String url;
    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
