package com.cgvsu.model;

import com.cgvsu.affine_transformation.AffineTransf;

public class TransformedModel {
    private final TriangulatedModelWithCorrectNormal triangulatedModel;

    private AffineTransf transformations;


    public TriangulatedModelWithCorrectNormal getTriangulatedModel() {
        return triangulatedModel;
    }

    public AffineTransf getTransformations() {
        return transformations;
    }

    public TransformedModel(TriangulatedModelWithCorrectNormal triangulatedModel, AffineTransf transformations) {
        this.triangulatedModel = triangulatedModel;
        this.transformations = transformations;
    }
}