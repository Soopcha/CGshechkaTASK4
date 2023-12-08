package com.cgvsu.objreader;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Polygon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ObjReaderTest {

    @Test
    public void testParseVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseVertex05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
    @Test
    public void testParseTextureVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02"));
        Vector2f result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
        Vector2f expectedResult = new Vector2f(1.01f, 1.02f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseTextureVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02"));
        Vector2f result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
        Vector2f expectedResult = new Vector2f(1.01f, 1.12f);
        Assertions.assertNotEquals(result, expectedResult);
    }

    @Test
    public void testParseTextureVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseTextureVertex05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormal01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseNormal(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    public void testParseNormal02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseNormal(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    public void testParseNormal03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormal04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few normal arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseNormal05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many normal arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
    @Test
    public void testParseFaceWord01(){
        String wordInLine = "1//2";
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();
        ArrayList<Integer> expectedList1 = new ArrayList<>(Arrays.asList(1));
        ArrayList<Integer> expectedList2 = new ArrayList<>(Arrays.asList());
        ArrayList<Integer> expectedList3 = new ArrayList<>(Arrays.asList(2));
        ObjReader.parseFaceWord(wordInLine, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10);
        Assertions.assertEquals(onePolygonVertexIndices, expectedList1);
        Assertions.assertEquals(onePolygonTextureVertexIndices, expectedList2);
        Assertions.assertEquals(onePolygonNormalIndices, expectedList3);
    }
    @Test
    public void testParseFaceWord02(){
        String wordInLine = "1/3/2/4";
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();
        try {
            ObjReader.parseFaceWord(wordInLine,onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Invalid element size.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
    @Test
    public void testParseFaceWord03(){
        String wordInLine = "t/b/a";
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();
        try {
            ObjReader.parseFaceWord(wordInLine,onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse int value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
    @Test
    public void testFormatArray01() {
    ArrayList<Integer> list = new ArrayList<>(Arrays.asList(-1, 2, -3, 4));
    int n = 15;
    ObjReader.formatArray(list, n, 1);
    ArrayList<Integer> expectedList = new ArrayList<>(Arrays.asList(14, 1, 12, 3));
    Assertions.assertEquals(expectedList, list);
    }
    @Test
    public void testParseFace01() throws IncorrectFileException {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/2", "6/2/3", "2/5/7"));
        Polygon expectedPolygon = new Polygon(new ArrayList<Integer>(Arrays.asList(1,6,2)), new ArrayList<Integer>(Arrays.asList(1,2,5)), new ArrayList<Integer>(Arrays.asList(2,3,7)));
        Polygon polygon = ObjReader.parseFace(wordsInLineWithoutToken, 10);
        Assertions.assertTrue(polygon.equals(expectedPolygon));
    }
    //wrong index
    @Test
    public void testParseFace02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/2", "1/2/3", "1/5/7"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception){
            String expectedError = "Error parsing OBJ file on line: 10. The vertices of the polygon must not be repeated.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
    @Test
    public void testParseFace03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1", "1/2/3", "1"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception){
            String expectedError = "Error parsing OBJ file on line: 10. Incorrect data for polygon.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
    @Test
    public void testParseFace04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/2", "3/2/3"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception){
            String expectedError = "Error parsing OBJ file on line: 10. Not enough vertex to create polygon.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
    @Test
    public void testParseFace05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1//2", "6//3", "2//7"));
        Polygon expectedPolygon = new Polygon(new ArrayList<Integer>(Arrays.asList(1,6,2)), new ArrayList<Integer>(List.of()), new ArrayList<Integer>(Arrays.asList(2,3,7)));
        Polygon polygon = ObjReader.parseFace(wordsInLineWithoutToken, 10);
        Assertions.assertTrue(polygon.equals(expectedPolygon));
    }
}