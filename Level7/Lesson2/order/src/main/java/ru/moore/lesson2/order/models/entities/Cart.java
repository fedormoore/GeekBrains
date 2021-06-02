package ru.moore.lesson2.order.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.moore.lesson2.core.models.entities.User;
import ru.moore.lesson2.product.models.entities.Product;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne
    @JoinColumn(name="product_id")
    private Product product;

    @OneToMany (mappedBy = "cart")
    private List<OrderStatus> orderStatuses;
}
