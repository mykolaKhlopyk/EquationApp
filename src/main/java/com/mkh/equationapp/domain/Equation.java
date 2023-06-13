package com.mkh.equationapp.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "equations")
public class Equation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "left_part")
    private String leftPart;

    @Column(name = "right_part")
    private String rightPart;

    @OneToMany(mappedBy = "equation", cascade = CascadeType.REMOVE )
    private List<Root> roots;
}
