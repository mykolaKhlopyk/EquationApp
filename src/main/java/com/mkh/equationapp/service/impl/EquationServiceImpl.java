package com.mkh.equationapp.service.impl;

import com.mkh.equationapp.domain.Calculator;
import com.mkh.equationapp.domain.Equation;
import com.mkh.equationapp.domain.Root;
import com.mkh.equationapp.domain.exceptions.InputException;
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
    private final static double exp = 0.000_000_001;
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

    @Override
    @Transactional
    public void addRoot(long equation_id, String newRootStr) {
        final double newRoot = Double.parseDouble(newRootStr);
        Equation equation = equationRepository.getReferenceById(equation_id);
        if (equation.getRoots().stream().mapToDouble(Root::getValue).filter(root -> areEqual(root, newRoot)).findAny().isPresent())
            throw new InputException("the root is already saved");
        checkCorrectionRootForEquation(equation, newRoot);
        Root root = new Root();
        root.setValue(newRoot);
        equation.getRoots().add(root);
        root.setEquation(equation);
        rootRepository.save(root);
    }

    private void checkCorrectionRootForEquation(Equation equation, double root) {
        if (!areEqual(
                Calculator.calculateEquationInPolandForm(equation.getPolandLeftPart(), root),
                Calculator.calculateEquationInPolandForm(equation.getPolandRightPart(), root))
        ) {
            throw new InputException("entered root isn't the equation root");
        }

    }

    public static boolean areEqual(double a, double b) {
        return Math.abs(a - b) < exp;
    }

    @Override
    public List<Equation> getEquationsWithOneRoot() {
        return equationRepository.findAllWithOneRoot();
    }
}
