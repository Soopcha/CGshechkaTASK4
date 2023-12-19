package com.cgvsu.removepolygons;

import com.cgvsu.math.vector.Vector3;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.objreader.IncorrectFileException;
import com.cgvsu.objreader.ObjReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.cgvsu.model.PolygonRemover.removePolygons;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class RemovePolygonsTest {

    // тест 1: куб
    @Test
    public void testRemovePolygonsFromCube() {
        Model model = new Model();

        // Добавление вершин куба
        model.vertices.add(new Vector3(0, 0, 0)); // 0
        model.vertices.add(new Vector3(1, 0, 0)); // 1
        model.vertices.add(new Vector3(0, 1, 0)); // 2
        model.vertices.add(new Vector3(1, 1, 0)); // 3
        model.vertices.add(new Vector3(0, 0, 1)); // 4
        model.vertices.add(new Vector3(1, 0, 1)); // 5
        model.vertices.add(new Vector3(0, 1, 1)); // 6
        model.vertices.add(new Vector3(1, 1, 1)); // 7

        // Добавление полигонов куба
        ArrayList<Integer> vertexIndices1 = new ArrayList<>(List.of(0, 1, 3, 2)); // Передняя грань
        Polygon polygon1 = new Polygon();
        polygon1.setVertexIndices(vertexIndices1);
        model.polygons.add(polygon1);

        ArrayList<Integer> vertexIndices2 = new ArrayList<>(List.of(4, 5, 7, 6)); // Задняя грань
        Polygon polygon2 = new Polygon();
        polygon2.setVertexIndices(vertexIndices2);
        model.polygons.add(polygon2);

        ArrayList<Integer> vertexIndices3 = new ArrayList<>(List.of(0, 1, 5, 4)); // Нижняя грань
        Polygon polygon3 = new Polygon();
        polygon3.setVertexIndices(vertexIndices3);
        model.polygons.add(polygon3);

        ArrayList<Integer> vertexIndices4 = new ArrayList<>(List.of(2, 3, 7, 6)); // Верхняя грань
        Polygon polygon4 = new Polygon();
        polygon4.setVertexIndices(vertexIndices4);
        model.polygons.add(polygon4);

        ArrayList<Integer> vertexIndices5 = new ArrayList<>(List.of(0, 2, 6, 4)); // Левая грань
        Polygon polygon5 = new Polygon();
        polygon5.setVertexIndices(vertexIndices5);
        model.polygons.add(polygon5);

        ArrayList<Integer> vertexIndices6 = new ArrayList<>(List.of(1, 3, 7, 5)); // Правая грань
        Polygon polygon6 = new Polygon();
        polygon6.setVertexIndices(vertexIndices6);
        model.polygons.add(polygon6);
        ArrayList<Integer> polygonsToRemove = new ArrayList<>();


        polygonsToRemove.add(4);
        polygonsToRemove.add(1);
        polygonsToRemove.add(2);
        polygonsToRemove.add(0);
        polygonsToRemove.add(3);
        polygonsToRemove.add(5);
        polygonsToRemove.add(6);
        polygonsToRemove.add(7);



        removePolygons(model, polygonsToRemove, true);

       assertEquals(0, model.polygons.size());
       assertEquals(0, model.vertices.size());


    }


    @Test
    public void testRemovePolygonsFromDodecahedron() throws IOException, IncorrectFileException {
        Path fileName = Path.of("C:/Users/anyad/IdeaProjects/CGtask3-master/3DModels/SimpleModelsForReaderTests/dodecahedron.obj");
        String fileContent = Files.readString(fileName);
        Model model = ObjReader.read(fileContent);

        ArrayList<Integer> polygonsToRemove = new ArrayList<>();
        polygonsToRemove.add(1);
        removePolygons(model, polygonsToRemove, false);

        assertEquals(20, model.vertices.size());
        assertEquals(35, model.polygons.size());

    }

    @Test
    public void testRemovePolygonsFromTriangle() throws IOException, IncorrectFileException {
        Path fileName = Path.of("C:/Users/anyad/IdeaProjects/CGtask3-master/3DModels/SimpleModelsForReaderTests/Triangle.obj");
        String fileContent = Files.readString(fileName);
        Model model = ObjReader.read(fileContent);

        ArrayList<Integer> polygonsToRemove = new ArrayList<>();
        polygonsToRemove.add(0);
        removePolygons(model, polygonsToRemove, true);

        assertEquals(0, model.vertices.size());
        assertEquals(0, model.polygons.size());

    }
}
