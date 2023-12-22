package com.cgvsu.model;

import com.cgvsu.math.vector.Vector2;
import com.cgvsu.math.vector.Vector3;

import java.util.*;

public class Model {

    public ArrayList<Vector3> vertices = new ArrayList<Vector3>();
    public ArrayList<Vector2> textureVertices = new ArrayList<Vector2>();
    public ArrayList<Vector3> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    public void setPolygons(ArrayList<Polygon> polygons) {
        this.polygons = polygons;
    }

    public Model(ArrayList<Vector3> vertices, ArrayList<Vector2> textureVertices, ArrayList<Vector3> normals, ArrayList<Polygon> polygons) {
        this.vertices = vertices;
        this.textureVertices = textureVertices;
        this.normals = normals;
        this.polygons = polygons;
    }
    public Model() {

    }
    public ArrayList<Vector3> getVertices() {
        return new ArrayList<>(vertices);
    }
    public void addVertex(Vector3 vertex) {
        this.vertices.add(vertex);
    }

    public ArrayList<Vector2> getTextureVertices() {
        return new ArrayList<>(textureVertices);
    }

    public void addTextureVertex(Vector2 textureVertex) {
        this.textureVertices.add(textureVertex);
    }

    public ArrayList<Vector3> getNormals() {
        return new ArrayList<>(normals);
    }

    public void addNormal(Vector3 normal) {
        this.normals.add(normal);
    }

    public ArrayList<Polygon> getPolygons() {
        return new ArrayList<>(polygons);
    }

    public void addPolygon(Polygon polygon) {
        this.polygons.add(polygon);
    }

    public boolean isEmpty() {
        return vertices.isEmpty();
    }
}
