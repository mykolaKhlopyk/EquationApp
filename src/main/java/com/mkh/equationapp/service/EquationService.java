package com.mkh.equationapp.service;

import com.mkh.equationapp.domain.Equation;

import java.util.List;

public interface EquationService {
    void save(Equation equation);

    List<Equation> getAll();

    Equation getEquationById(long id);

    void deleteRoot(long root_id);

    void addRoot(long equation_id, String newRootStr);
}
