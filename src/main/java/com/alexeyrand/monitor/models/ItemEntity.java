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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private Integer id;

    @Column(name="avito_id")
    private String avitoId;

    @Column(name="item_name")
    private String name;

    @Column(name="href")
    private String href;

    @Column(name="price")
    private String price;

    @Column(name="date")
    private String date;

    @Column(name="description", length = 400)
    private String description;

    @Column(name="image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private ShopEntity shopEntity;


}
