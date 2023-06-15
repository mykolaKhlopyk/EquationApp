package com.mkh.equationapp.domain;


import com.mkh.equationapp.domain.exceptions.InputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CalculatorTest {
    private final static double exp = 0.000_000_001;


    @Test
    public void calculateEquation() {
        assertTrue(
                checkCorrectionRootForEquation(new Equation("3+x=4"), 1));
        assertTrue(
                checkCorrectionRootForEquation(new Equation("x+4*(-1+4)=-x+4"), -4 ));
        assertTrue(
                checkCorrectionRootForEquation(new Equation("2+2=4"), 1));
        assertTrue(
                checkCorrectionRootForEquation(new Equation("x=9"), 9));
    }

    private boolean checkCorrectionRootForEquation(Equation equation, double root) {
        return areEqual(
                Calculator.calculateEquationInPolandForm(equation.getPolandLeftPart(), root),
                Calculator.calculateEquationInPolandForm(equation.getPolandRightPart(), root));

    }

    public static boolean areEqual(double a, double b) {
        return Math.abs(a - b) < exp;
    }
}
