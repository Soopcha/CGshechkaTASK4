package com.cgvsu.affinetransf;

import com.cgvsu.affine_transformation.AffineTransformations;
import com.cgvsu.affine_transformation.RotationOrder;
import com.cgvsu.math.vector.Vector3;
import org.junit.jupiter.api.Test;

import static com.cgvsu.math.MathSettings.EPS;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AffineTransformationsTest {
    @Test
    void transformVertex01() {
        AffineTransformations A = new AffineTransformations();

        Vector3 V = new Vector3(2,3,4);
        Vector3 V1 = A.transformVertex(V);
        double delta = 0;
        assertEquals(V1.getX(), V.getX(), delta);
        assertEquals(V1.getY(), V.getY(), delta);
        assertEquals(V1.getZ(), V.getZ(), delta);
    }

    @Test
    void transformVertex02() {
        AffineTransformations A = new AffineTransformations();
        A.setRotationZ(-90);
        double delta = 0.1;
        Vector3 V = new Vector3(0,1,0);
        Vector3 V1 = A.transformVertex(V);
        Vector3 V2 = new Vector3(-1,0,0);
        assertEquals(V1.getX(), V2.getX(), delta);
        assertEquals(V1.getY(), V2.getY(), delta);
        assertEquals(V1.getZ(), V2.getZ(), delta);
    }
    @Test
    void transformVertex03() {
        AffineTransformations A = new AffineTransformations(RotationOrder.ZYX,2,6,0.5F,0,0,-60,11,12,15);

        Vector3 V = new Vector3((double) (Math.sqrt(3)/4),(double) 1/12,4);
        Vector3 V1 = A.transformVertex(V);
        Vector3 V2 = new Vector3(11,13,17);
        double delta = 0.00001;
        assertEquals(V1.getX(), V2.getX(), delta);
        assertEquals(V1.getY(), V2.getY(), delta);
        assertEquals(V1.getZ(), V2.getZ(), delta);
    }


    @Test
    void transformVertex04() {
        double delta = 0.1;
        AffineTransformations A = new AffineTransformations();
        A.setRotationOrder(RotationOrder.ZYX);
        A.setRotationZ(-45);
        A.setRotationY(-90);
        Vector3 V = new Vector3(1,0,0);
        Vector3 V1 = A.transformVertex(V);
        Vector3 V2 = new Vector3(0,(float) Math.sqrt(2)/2,(float) Math.sqrt(2)/2);
        //Assertions.assertEquals(new Vector3(0,(float) Math.sqrt(2)/2,(float) Math.sqrt(2)/2),A.transformVertex(V));
        assertEquals(V1.getX(), V2.getX(), delta);
        assertEquals(V1.getY(), V2.getY(), delta);
        assertEquals(V1.getZ(), V2.getZ(), delta);
    }

    @Test
    void transformVertex04a() {
        double delta = 0.1;
        AffineTransformations A = new AffineTransformations();
        A.setRotationOrder(RotationOrder.YXZ);
        A.setRotationZ(-45);
        A.setRotationY(-90);
        Vector3 V = new Vector3(1,0,0);
        Vector3 V1 = A.transformVertex(V);
        Vector3 V2 = new Vector3(0,0,1);
        // Assertions.assertEquals(new Vector3(0,0,1),A.transformVertex(V));
        assertEquals(V1.getX(), V2.getX(), delta);
        assertEquals(V1.getY(), V2.getY(), delta);
        assertEquals(V1.getZ(), V2.getZ(), delta);


    }
}