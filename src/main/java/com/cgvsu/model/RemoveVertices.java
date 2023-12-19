package com.cgvsu.model;

import java.util.ArrayList;
import java.util.Iterator;

public class RemoveVertices {
    public static void removeVertices(Model model, ArrayList<Integer> vertexIndicesToRemove) {
        if (model == null) {
            throw new IllegalArgumentException("Model is null");
        }

        if (vertexIndicesToRemove == null) {
            throw new IllegalArgumentException("VertexIndicesToRemove is null");
        }

        if (model.vertices.isEmpty()) {
            throw new IllegalArgumentException("Model vertices list is empty");
        }

        if (model.polygons.isEmpty()) {
            throw new IllegalArgumentException("Model polygons list is empty");
        }

        for (int index : vertexIndicesToRemove) {
            if (index < 0 || index >= model.vertices.size()) {
                throw new IndexOutOfBoundsException("Vertex index is out of bounds");
            }

            if (model.vertices.get(index) == null) {
                throw new NullPointerException("Vertex at the specified index is null");
            }

            model.vertices.remove(index);

            Iterator<Polygon> iterator = model.polygons.iterator();
            while (iterator.hasNext()) {
                Polygon polygon = iterator.next();
                ArrayList<Integer> indices = polygon.getVertexIndices();
                if (indices.contains(index)) {
                    iterator.remove();
                } else {
                    for (int i = 0; i < indices.size(); i++) {
                        int currentIndex = indices.get(i);
                        if (currentIndex > index) {
                            indices.set(i, currentIndex - 1);
                        }
                    }
                }
            }
        }
    }
}
