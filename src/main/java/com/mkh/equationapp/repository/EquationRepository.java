package com.mkh.equationapp.repository;

import com.mkh.equationapp.domain.Equation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquationRepository extends JpaRepository<Equation, Long> {
}
