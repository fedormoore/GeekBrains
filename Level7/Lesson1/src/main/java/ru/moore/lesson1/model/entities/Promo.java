package ru.moore.lesson1.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "promo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "date_from")
    private Date dateFrom;

    @Column (name = "date_to")
    private Date dateTo;

    @Column
    private int discount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
