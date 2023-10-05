package com.teamvoy.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "order_goods",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "goods_id")
    )
    private List<Goods> goodsList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "totalPrice")
    private double totalPrice;

    @Column(name = "orderTime")
    private LocalDateTime orderTime;

}