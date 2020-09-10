package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrintLetterWithNumberTest {

    @Test
    public void print() {
        PrintLetterWithNumber printer = new PrintLetterWithNumber();
        String input = "abce";
        String answer = "a1b2c3e5";
        String input2 = "dfgA";
        String answer2 = "d4f6g7A27";

        assertEquals(answer, printer.print(input));
        assertEquals(answer2, printer.print(input2));
    }

}