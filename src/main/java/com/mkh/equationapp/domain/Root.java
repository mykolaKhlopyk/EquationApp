package com.mkh.equationapp.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roots")
public class Root {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "value")
    private long value;

    @ManyToOne
    @JoinColumn(name = "equation_id", referencedColumnName = "id")
    private Equation equation;
}
