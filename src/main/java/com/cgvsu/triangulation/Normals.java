package com.cgvsu.triangulation;

import com.cgvsu.math.vector.Vector3;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;

public class Normals {
    private ArrayList<Vector3> normals = new ArrayList<>();

    public Normals(Model model){
        normals = makeNormals(model);
    }

    public ArrayList<Vector3> getNormals() {
        return normals;
    }

    public int getSize(){
        return normals.size();
    }




    private ArrayList<Vector3> makeNormals(Model model) {
        for (Polygon polygon : model.polygons) {
            ArrayList<Integer> vertexIndices = polygon.getVertexIndices();

            // Получаем координаты вершин полигона
            Vector3 v0 = model.vertices.get(vertexIndices.get(0));
            Vector3 v1 = model.vertices.get(vertexIndices.get(1));
            Vector3 v2 = model.vertices.get(vertexIndices.get(2));

            // Вычисляем векторы, лежащие на поверхности полигона
            Vector3 edge1 = new Vector3(v1.getX() - v0.getX(), v1.getY() - v0.getY(), v1.getZ() - v0.getZ());
            Vector3 edge2 = new Vector3(v2.getX() - v0.getX(), v2.getY() - v0.getY(), v2.getZ() - v0.getZ());

            // Вычисляем нормаль как векторное произведение edge1 и edge2
            Vector3 normal = new Vector3(
                    edge1.getY() * edge2.getZ() - edge1.getZ() * edge2.getY(),
                    edge1.getZ() * edge2.getX() - edge1.getX() * edge2.getZ(),
                    edge1.getX() * edge2.getY() - edge1.getY() * edge2.getX()
            );

            // Нормализуем нормаль (приводим ее к единичной длине)
            double length = (double) Math.sqrt(normal.getX() * normal.getX() + normal.getY() * normal.getY() + normal.getZ() * normal.getZ());
            normal.setX(normal.getX()/length);
            normal.setY(normal.getY()/length);
            normal.setZ(normal.getZ()/length);

            // Добавляем нормаль в список
            normals.add(normal);
        }
        return normals;
    }
}
