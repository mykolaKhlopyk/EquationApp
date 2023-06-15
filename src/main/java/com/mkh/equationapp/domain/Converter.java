package com.mkh.equationapp.domain;

import com.mkh.equationapp.domain.exceptions.InputException;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Map;
import java.util.Stack;

public class Converter {

    private static final Map<Character, Integer> OPERATORS = Map.of(
            ')', 0,
            '(', 0,
            '+', 1,
            '-', 1,
            '*', 2,
            '/', 2
    );

    public static String getPolandForm(String equationsPart) {
        if (equationsPart == null || equationsPart.isBlank())
            throw new InputException("Пустий ввід");
        String rebuiltEquationPart = equationsPart.replace(" ", "");
        rebuiltEquationPart = getFormWithSolvedProblemsByMinuses(rebuiltEquationPart);
        checkSigns(rebuiltEquationPart);
        return buildPolandForm(rebuiltEquationPart);
    }

    private static String getFormWithSolvedProblemsByMinuses(String str) {
        if (str.charAt(0) == '-')
            str = "(-1)*" + str.substring(1);
        return str.replace("+-", "-").replace("/-", "/(-1)/").replace("*-", "*(-1)*");
    }

    private static void checkSigns(String str) {
        for (int i = 0; i < str.length() - 1; i++) {
            if (isOperator(str.charAt(i)) && isOperator(str.charAt(i + 1))) {
                if (!(str.charAt(i) == ')' && str.charAt(i + 1) != '(')
                        && !(str.charAt(i) != ')' && str.charAt(i + 1) == '(')
                        && !(str.charAt(i) == '(' && str.charAt(i + 1) == '-')
                )
                    throw new InputException("Some operators are too close");
            }
        }
        checkSignsAtTheEdge(str);
    }

    private static void checkSignsAtTheEdge(String str) {
        if ((isOperator(str.charAt(0)) && !(str.charAt(0) == '('))
                || (isOperator(str.charAt(str.length() - 1)) && !(str.charAt(str.length() - 1) == ')')))
            throw new InputException("Incorrect operator");
    }

    private static String buildPolandForm(String equation) {

        StringBuilder result = new StringBuilder();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < equation.length(); i++) {
            char currentChar = equation.charAt(i);

            if (Character.isDigit(currentChar)) {
                result.append(readNextNumber(equation, i));
                result.append(" ");
            } else if (currentChar == 'x') {
                result.append(currentChar);
                result.append(" ");
            } else if (currentChar == '(') {
                operators.push(currentChar);
            } else if (currentChar == ')') {
                closeBrackets(operators, result);
            } else if (isOperator(currentChar)) {
                readOperator(equation, i, operators, result);
            } else {
                throw new InputException("Incorrect symbol");
            }
        }

        while (!operators.isEmpty()) {
            if (operators.peek() == '(') {
                throw new InputException("Brackets are incorrect");
            }
            result.append(operators.pop());
            result.append(" ");
        }

        return result.toString().trim();
    }

    private static Number readNextNumber(String equationsPart, int startIndex) {
        StringBuilder str = new StringBuilder("");
        char currentChar;
        for (int i = startIndex; i < equationsPart.length(); i++) {
            currentChar = equationsPart.charAt(i);
            if (Character.isDigit(currentChar) || currentChar == '.')
                str.append(currentChar);
            else
                break;
        }
        if (str.toString().contains("."))
            return Double.parseDouble(str.toString());
        return Integer.parseInt(str.toString());
    }

    private static void closeBrackets(Stack<Character> operators, StringBuilder result) {
        while (!operators.isEmpty() && operators.peek() != '(') {
            result.append(operators.pop());
            result.append(" ");
        }
        if (!operators.isEmpty() && operators.peek() != '(') {
            throw new IllegalArgumentException("Brackets are incorrect");
        }
        operators.pop();
    }

    private static boolean isOperator(char operator) {
        return OPERATORS.containsKey(operator);
    }

    private static void readOperator(String equation, int i, Stack<Character> operators, StringBuilder result) {
        char currentChar = equation.charAt(i);
        if (currentChar == '-' && i + 3 < equation.length() && equation.substring(i + 1, i + 3).equals("1)")) {
            result.append("-1 ");
            operators.pop();
            i += 2;
            return;
        }
        while (!operators.isEmpty() && OPERATORS.get(currentChar) <= OPERATORS.get(operators.peek())) {
            result.append(operators.pop());
            result.append(" ");
        }
        operators.push(currentChar);
    }
}

