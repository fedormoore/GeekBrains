package ru.moore.lesson2.product.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int amount;

//    @ManyToMany
//    @JoinTable(
//            name = "product_item_storage",
//            joinColumns = @JoinColumn(name = "product_item_id"),
//            inverseJoinColumns = @JoinColumn(name = "storage_id"))
//    private List<Storage> storages;

}
