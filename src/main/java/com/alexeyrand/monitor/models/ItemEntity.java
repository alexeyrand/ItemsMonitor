package com.alexeyrand.monitor.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ItemEntity {

    @Id
    //@GenericGenerator(name = "generator", strategy = "increment")
    //@GeneratedValue(generator = "generator")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Integer itemId;

    @Column(name="item_name")
    private String itemName;

    //@Column(name="shop_id")
    @OneToOne
    @JoinColumn(name = "shop_id")
    private ShopEntity shopEntity;


}
