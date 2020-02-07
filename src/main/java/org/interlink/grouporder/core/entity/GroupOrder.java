package org.interlink.grouporder.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.interlink.grouporder.core.entity.view.GroupOrderView;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "group_order")
@Getter
@Setter
public class GroupOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonView(GroupOrderView.Basic.class)
    private String code;
    private String restaurantName;
    private String restaurantUrl;
    private int restaurantId;

//    @ColumnDefault(value = "0")
//    private BigDecimal fullPrice;

    @JsonIgnore
    @ColumnDefault(value = "false")
    private boolean isLocked;

    public GroupOrder() {
    }
}
