package org.interlink.grouporder.core.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "member_order")
@TypeDef(
        name = "jsonb",
        typeClass = JsonBinaryType.class
)
@Getter
@Setter
public class MemberOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "group_order_id")
    private GroupOrder groupOrder;
    private String name;
    private String email;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private List<Product> products = new ArrayList<>();
    private int restaurantId;
    private BigDecimal orderPrice;


}
