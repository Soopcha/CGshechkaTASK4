package com.cgvsu.render_engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Rasterization {

    private final GraphicsContext graphicsContext;
    private final PixelWriter pixelWriter;
    private final int width;
    private final int height;
    private final double[][] zBuffer;

    private double z1;
    private double z2;
    private double z3;
    private double x3;
    private double y3;
    private double caff1;
    private double caff2;
    private double caff3;
    private double caff4;
    private double caff5;

    public Rasterization(GraphicsContext graphicsContext, int width, int height) {
        this.graphicsContext = graphicsContext;
        this.pixelWriter = graphicsContext.getPixelWriter();
        this.width = width;
        this.height = height;
        this.zBuffer = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                zBuffer[i][j] = Double.NEGATIVE_INFINITY;
            }
        }
    }

    private void paintPoint(int x, int y, Color color) {
        double alpha = (double) ((x - x3) * caff4 - (y - y3) * caff5) / caff2;
        double beta = (double) (x - x3) / caff3 - alpha * caff1;
        double gamma = 1 - alpha - beta;
        double sum = alpha + beta + gamma;

        alpha /= sum;
        beta /= sum;
        gamma /= sum;

        double z = (double) (alpha * z1 + beta * z2 + gamma * z3);

        if (x < 0 || y < 0 || x >= width || y >= height || !(z >= zBuffer[x][y])) {
            return;
        }

        this.zBuffer[x][y] = z;
        pixelWriter.setColor(x, y, color);
    }

    public void paintTriangle(Triangle triangle, Color fillColor) {
        this.z1 = triangle.getZ1();
        this.z2 = triangle.getZ2();
        this.z3 = triangle.getZ3();

        paintPointsTriangle(
                (int) Math.round(triangle.getA().getX()),
                (int) Math.round(triangle.getA().getY()),
                (int)Math.round(triangle.getB().getX()),
                (int)Math.round(triangle.getB().getY()),
                (int)Math.round(triangle.getC().getX()),
                (int)Math.round(triangle.getC().getY()),
                fillColor
        );
    }

    private void paintPointsTriangle(int x1, int y1, int x2, int y2, int x3, int y3, Color fillColor) {
        this.x3 = x3;
        this.y3 = y3;

        boolean flag = false;
        if (x2 == x3) {
            x3++;
            flag = true;
        }

        caff1 = (x1 - x3) / (x2 - x3);
        caff2 = ((x1 - x3) * (y2 - y3) - (y1 - y3) * (x2 - x3));
        caff3 = (x2 - x3);
        caff4 = (y2 - y3);
        caff5 = (x2 - x3);


        if (flag) {
            x3--;
        }

        if (y2 < y1) {
            y1 = y1 ^ y2 ^ (y2 = y1);
            x1 = x1 ^ x2 ^ (x2 = x1);
        }
        if (y3 < y1) {
            y1 = y1 ^ y3 ^ (y3 = y1);
            x1 = x1 ^ x3 ^ (x3 = x1);
        }
        if (y2 > y3) {
            y2 = y2 ^ y3 ^ (y3 = y2);
            x2 = x2 ^ x3 ^ (x3 = x2);
        }

        double dx13 = 0.0;
        double dx12 = 0.0;
        double dx23 = 0.0;
        if (y3 != y1) {
            dx13 = (x3 - x1) / (y3 - y1);
        }
        if (y2 != y1) {
            dx12 = (x2 - x1) / (y2 - y1);
        }
        if (y3 != y2) {
            dx23 = (x3 - x2) / (y3 - y2);
        }
        double wx1 = x1;
        double wx2 = wx1;
        double _dx13 = dx13;
        if (dx13 > dx12) {
            double c = dx12;
            dx12 = dx13;
            dx13 = c;
        }
        for (int i = y1; i < y2; i++) {
            for (int j = Math.round((float) wx1); j <= Math.round((float) wx2); j++) {
                paintPoint(j, i, fillColor);
            }
            wx1 += dx13;
            wx2 += dx12;
        }
    }
}