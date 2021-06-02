package ru.moore.market.msproduct.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long cost;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private String image;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Category category;

}
