package com.cgvsu.render_engine;

import java.util.ArrayList;
import java.util.List;

import com.cgvsu.math.matrix.Matrix4x4;
import com.cgvsu.math.vector.Vector2;
import com.cgvsu.math.vector.Vector3;
import com.cgvsu.model.Polygon;
import javafx.scene.canvas.GraphicsContext;
import com.cgvsu.model.Model;
import javafx.scene.paint.Color;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point2d;

import static com.cgvsu.math.matrix.Matrix4x4.multiplyMatrix4ByVector3;
import static com.cgvsu.math.matrix.Matrix4x4.rotateScaleTranslate;
import static com.cgvsu.math.vector.Vector3.vertexToPoint;


public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height,
            double[][] zBuffer ) {
        Matrix4x4 modelMatrix = rotateScaleTranslate();
        Matrix4x4 viewMatrix = camera.getViewMatrix();
        Matrix4x4 projectionMatrix = camera.getProjectionMatrix();

        Matrix4x4 modelViewProjectionMatrix = modelMatrix.multiply(viewMatrix).multiply(projectionMatrix);

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Vector2> resultVectors = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3 vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
                Vector3 transformedVertex = Matrix4x4.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex);

                Vector2 resultPoint = Vector3.vertexToPoint(transformedVertex, width, height);
                resultVectors.add(resultPoint);
            }

            ArrayList<Point2d> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3 vertexVecmath = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
//                Vector3 vertexVecmath = new Vector3(vertex.getX(), vertex.getY(), vertex.getZ());

                Point2d resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                resultPoints.add(resultPoint);
            }

            if (nVerticesInPolygon > 2) fillPolygons(
                    graphicsContext,
                    camera,
                    mesh,
                    (int) width,
                    (int) height,
                    Color.RED,
                    zBuffer,
                    modelViewProjectionMatrix);

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(
                        resultVectors.get(vertexInPolygonInd - 1).getX(),
                        resultVectors.get(vertexInPolygonInd - 1).getY(),
                        resultVectors.get(vertexInPolygonInd).getX(),
                        resultVectors.get(vertexInPolygonInd).getY());
            }



            if (nVerticesInPolygon > 0)
                graphicsContext.strokeLine(
                        resultVectors.get(nVerticesInPolygon - 1).getX(),
                        resultVectors.get(nVerticesInPolygon - 1).getY(),
                        resultVectors.get(0).getX(),
                        resultVectors.get(0).getY());
        }
    }
    public static Point2d vertexToPoint(final Vector3 vertex, final int width, final int height) {
        return new Point2d(vertex.getX() * width + width / 2.0F, -vertex.getY() * height + height / 2.0F);
    }
    private static void fillPolygons(GraphicsContext graphicsContext, Camera camera, Model mesh, int width, int height, Color fillColor, double[][] zBuffer, Matrix4x4 modelViewProjectionMatrix) {

        // Проходим по всем полигонам модели
        for (Polygon polygon : mesh.getPolygons()) {

            // Получаем вершины полигона в мировых координатах
            ArrayList<Vector3> polygonVertices = new ArrayList<>();
            for (int vertexIndex : polygon.getVertexIndices()) {
                Vector3 vertexVecmath = mesh.getVertices().get(vertexIndex);
                //Vector3 vertexVecmath = new Vector3(vertex.getX(), vertex.getY(), vertex.getZ());

                Vector3 projectedPoint = multiplyMatrix4ByVector3(modelViewProjectionMatrix,vertexVecmath);
                Point2d screenPoint = vertexToPoint(projectedPoint,width,height);


                polygonVertices.add(new Vector3(screenPoint.x, screenPoint.y, projectedPoint.getZ()));
            }


            // Растеризация треугольника
            rasterizeTriangle(graphicsContext, polygonVertices, width, height, fillColor,zBuffer);
        }
    }


    private static void rasterizeTriangle(
            final GraphicsContext graphicsContext,
            final List<Vector3> triangleVertices,
            final int width,
            final int height,
            final Color fillColor,
            double[][] zBuffer
    ) {
        // Получаем целочисленные координаты вершин треугольника
        int x1 = (int) triangleVertices.get(0).getX();
        int y1 = (int) triangleVertices.get(0).getY();
        int x2 = (int) triangleVertices.get(1).getX();
        int y2 = (int) triangleVertices.get(1).getY();
        int x3 = (int) triangleVertices.get(2).getX();
        int y3 = (int) triangleVertices.get(2).getY();

        // Находим ограничивающий прямоугольник
        int minX = Math.max(0, Math.min(x1, Math.min(x2, x3)));
        int minY = Math.max(0, Math.min(y1, Math.min(y2, y3)));
        int maxX = Math.min(width, Math.max(x1, Math.max(x2, x3)));
        int maxY = Math.min(height, Math.max(y1, Math.max(y2, y3)));

        // Предварительно вычисляем штуку но мб это не верно
        double detT = (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);
        double invDetT = 1.0 / detT;

        // Используем алгоритм барицентрических координат для определения принадлежности точек треугольнику
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                double b1 = barycentricCoordinate(
                        x2, y2, x3, y3,
                        x1, y1, x, y,invDetT);
                double b2 = barycentricCoordinate(
                        x3, y3, x1, y1,
                        x2, y2, x, y,invDetT);
                double b3 = barycentricCoordinate(
                        x1, y1, x2, y2,
                        x3, y3, x, y,invDetT);

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
            double x, double y, double invDetT) {
        // Calculate the barycentric coordinates
//        double detT = (y2 - y3) * (x1 - x3) + (x3 - x2) * (y1 - y3);
        double lambda1 = ((y2 - y3) * (x - x3) + (x3 - x2) * (y - y3)) * invDetT ;
        double lambda2 = ((y3 - y1) * (x - x3) + (x1 - x3) * (y - y3)) * invDetT ;
        double lambda3 = 1 - lambda1 - lambda2;


        // Check if the point is inside the triangle
        if (lambda1 >= 0 && lambda1 <= 1 && lambda2 >= 0 && lambda2 <= 1 && lambda3 >= 0 && lambda3 <= 1) {
            // Point is inside the triangle
            return lambda1;
        } else {
            // Point is outside the triangle
            return -1;
        }
    }
    public static Vector3 multiplyMatrix4ByVector3(final Matrix4x4 matrix, final Vector3 vertex) {
        final double x = (vertex.get(0) * matrix.getElem(0, 0)) + (vertex.get(1) * matrix.getElem(1, 0)) + (vertex.get(2) * matrix.getElem(2, 0)) + matrix.getElem(3, 0);
        final double y = (vertex.get(0) * matrix.getElem(0, 1)) + (vertex.get(1) * matrix.getElem(1, 1)) + (vertex.get(2) * matrix.getElem(2, 1)) + matrix.getElem(3, 1);
        final double z = (vertex.get(0) * matrix.getElem(0, 2)) + (vertex.get(1) * matrix.getElem(1, 2)) + (vertex.get(2) * matrix.getElem(2, 2)) + matrix.getElem(3, 2);
        final double w = (vertex.get(0) * matrix.getElem(0, 3)) + (vertex.get(1) * matrix.getElem(1, 3)) + (vertex.get(2) * matrix.getElem(2, 3)) + matrix.getElem(3, 3);
        return new Vector3(x / w, y / w, z / w);
    }

}