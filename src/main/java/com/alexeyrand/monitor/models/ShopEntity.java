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
@Table(name = "block_list")
public class ShopEntity {

    @Id
    @GeneratedValue
    @Column(name = "shop_id")
    private Long id;

    @Column(name = "shop_name")
    private String name;

}
