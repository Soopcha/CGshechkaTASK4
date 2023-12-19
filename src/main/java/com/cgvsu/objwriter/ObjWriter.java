package com.cgvsu.objwriter;

import com.cgvsu.math.vector.Vector2;
import com.cgvsu.math.vector.Vector3;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ObjWriter {
    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";
    public static void write(String fileName, Model model) {
        if (model == null || model.isEmpty()) {
            throw new IllegalArgumentException("Invalid model for writing");
        }

        File file = new File(fileName);

        try {
            if (file.createNewFile()) {
                System.out.println("File created");
            }
        } catch (IOException e) {
            throw new ObjWriterException("Error creating the file: " + fileName + " " + e.getMessage());
        }

        try (PrintWriter printWriter = new PrintWriter(file)) {
            writeVertices(printWriter, model.getVertices());
            writeTextureVertices(printWriter, model.getTextureVertices());
            writeNormals(printWriter, model.getNormals());
            writePolygons(printWriter, model.getPolygons());
        } catch (IOException e) {
            throw new ObjWriterException("Error writing to file: " + fileName + " " + e.getMessage());
        }
    }
    protected static void writeVertices(PrintWriter pw, List<Vector3> vertices) throws IOException {
        for (Vector3 vertex: vertices) {
            pw.println(OBJ_VERTEX_TOKEN + " " + vertex.getX() + " " + vertex.getY() + " " + vertex.getZ());
        }
        pw.println();
    }

    protected static void writeTextureVertices(PrintWriter pw, List<Vector2> textureVertices) throws IOException {
        for (Vector2 vertex: textureVertices) {
            pw.println(OBJ_TEXTURE_TOKEN + " " + vertex.getX() + " " + vertex.getY());
        }
        pw.println();
    }

    protected static void writeNormals(PrintWriter pw, List<Vector3> normals) throws IOException {
        for (Vector3 normal: normals) {
            pw.println(OBJ_NORMAL_TOKEN + " " + normal.getX() + " " + normal.getY() + " " + normal.getZ());
        }
        pw.println();
    }

    protected static void writePolygons(PrintWriter pw, List<Polygon> polygons) throws IOException {
        for (Polygon polygon : polygons) {
            ArrayList<Integer> vertexIndices = polygon.getVertexIndices();
            ArrayList<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
            ArrayList<Integer> normalIndices = polygon.getNormalIndices();

            pw.print(polygonToObjString(vertexIndices, textureVertexIndices, normalIndices));
            pw.println();
        }
    }

    private static String polygonToObjString(List<Integer> vertexIndices, List<Integer> textureVertexIndices, List<Integer> normalIndices) {
        StringBuilder objString = new StringBuilder(OBJ_FACE_TOKEN + " ");
        for (int i = 0; i < vertexIndices.size(); i++) {
            if (!textureVertexIndices.isEmpty()) {
                objString.append(vertexIndices.get(i) + 1).append("/").append(textureVertexIndices.get(i) + 1);
            } else {
                objString.append(vertexIndices.get(i) + 1);
            }
            if (!normalIndices.isEmpty()) {
                if (textureVertexIndices.isEmpty()) {
                    objString.append("/");
                }
                objString.append("/").append(normalIndices.get(i) + 1);
            }
            objString.append(" ");
        }
        return objString.toString();
    }
}
