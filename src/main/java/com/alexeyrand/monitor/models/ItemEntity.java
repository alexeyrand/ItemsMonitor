package com.alexeyrand.monitor.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Table(name = "items")
public class ItemEntity {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String name;

    @Column(name = "shop_id")
    private String name;

    @Column(name = "item_shop_id")
    @OneToOne(mappedBy = "shop", cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    private ShopEntity shopEntity;

}
