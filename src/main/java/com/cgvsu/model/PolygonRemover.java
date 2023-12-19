package com.cgvsu.model;
import java.util.ArrayList;
import java.util.Iterator;

public class PolygonRemover {

    public static void removePolygons(Model model, ArrayList<Integer> polygonIndices, boolean removeFreeVertices) {
        removeSelectedPolygons(model, polygonIndices);
        if (removeFreeVertices) {
            removeFreeVertices(model);
        }
    }
 //удаление полигонов из модели
    private static void removeSelectedPolygons(Model model, ArrayList<Integer> polygonIndices) {
        Iterator<Polygon> iterator = model.polygons.iterator();
        int currentIndex = 0;

        while (iterator.hasNext()) {
            iterator.next();
            if (polygonIndices.contains(currentIndex)) {
                iterator.remove();
            }
            currentIndex++;
        }
    }

//удаление свободных вершин из модели
    private static void removeFreeVertices(Model model) {
        ArrayList<Integer> verticesToRemove = new ArrayList<>();
        for (int i = 0; i < model.vertices.size(); i++) {
            if (!isVertexUsed(model, i)) {
                verticesToRemove.add(i);
            }
        }
        removeVertices(model, verticesToRemove);
    }

    //проверка используется ли вершина в каких то полигонах
    private static boolean isVertexUsed(Model model, int vertexIndex) {
        for (Polygon polygon : model.polygons) {
            if (polygon.getVertexIndices().contains(vertexIndex)) {
                return true;
            }
        }
        return false;
    }

    //удаление вершин из модели и обновление индексов полигонов после удаления вершин
    private static void removeVertices(Model model, ArrayList<Integer> verticesToRemove) {
        for (int i = verticesToRemove.size() - 1; i >= 0; i--) {
            int vertexIndexToRemove = verticesToRemove.get(i);//получение текущего индекса для удаления вершины
            model.vertices.remove(vertexIndexToRemove);

            for (Polygon polygon : model.polygons) {
                updateIndices(polygon.getVertexIndices(), vertexIndexToRemove);
                updateIndices(polygon.getTextureVertexIndices(), vertexIndexToRemove);
                updateIndices(polygon.getNormalIndices(), vertexIndexToRemove);
            }
        }
    }

    //метод для обновления индексов в списке после удаления вершины из модели.
    private static void updateIndices(ArrayList<Integer> indices, int vertexIndexToRemove) {
        for (int i = indices.size() - 1; i >= 0; i--) {
            int currentIndex = indices.get(i);
            if (currentIndex == vertexIndexToRemove) {
                indices.remove(i);
            }
            else if (currentIndex > vertexIndexToRemove) {
                indices.set(i, currentIndex - 1);
            }
        }
    }
}
