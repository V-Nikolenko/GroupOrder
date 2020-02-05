package org.interlink.grouporder.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.interlink.grouporder.core.entity.view.GroupOrderView;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "group_order")
@Getter
@Setter
public class GroupOrder {

    @Id
    @GeneratedValue
    private Integer id;

    @JsonView(GroupOrderView.Basic.class)
    private String code;
    private String restaurantName;
    private String restaurantUrl;

    private int restaurantId;
    private int fullPrice;

    @JsonIgnore
    private boolean isLocked = false;

    public GroupOrder() {
    }
}
