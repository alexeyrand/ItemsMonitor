package com.alexeyrand.monitor.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "shop_list")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ShopEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="shop_id")
    private Integer shopId;

    @Column(name="shop_name")
    private String shopName;

    @Column(name="blocked")
    @Builder.Default
    private boolean blocked = false;


}
