package com.cgvsu.render_engine;

import java.util.ArrayList;
import java.util.List;

import com.cgvsu.math.vector.Vector3;
import com.cgvsu.model.Polygon;
import javafx.scene.canvas.GraphicsContext;

import javax.vecmath.*;

import com.cgvsu.model.Model;
import javafx.scene.paint.Color;

import static com.cgvsu.render_engine.GraphicConveyor.*;

public class RenderEngine {
    private static double[][] zBuffer; // Добавьте это

    public static void setZBuffer(double[][] buffer) {
        zBuffer = buffer;
    }

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height) {

        Matrix4f modelMatrix = rotateScaleTranslate();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
        modelViewProjectionMatrix.mul(viewMatrix);
        modelViewProjectionMatrix.mul(projectionMatrix);

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Point2d> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3 vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
                Vector3 vertexVecmath = new Vector3(vertex.getX(), vertex.getY(), vertex.getZ());

                Point2d resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                resultPoints.add(resultPoint);
            }

//            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
//                fillPolygons(
//                        graphicsContext,
//                        camera,
//                        mesh,
//                        (int) width,
//                        (int) height,
//                        Color.RED);
//            }
////                graphicsContext.strokeLine(
////                        resultPoints.get(vertexInPolygonInd - 1).x,
////                        resultPoints.get(vertexInPolygonInd - 1).y,
////                        resultPoints.get(vertexInPolygonInd).x,
////                        resultPoints.get(vertexInPolygonInd).y);
//            }

            if (nVerticesInPolygon > 2) fillPolygons(
                    graphicsContext,
                    camera,
                    mesh,
                    (int) width,
                    (int) height,
                    Color.RED);
//                graphicsContext.strokeLine(
//                        resultPoints.get(nVerticesInPolygon - 1).x,
//                        resultPoints.get(nVerticesInPolygon - 1).y,
//                        resultPoints.get(0).x,
//                        resultPoints.get(0).y);
        }
    }

    private static void fillPolygons(GraphicsContext graphicsContext, Camera camera, Model mesh, int width, int height, Color fillColor) {
        // Получаем матрицы преобразования

        Matrix4f modelMatrix = rotateScaleTranslate();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        // Вычисляем конечную матрицу модели-вида-проекции
        Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
        modelViewProjectionMatrix.mul(viewMatrix);
        modelViewProjectionMatrix.mul(projectionMatrix);

        // Проходим по всем полигонам модели
        for (Polygon polygon : mesh.getPolygons()) {
            // Получаем вершины полигона в мировых координатах
            ArrayList<Vector3> polygonVertices = new ArrayList<>();
            for (int vertexIndex : polygon.getVertexIndices()) {
                Vector3 vertex = mesh.getVertices().get(vertexIndex);
                Vector3 vertexVecmath = new Vector3(vertex.getX(), vertex.getY(), vertex.getZ());
                polygonVertices.add(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath));
            }

            // Растеризация треугольника
            rasterizeTriangle(graphicsContext, polygonVertices, width, height, fillColor);
        }
    }

    private static void rasterizeTriangle(
            final GraphicsContext graphicsContext,
            final List<Vector3> triangleVertices,
            final int width,
            final int height,
            final Color fillColor) {
        // Получаем координаты вершин треугольника
        double x1 = triangleVertices.get(0).getX();
        double y1 = triangleVertices.get(0).getY();
        double x2 = triangleVertices.get(1).getX();
        double y2 = triangleVertices.get(1).getY();
        double x3 = triangleVertices.get(2).getX();
        double y3 = triangleVertices.get(2).getY();

        // Находим ограничивающий прямоугольник
        double minX = Math.max(0, Math.min(x1, Math.min(x2, x3)));
        double minY = Math.max(0, Math.min(y1, Math.min(y2, y3)));
        double maxX = Math.min(width, Math.max(x1, Math.max(x2, x3)));
        double maxY = Math.min(height, Math.max(y1, Math.max(y2, y3)));

        // Используем алгоритм барицентрических координат для определения принадлежности точек треугольнику
        for (double x = minX; x <= maxX; x++) {
            for (double y = minY; y <= maxY; y++) {
//                double b1 = barycentricCoordinate(x2, y2, x3, y3, x, y) / barycentricCoordinate(x2, y2, x3, y3, x1, y1);
//                double b2 = barycentricCoordinate(x3, y3, x1, y1, x, y) / barycentricCoordinate(x3, y3, x1, y1, x2, y2);
//                double b3 = barycentricCoordinate(x1, y1, x2, y2, x, y) / barycentricCoordinate(x1, y1, x2, y2, x3, y3);
                double b1 = barycentricCoordinate(
                        x2, y2, x3, y3,
                        x, y, x1, y1);
                double b2 = barycentricCoordinate(
                        x3, y3, x1, y1,
                        x, y, x2, y2);
                double b3 = barycentricCoordinate(
                        x1, y1, x2, y2,
                        x, y, x3, y3);

                if (b1 >= 0 && b2 >= 0 && b3 >= 0) {
                    // Точка (x, y) находится внутри треугольника

                    double z1 = triangleVertices.get(0).getZ();
                    double z2 = triangleVertices.get(1).getZ();
                    double z3 = triangleVertices.get(2).getZ();

                    // Используем формулу для вычисления глубины
                    double InterpolatedDepth = b1 * z1 + b2 * z2 + b3 * z3;

                    int pixelX = (int) x;
                    int pixelY = (int) y;

                    if (InterpolatedDepth < zBuffer[pixelX][pixelY]) {
                        zBuffer[pixelX][pixelY] = (double) InterpolatedDepth;

                        // Заливка цветом или применение затенения
                        graphicsContext.getPixelWriter().setColor(pixelX, pixelY, fillColor);
                    }
                }
            }
        }
    }


    private static double barycentricCoordinate(
            double x1, double y1,
            double x2, double y2,
            double x3, double y3,
            double x, double y) {
        return ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) /
                ((y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3));
    }
}

