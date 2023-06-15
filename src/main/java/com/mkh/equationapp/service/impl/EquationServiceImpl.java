package com.mkh.equationapp.service.impl;

import com.mkh.equationapp.domain.Equation;
import com.mkh.equationapp.repository.EquationRepository;
import com.mkh.equationapp.service.EquationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EquationServiceImpl implements EquationService {
    private final EquationRepository equationRepository;
    @Override
    public void save(Equation equation) {
        equationRepository.save(equation);
    }
}
