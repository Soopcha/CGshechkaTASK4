package com.cgvsu.model;

import com.cgvsu.affine_transformation.AffineTransformations;

public class TransformedModel {
    private final TriangulatedModelWithCorrectNormal triangulatedModel;

    private AffineTransformations transformations;


    public TriangulatedModelWithCorrectNormal getTriangulatedModel() {
        return triangulatedModel;
    }

    public AffineTransformations getTransformations() {
        return transformations;
    }

    public TransformedModel(TriangulatedModelWithCorrectNormal triangulatedModel, AffineTransformations transformations) {
        this.triangulatedModel = triangulatedModel;
        this.transformations = transformations;
    }
}