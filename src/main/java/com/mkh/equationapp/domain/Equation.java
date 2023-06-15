package com.mkh.equationapp.domain;

import com.mkh.equationapp.domain.exceptions.InputException;
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

    @Column(name = "poland_left_part")
    private String polandLeftPart;

    @Column(name = "poland_right_part")
    private String polandRightPart;

    @OneToMany(mappedBy = "equation", cascade = CascadeType.REMOVE)
    private List<Root> roots;

    public Equation(String fullEquation) {
        splitEquation(fullEquation);
        polandLeftPart = Converter.getPolandForm(leftPart);
        polandRightPart = Converter.getPolandForm(rightPart);
    }

    private void splitEquation(String fullEquation) {
        String[] parts = fullEquation.split("=");
        if (parts.length != 2) {
            throw new InputException("Check operator '='");
        }
        leftPart = parts[0];
        rightPart = parts[1];
    }



}
