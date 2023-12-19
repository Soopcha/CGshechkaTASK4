package com.cgvsu.objwriter;

import com.cgvsu.math.vector.Vector2;
import com.cgvsu.math.vector.Vector3;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import com.cgvsu.objreader.IncorrectFileException;
import com.cgvsu.objreader.ObjReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

class ObjWriterTest {
    @Test
    public void testWriteVertices() throws IOException {
        ArrayList<Vector3> vertices = new ArrayList<>();
        vertices.add(new Vector3(1.0f, 2.0f, 3.0f));

        File file = new File("testFile.obj");
        if (file.createNewFile()) {
            System.out.println("File created");
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            ObjWriter.writeVertices(printWriter, vertices);
        }

        String fileContent = Files.readString(Path.of("testFile.obj"));
        Assertions.assertTrue(fileContent.contains("v 1.0 2.0 3.0"));
    }

    @Test
    public void testWriteTextureVertices() throws IOException {
        ArrayList<Vector2> textureVertices = new ArrayList<>();
        textureVertices.add(new Vector2(1.0f, 2.0f));

        File file = new File("testFile.obj");
        if (file.createNewFile()) {
            System.out.println("File created");
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            ObjWriter.writeTextureVertices(printWriter, textureVertices);
        }

        String fileContent = Files.readString(Path.of("testFile.obj"));
        Assertions.assertTrue(fileContent.contains("vt 1.0 2.0"));
    }

    @Test
    public void testWriteNormals() throws IOException {
        ArrayList<Vector3> normals = new ArrayList<>();
        normals.add(new Vector3(1.0f, 2.0f, 3.0f));

        File file = new File("testFile.obj");
        if (file.createNewFile()) {
            System.out.println("File created");
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            ObjWriter.writeNormals(printWriter, normals);
        }

        String fileContent = Files.readString(Path.of("testFile.obj"));
        Assertions.assertTrue(fileContent.contains("vn 1.0 2.0 3.0"));
    }

    @Test
    public void testWritePolygons() throws IOException {
        ArrayList<Polygon> polygons = new ArrayList<>();
        Polygon polygon = new Polygon();
        ArrayList<Integer> vertexIndices = new ArrayList<>();
        ArrayList<Integer> textureVertexIndices = new ArrayList<>();
        ArrayList<Integer> normalIndices = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            vertexIndices.add(i);
            textureVertexIndices.add(i);
            normalIndices.add(i);
        }
        polygon.setVertexIndices(vertexIndices);
        polygon.setTextureVertexIndices(textureVertexIndices);
        polygon.setNormalIndices(normalIndices);
        polygons.add(polygon);

        File file = new File("testFile.obj");
        if (file.createNewFile()) {
            System.out.println("File created");
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            ObjWriter.writePolygons(printWriter, polygons);
        }

        String fileContent = Files.readString(Path.of("testFile.obj"));
        Assertions.assertTrue(fileContent.contains("f 1/1/1 2/2/2 3/3/3 "));
    }

    @Test
    public void testWritePolygonsWithoutNormals() throws IOException {
        ArrayList<Polygon> polygons = new ArrayList<>();
        Polygon polygon = new Polygon();
        ArrayList<Integer> vertexIndices = new ArrayList<>();
        ArrayList<Integer> textureVertexIndices = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            vertexIndices.add(i);
            textureVertexIndices.add(i);
        }
        polygon.setVertexIndices(vertexIndices);
        polygon.setTextureVertexIndices(textureVertexIndices);
        polygons.add(polygon);

        File file = new File("testFile.obj");
        if (file.createNewFile()) {
            System.out.println("File created");
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            ObjWriter.writePolygons(printWriter, polygons);
        }

        String fileContent = Files.readString(Path.of("testFile.obj"));
        Assertions.assertTrue(fileContent.contains("f 1/1 2/2 3/3 "));
    }

    @Test
    public void testWritePolygonsWithoutTextureVertices() throws IOException {
        ArrayList<Polygon> polygons = new ArrayList<>();
        Polygon polygon = new Polygon();
        ArrayList<Integer> vertexIndices = new ArrayList<>();
        ArrayList<Integer> normalIndices = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            vertexIndices.add(i);
            normalIndices.add(i);
        }
        polygon.setVertexIndices(vertexIndices);
        polygon.setNormalIndices(normalIndices);
        polygons.add(polygon);

        File file = new File("testFile.obj");
        if (file.createNewFile()) {
            System.out.println("File created");
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            ObjWriter.writePolygons(printWriter, polygons);
        }

        String fileContent = Files.readString(Path.of("testFile.obj"));
        Assertions.assertTrue(fileContent.contains("f 1//1 2//2 3//3 "));
    }

    @Test
    public void testWritePolygonsWithOnlyVertices() throws IOException {
        ArrayList<Polygon> polygons = new ArrayList<>();
        Polygon polygon = new Polygon();
        ArrayList<Integer> vertexIndices = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            vertexIndices.add(i);
        }
        polygon.setVertexIndices(vertexIndices);
        polygons.add(polygon);

        File file = new File("testFile.obj");
        if (file.createNewFile()) {
            System.out.println("File created");
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            ObjWriter.writePolygons(printWriter, polygons);
        }

        String fileContent = Files.readString(Path.of("testFile.obj"));
        Assertions.assertTrue(fileContent.contains("f 1 2 3 4 5 "));
    }

    @Test
    public void testWrite() throws IOException, IncorrectFileException {
        Path fileName = Path.of("/Users/valiere/Downloads/Task4-master/3DModels/Faceform/WrapHead.obj");
        String fileContent = Files.readString(fileName);
        Model model = ObjReader.read(fileContent);
        try {
            ObjWriter.write("testFile.obj", model);
            String fileContent2 = Files.readString(Path.of("testFile.obj"));
            Model model2 = ObjReader.read(fileContent2);
            Assertions.assertEquals(model, model2);
        } catch (ObjWriterException e) {
            String expectedError = "Error writing to file.";
            Assertions.assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    public void testWriteToNonWritableDirectory() throws IOException, IncorrectFileException {
        Path fileName = Path.of("/Users/valiere/Downloads/Task4-master/3DModels/Faceform/WrapHead.obj");
        String fileContent = Files.readString(fileName);
        Model model = ObjReader.read(fileContent);
        String nonWritableDirectory = "/non/writable/directory/testFile.obj";
        Assertions.assertThrows(ObjWriterException.class, () -> {
            ObjWriter.write(nonWritableDirectory, model);
        });
    }

    @Test
    public void testWriteEmptyModel() {
        Model model = new Model();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ObjWriter.write("testFile.obj", model);
        });
    }
}
