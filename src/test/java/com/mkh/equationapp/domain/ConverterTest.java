package com.mkh.equationapp.domain;


import com.mkh.equationapp.domain.exceptions.InputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterTest {

    @Test
    public void testCorrectConvertToPoland(){
        assertEquals("2 2 +", Converter.getPolandForm("2+2"));
        assertEquals("2 2 + 3 +", Converter.getPolandForm("(2+ 2+3)"));
        assertEquals("3 3 * 4 5 5 + * +", Converter.getPolandForm("3*3+ 4*(5+5)"));
    }


    @Test
    public void testThrowingExceptionsOfEmptyInputConvertToPoland(){
        assertThrows(InputException.class, ()->Converter.getPolandForm(""), "Пустий ввід");
        assertThrows(InputException.class, ()->Converter.getPolandForm(null), "Пустий ввід");
        assertThrows(InputException.class, ()->Converter.getPolandForm("      "), "Пустий ввід");
    }

    @Test
    public void testThrowingExceptionsConvertToPoland(){
        assertThrows(InputException.class, ()->Converter.getPolandForm("y"), "Некоректний символ");
        assertThrows(InputException.class, ()->Converter.getPolandForm("()"), "Два неприпустимі оператори по сусідству");
        assertThrows(InputException.class, ()->Converter.getPolandForm("(2+3*)"), "Неприпустимий оператор");
        assertThrows(InputException.class, ()->Converter.getPolandForm("3*3+ 4*(5+5"), "Некоректна розстановка дужок");
    }


}
