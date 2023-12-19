package com.cgvsu.model;

import java.util.ArrayList;
import java.util.Objects;

public class Polygon {

    private ArrayList<Integer> vertexIndices;
    private ArrayList<Integer> textureVertexIndices;
    private ArrayList<Integer> normalIndices;


    public Polygon(ArrayList<Integer> vertexIndices, ArrayList<Integer> textureVertexIndices, ArrayList<Integer> normalIndices) {
        this.vertexIndices = vertexIndices;
        this.textureVertexIndices = textureVertexIndices;
        this.normalIndices = normalIndices;
    }

    public Polygon() {
        vertexIndices = new ArrayList<Integer>();
        textureVertexIndices = new ArrayList<Integer>();
        normalIndices = new ArrayList<Integer>();
    }

    public void setVertexIndices(ArrayList<Integer> vertexIndices) {
//        assert vertexIndices.size() >= 3;
        this.vertexIndices = vertexIndices;
    }

    public void setTextureVertexIndices(ArrayList<Integer> textureVertexIndices) {
//        assert textureVertexIndices.size() >= 3;
        this.textureVertexIndices = textureVertexIndices;
    }

    public void setNormalIndices(ArrayList<Integer> normalIndices) {
//        assert normalIndices.size() >= 3;
        this.normalIndices = normalIndices;
    }

    public ArrayList<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public ArrayList<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public ArrayList<Integer> getNormalIndices() {
        return normalIndices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Polygon polygon)) return false;
        return Objects.equals(vertexIndices, polygon.vertexIndices) && Objects.equals(textureVertexIndices, polygon.textureVertexIndices) && Objects.equals(normalIndices, polygon.normalIndices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertexIndices, textureVertexIndices, normalIndices);
    }

   //дописала по коду Артема п
   @Override
    public String toString() {
        StringBuilder string = new StringBuilder("f ");

        for (int index = 0; index < vertexIndices.size(); index++) {
            string.append(vertexIndices.get(index));

            if (textureVertexIndices.size() != 0 || normalIndices.size() != 0) {
                string.append("/");
                if (textureVertexIndices.size() != 0) {
                    string.append(textureVertexIndices.get(index));
                }

                if (normalIndices.size() != 0) {
                    string.append("/");
                    string.append(normalIndices.get(index));
                }
            }

            string.append(" ");
        }

        return string.toString();
    }
}
