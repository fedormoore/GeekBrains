package ru.moore.lesson1.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_status")
    private Date dateStatue;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

}
