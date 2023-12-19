package com.cgvsu.objreader;

import com.cgvsu.math.vector.Vector3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjReaderTest {

    //    @Test
//    public void testParseVertex01() {
//        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
//        Vector3 result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
//        Vector3 expectedResult = new Vector3(1.01, 1.02, 1.03);
//        Assertions.assertTrue(result.equals(expectedResult));
//    }
    @Test
    public void testParseVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3 result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3 expectedResult = new Vector3(1.01, 1.02, 1.03);
// todo: тестик не работает с Assertions.assertTrue(result.equals(expectedResult));
        double delta = 0; // Set an appropriate delta value
        assertEquals(expectedResult.getX(), result.getX(), delta);
        assertEquals(expectedResult.getY(), result.getY(), delta);
        assertEquals(expectedResult.getZ(), result.getZ(), delta);
    }


    @Test
    public void testParseVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3 result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3 expectedResult = new Vector3(1.01, 1.02, 1.10);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
            Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
            Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
            assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex05() {
        // АГААА! Вот тест, который говорит, что у метода нет проверки на более, чем 3 числа
        // А такой случай лучше не игнорировать, а сообщать пользователю, что у него что-то не так

        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
            Assertions.assertTrue(false);
        } catch (ObjReaderException exception) {
            String expectedError = "";
            assertEquals(expectedError, exception.getMessage());
        }
    }
}