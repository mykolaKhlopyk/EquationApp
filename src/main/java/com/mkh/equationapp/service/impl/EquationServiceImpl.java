package com.mkh.equationapp.service.impl;

import com.mkh.equationapp.domain.Equation;
import com.mkh.equationapp.repository.EquationRepository;
import com.mkh.equationapp.repository.RootRepository;
import com.mkh.equationapp.service.EquationService;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class EquationServiceImpl implements EquationService {
    private final EquationRepository equationRepository;
    private final RootRepository rootRepository;
    @Override
    @Transactional
    public void save(Equation equation) {
        equationRepository.save(equation);
    }

    @Override
    public List<Equation> getAll() {
        return equationRepository.findAll();
    }

    @Override
    public Equation getEquationById(long id) {
        Equation equation = equationRepository.getReferenceById(id);
        Hibernate.initialize(equation.getRoots());
        return equation;
    }

    @Override
    @Transactional
    public void deleteRoot(long root_id) {
        rootRepository.deleteById(root_id);
    }
}
