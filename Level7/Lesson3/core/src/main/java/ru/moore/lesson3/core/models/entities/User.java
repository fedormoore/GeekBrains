package ru.moore.lesson3.core.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String login;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

//    @OneToMany(mappedBy = "user")
//    private List<ProductComment> productCommentList;
//
//    @OneToMany(mappedBy = "user")
//    private List<Cashback> cashbacks;
//
//    @OneToMany(mappedBy = "user")
//    private List<Cart> carts;

}
