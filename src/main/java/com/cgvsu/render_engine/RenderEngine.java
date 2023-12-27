package com.cgvsu.render_engine;

import java.util.ArrayList;
import com.cgvsu.math.matrix.Matrix4x4;
import com.cgvsu.math.vector.Vector2;
import com.cgvsu.math.vector.Vector3;
import javafx.scene.canvas.GraphicsContext;
import com.cgvsu.model.Model;
import javafx.scene.paint.Color;
import static com.cgvsu.math.matrix.Matrix4x4.multiplyMatrix4ByVector3;
import static com.cgvsu.math.matrix.Matrix4x4.rotateScaleTranslate;
import static com.cgvsu.math.vector.Vector3.vertexToPoint;
//import static com.cgvsu.render_engine.GraphicConveyor.*;


public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height) {
        Matrix4x4 modelMatrix = rotateScaleTranslate();
        Matrix4x4 viewMatrix = camera.getViewMatrix();
        Matrix4x4 projectionMatrix = camera.getProjectionMatrix();

        Matrix4x4 modelViewProjectionMatrix = modelMatrix.multiply(viewMatrix).multiply(projectionMatrix);

        final int nPolygons = mesh.polygons.size();
        Rasterization rasterization = new Rasterization(graphicsContext, width, height); // Create rasterization instance

        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();

            ArrayList<Vector2> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                Vector3 vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
                Vector3 transformedVertex = multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex);

                Vector2 resultPoint = vertexToPoint(transformedVertex, width, height);
                resultPoints.add(resultPoint);
            }

            // Use your rasterization to paint the triangle with the specified color
            Color fillColor = Color.CYAN; // Change this to the desired fill color
            Triangle triangle = new Triangle(
                    new Vector3(resultPoints.get(0).getX(), resultPoints.get(0).getY(), 0),
                    new Vector3(resultPoints.get(1).getX(), resultPoints.get(1).getY(), 0),
                    new Vector3(resultPoints.get(2).getX(), resultPoints.get(2).getY(), 0),
                    0, 0, 0,
                    null, null, null, null, null, null // You might need to provide texture and normal coordinates
            );

            rasterization.paintTriangle(triangle, fillColor);

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(
                        resultPoints.get(vertexInPolygonInd - 1).getX(),
                        resultPoints.get(vertexInPolygonInd - 1).getY(),
                        resultPoints.get(vertexInPolygonInd).getX(),
                        resultPoints.get(vertexInPolygonInd).getY());
            }

            if (nVerticesInPolygon > 0)
                graphicsContext.strokeLine(
                        resultPoints.get(nVerticesInPolygon - 1).getX(),
                        resultPoints.get(nVerticesInPolygon - 1).getY(),
                        resultPoints.get(0).getX(),
                        resultPoints.get(0).getY());
        }
    }
}






//е
//            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
//                graphicsContext.strokeLine(
//                        resultPoints.get(vertexInPolygonInd - 1).x,
//                        resultPoints.get(vertexInPolygonInd - 1).y,
//                        resultPoints.get(vertexInPolygonInd).x,
//                        resultPoints.get(vertexInPolygonInd).y);
//            }
//
//            if (nVerticesInPolygon > 0)
//                graphicsContext.strokeLine(
//                        resultPoints.get(nVerticesInPolygon - 1).x,
//                        resultPoints.get(nVerticesInPolygon - 1).y,
//                        resultPoints.get(0).x,
//                        resultPoints.get(0).y);


