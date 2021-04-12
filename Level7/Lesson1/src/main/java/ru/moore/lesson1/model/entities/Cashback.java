package ru.moore.lesson1.model.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cashback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cashback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int sum;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
