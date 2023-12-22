package com.cgvsu.triangulation;

import com.cgvsu.math.vector.Vector3;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TriangulationTest {

    @Test
    void triangulationPolygonWith5VertexWithoutTextureAndNormal() {
        Polygon initialPolygon = new Polygon();
        initialPolygon.setVertexIndices(new ArrayList<>(Arrays.asList(8, 6, 1, 5, 3)));

        List<Polygon> triangularPolygons = Triangulation.triangulation(initialPolygon);

        Polygon polygon1 = new Polygon();
        polygon1.setVertexIndices(new ArrayList<>(Arrays.asList(8, 6, 1)));

        Polygon polygon2 = new Polygon();
        polygon2.setVertexIndices(new ArrayList<>(Arrays.asList(8, 1, 5)));

        Polygon polygon3 = new Polygon();
        polygon3.setVertexIndices(new ArrayList<>(Arrays.asList(8, 5, 3)));

        List<Polygon> expectedPolygons = new ArrayList<>(Arrays.asList(polygon1, polygon2, polygon3));


        StringBuilder result = new StringBuilder();
        StringBuilder expectedResult = new StringBuilder();

        for (Polygon polygon : expectedPolygons) {
            expectedResult.append(polygon);
        }

        for (Polygon polygon : triangularPolygons) {
            result.append(polygon);
        }

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    void triangulationPolygonWith3VertexWithoutTextureAndNormal() {
        Polygon initialPolygon = new Polygon();
        initialPolygon.setVertexIndices(new ArrayList<>(Arrays.asList(9, 11, 3)));

        List<Polygon> triangularPolygons = Triangulation.triangulation(initialPolygon);

        List<Polygon> expectedPolygons = new ArrayList<>();
        expectedPolygons.add(initialPolygon);


        StringBuilder result = new StringBuilder();
        StringBuilder expectedResult = new StringBuilder();

        for (Polygon polygon : expectedPolygons) {
            expectedResult.append(polygon);
        }

        for (Polygon polygon : triangularPolygons) {
            result.append(polygon);
        }

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    void triangulationPolygonWith5VertexWithTextureAndNormal() {
        Polygon initialPolygon = new Polygon();
        initialPolygon.setVertexIndices(new ArrayList<>(Arrays.asList(8, 6, 1, 5, 3)));
        initialPolygon.setTextureVertexIndices(new ArrayList<>(Arrays.asList(7, 9, 3, 85, 27)));
        initialPolygon.setNormalIndices(new ArrayList<>(Arrays.asList(56, 55, 79, 10, 2)));

        List<Polygon> triangularPolygons = Triangulation.triangulation(initialPolygon);

        Polygon polygon1 = new Polygon();
        polygon1.setVertexIndices(new ArrayList<>(Arrays.asList(8, 6, 1)));
        polygon1.setTextureVertexIndices(new ArrayList<>(Arrays.asList(7, 9, 3)));
        polygon1.setNormalIndices(new ArrayList<>(Arrays.asList(56, 55, 79)));

        Polygon polygon2 = new Polygon();
        polygon2.setVertexIndices(new ArrayList<>(Arrays.asList(8, 1, 5)));
        polygon2.setTextureVertexIndices(new ArrayList<>(Arrays.asList(7, 3, 85)));
        polygon2.setNormalIndices(new ArrayList<>(Arrays.asList(56, 79, 10)));

        Polygon polygon3 = new Polygon();
        polygon3.setVertexIndices(new ArrayList<>(Arrays.asList(8, 5, 3)));
        polygon3.setTextureVertexIndices(new ArrayList<>(Arrays.asList(7, 85, 27)));
        polygon3.setNormalIndices(new ArrayList<>(Arrays.asList(56, 10, 2)));

        List<Polygon> expectedPolygons = new ArrayList<>(Arrays.asList(polygon1, polygon2, polygon3));


        StringBuilder result = new StringBuilder();
        StringBuilder expectedResult = new StringBuilder();

        for (Polygon polygon : expectedPolygons) {
            expectedResult.append(polygon);
        }

        for (Polygon polygon : triangularPolygons) {
            result.append(polygon);
        }

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    void triangulationPolygonWith5VertexWithTextureWithoutNormal() {
        Polygon initialPolygon = new Polygon();
        initialPolygon.setVertexIndices(new ArrayList<>(Arrays.asList(8, 6, 1, 5, 3)));
        initialPolygon.setTextureVertexIndices(new ArrayList<>(Arrays.asList(7, 9, 3, 85, 27)));

        List<Polygon> triangularPolygons = Triangulation.triangulation(initialPolygon);

        Polygon polygon1 = new Polygon();
        polygon1.setVertexIndices(new ArrayList<>(Arrays.asList(8, 6, 1)));
        polygon1.setTextureVertexIndices(new ArrayList<>(Arrays.asList(7, 9, 3)));

        Polygon polygon2 = new Polygon();
        polygon2.setVertexIndices(new ArrayList<>(Arrays.asList(8, 1, 5)));
        polygon2.setTextureVertexIndices(new ArrayList<>(Arrays.asList(7, 3, 85)));

        Polygon polygon3 = new Polygon();
        polygon3.setVertexIndices(new ArrayList<>(Arrays.asList(8, 5, 3)));
        polygon3.setTextureVertexIndices(new ArrayList<>(Arrays.asList(7, 85, 27)));

        List<Polygon> expectedPolygons = new ArrayList<>(Arrays.asList(polygon1, polygon2, polygon3));


        StringBuilder result = new StringBuilder();
        StringBuilder expectedResult = new StringBuilder();

        for (Polygon polygon : expectedPolygons) {
            expectedResult.append(polygon);
        }

        for (Polygon polygon : triangularPolygons) {
            result.append(polygon);
        }

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    void triangulationPolygonWith5VertexWithNormalWithoutTexture() {
        Polygon initialPolygon = new Polygon();
        initialPolygon.setVertexIndices(new ArrayList<>(Arrays.asList(8, 6, 1, 5, 3)));
        initialPolygon.setNormalIndices(new ArrayList<>(Arrays.asList(56, 55, 79, 10, 2)));

        List<Polygon> triangularPolygons = Triangulation.triangulation(initialPolygon);

        Polygon polygon1 = new Polygon();
        polygon1.setVertexIndices(new ArrayList<>(Arrays.asList(8, 6, 1)));
        polygon1.setNormalIndices(new ArrayList<>(Arrays.asList(56, 55, 79)));

        Polygon polygon2 = new Polygon();
        polygon2.setVertexIndices(new ArrayList<>(Arrays.asList(8, 1, 5)));
        polygon2.setNormalIndices(new ArrayList<>(Arrays.asList(56, 79, 10)));

        Polygon polygon3 = new Polygon();
        polygon3.setVertexIndices(new ArrayList<>(Arrays.asList(8, 5, 3)));
        polygon3.setNormalIndices(new ArrayList<>(Arrays.asList(56, 10, 2)));

        List<Polygon> expectedPolygons = new ArrayList<>(Arrays.asList(polygon1, polygon2, polygon3));


        StringBuilder result = new StringBuilder();
        StringBuilder expectedResult = new StringBuilder();

        for (Polygon polygon : expectedPolygons) {
            expectedResult.append(polygon);
        }

        for (Polygon polygon : triangularPolygons) {
            result.append(polygon);
        }

        assertEquals(expectedResult.toString(), result.toString());
    }

    @Test
    void triangulationPolygonWith5VertexWithIncorrectNormalListSize() {
        Polygon initialPolygon = new Polygon();
        initialPolygon.setVertexIndices(new ArrayList<>(Arrays.asList(8, 6, 1, 5, 3)));
        initialPolygon.setNormalIndices(new ArrayList<>(Arrays.asList(56, 55, 79, 10)));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Triangulation.triangulation(initialPolygon));
    }

    @Test
    void triangulationPolygonWith5VertexWithIncorrectTextureListSize() {
        Polygon initialPolygon = new Polygon();
        initialPolygon.setVertexIndices(new ArrayList<>(Arrays.asList(8, 6, 1, 5, 3)));
        initialPolygon.setTextureVertexIndices(new ArrayList<>(Arrays.asList(7, 3, 85, 27)));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Triangulation.triangulation(initialPolygon));
    }

    @Test
    void calculateNormalForVertexInPolygon(){

        Model model = new Model();

        model.vertices.add(new Vector3(0.0, 0.0, 0.0));
        model.vertices.add(new Vector3(38.0, 2.0, 3.5));
        model.vertices.add(new Vector3(24.0, 10.3, 5.6));
        model.vertices.add(new Vector3(6.3, 2.1, 15.2));
        ArrayList<Integer> vertexIndices = new ArrayList<>(Arrays.asList(0, 1, 2, 3));

        Polygon polygon = new Polygon();
        polygon.setVertexIndices(vertexIndices);

        model.polygons.add(polygon);

        polygon = model.polygons.get(0);

        Vector3 result = Triangulation.calculateNormalForPolygon(polygon, model);
        Vector3 expectedResult = new Vector3(23.05, -555.55, 67.2);

        assertEquals(expectedResult.getX(), result.getX(), 0.01);
        assertEquals(expectedResult.getY(), result.getY(), 0.01);
        assertEquals(expectedResult.getZ(), result.getZ(), 0.01);

    }


    @Test
    void calculateNormalForVertexInModel() {

        Model model = new Model();


        model.vertices.add(new Vector3(0, 0, 0));
        model.vertices.add(new Vector3(1, 0, 0));
        model.vertices.add(new Vector3(1, 1, 0));
        model.vertices.add(new Vector3(0, 1, 0));
        model.vertices.add(new Vector3(0, 0, 1));
        model.vertices.add(new Vector3(1, 0, 1));

        ArrayList<Integer> vertexIndices1 = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        ArrayList<Integer> vertexIndices2 = new ArrayList<>(Arrays.asList(0, 1, 4, 5));
        ArrayList<Integer> vertexIndices3 = new ArrayList<>(Arrays.asList(2, 3, 4, 5));
        ArrayList<Integer> vertexIndices4 = new ArrayList<>(Arrays.asList(1, 2, 5));
        ArrayList<Integer> vertexIndices5 = new ArrayList<>(Arrays.asList(0, 3, 4));

        Polygon polygon1 = new Polygon();
        polygon1.setVertexIndices(vertexIndices1);
        Polygon polygon2 = new Polygon();
        polygon2.setVertexIndices(vertexIndices2);
        Polygon polygon3 = new Polygon();
        polygon3.setVertexIndices(vertexIndices3);
        Polygon polygon4 = new Polygon();
        polygon4.setVertexIndices(vertexIndices4);
        Polygon polygon5 = new Polygon();
        polygon5.setVertexIndices(vertexIndices5);

        model.polygons.add(polygon1);
        model.polygons.add(polygon2);
        model.polygons.add(polygon3);
        model.polygons.add(polygon4);
        model.polygons.add(polygon5);

        Triangulation.recalculateNormals(model);

        Vector3 expectedResult1 = new Vector3(1.0 / 3.0, -1 / 3.0, 1 / 3.0);
        Vector3 expectedResult2 = new Vector3(1.0 / 3.0, -1.0 / 3.0, 1.0 / 3.0);
        Vector3 expectedResult3 = new Vector3(1.0 / 3.0, 1.0 / 3.0, 2.0 / 3.0);
        Vector3 expectedResult4 = new Vector3(1.0 / 3.0, 1.0 / 3.0, 2.0 / 3.0);
        Vector3 expectedResult5 = new Vector3(1.0 / 3.0, 0.0, 1.0 / 3.0);
        Vector3 expectedResult6 = new Vector3(1.0 / 3.0, 0.0, 1.0 / 3.0);


        double delta = 0.1;
        ArrayList<Vector3> expectedResult = new ArrayList<>(Arrays.asList(expectedResult1, expectedResult2, expectedResult3, expectedResult4, expectedResult5, expectedResult6));
//        for (int i = 0; i < model.normals.size(); i++) {
//            assertEquals(expectedResult.get(i), model.normals.get(i),delta);
//        }

        for (int i = 0; i < model.normals.size(); i++) {
            Vector3 expected = expectedResult.get(i);
            Vector3 result = model.normals.get(i);

            assertEquals(expected.getX(), result.getX(), 0.01);
            assertEquals(expected.getY(), result.getY(), 0.01);
            assertEquals(expected.getZ(), result.getZ(), 0.01);
        }



        /*
        for (int i = 0; i < model.normals.size(); i++) {
            Assertions.assertTrue(expectedResult.get(i).equals(model.normals.get(i)));
        }


        /* for (int i = 0; i < model.normals.size(); i++) {
            Vector3 expected = expectedResult.get(i);
            Vector3 result = model.normals.get(i);

            System.out.println("Calculated Normal: " + result.getX() + " " + result.getY()+ " " + result.getZ());

            Assertions.assertEquals(expected.getX(), result.getX(), 0.01);
            Assertions.assertEquals(expected.getY(), result.getY(), 0.01);
            Assertions.assertEquals(expected.getZ(), result.getZ(), 0.01);
        }

         */
    }

}