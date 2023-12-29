package com.cgvsu.model;

import com.cgvsu.utils.Normals;
import java.util.ArrayList;
import java.util.List;

import static com.cgvsu.triangulation.Triangulation.triangulation;

public class TriangulatedModelWithCorrectNormal {
    private final Model initialModel;
    private final List<Polygon> triangulatedPolygons;

    public TriangulatedModelWithCorrectNormal(Model initialModel) {
        this.initialModel = initialModel;
        recalculateNormals();
        this.triangulatedPolygons = triangulatePolygons(initialModel.polygons);
        this.initialModel.setPolygons(triangulatePolygons(this.triangulatedPolygons));
    }


    public void recalculateNormals() {
        initialModel.normals.clear();
        Normals normals = new Normals(initialModel);
        initialModel.normals.addAll(normals.getNormals());

    }

    public Model getInitialModel() {
        return initialModel;
    }

    public List<Polygon> getTriangulatedPolygons() {
        return triangulatedPolygons;
    }

    public static ArrayList<Polygon> triangulatePolygons(List<Polygon> initialPolygons) {
        ArrayList<Polygon> triangulationPolygons = new ArrayList<>();

        for (Polygon polygon : initialPolygons) {
            triangulationPolygons.addAll(triangulation(polygon));
        }

        return triangulationPolygons;
    }
}
