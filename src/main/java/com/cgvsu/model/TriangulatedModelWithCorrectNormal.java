package com.cgvsu.model;




import java.util.ArrayList;
import java.util.List;

import static com.cgvsu.triangulation.Triangulation.triangulation;
//НУЖЕН КЛАСС ТРАНГУЛИРОВАННОЙ И ТРЕНСФОРМИРОВАННОЙ МОДЕЛИ ГДЕ ПОЛЕ - ЭТОТ КЛАСС
public class TriangulatedModelWithCorrectNormal {
    private final Model initialModel;
    private final List<Polygon> triangulatedPolygons;

    public TriangulatedModelWithCorrectNormal(Model initialModel) {
        //НОРМАЛИИИИ //   RenderingPreparationUtilities.recalculateNormals(initialModel);
        this.initialModel = initialModel;
        this.triangulatedPolygons = triangulatePolygons(initialModel.polygons);
    }

    public Model getInitialModel() {
        return initialModel;
    }

    public List<Polygon> getTriangulatedPolygons() {
        return triangulatedPolygons;
    }

    public static List<Polygon> triangulatePolygons(List<Polygon> initialPolygons) {
        List<Polygon> triangulationPolygons = new ArrayList<>();

        for (Polygon polygon : initialPolygons) {
            triangulationPolygons.addAll(triangulation(polygon));
        }

        return triangulationPolygons;
    }
}
