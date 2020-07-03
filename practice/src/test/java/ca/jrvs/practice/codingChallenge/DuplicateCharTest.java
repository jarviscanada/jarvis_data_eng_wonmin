package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DuplicateCharTest {

    @Test
    public void duplicateChar() {
        String input = "A black cat";
        DuplicateChar tester = new DuplicateChar();
        List<Character> goodList = new ArrayList<>();
        goodList.add(' '); goodList.add('a'); goodList.add('c');

        assertEquals(goodList, tester.duplicateChar(input));

    }
}