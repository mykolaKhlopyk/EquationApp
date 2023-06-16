package com.mkh.equationapp.repository;

import com.mkh.equationapp.domain.Equation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquationRepository extends JpaRepository<Equation, Long> {
    @Query(value = "SELECT e.* \n" +
            "FROM equations e \n" +
            "JOIN (\n" +
            "    SELECT equation_id\n" +
            "    FROM roots \n" +
            "    GROUP BY equation_id \n" +
            "    HAVING COUNT(*) = 1 \n" +
            ") r ON e.id = r.equation_id;", nativeQuery = true)
    List<Equation> findAllWithOneRoot();
}
