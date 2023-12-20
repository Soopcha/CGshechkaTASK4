package com.cgvsu.affinetransf;

import com.cgvsu.affine_transformation.AffineTransf;
import com.cgvsu.affine_transformation.OrderRotation;
import com.cgvsu.math.vector.Vector3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AffineTransfTest {
    // todo: через assertEquals не работает, тк координаты векторов в ультра точном значении находятся
    @Test
    void transformVertex01() {
        AffineTransf A = new AffineTransf();

        Vector3 V = new Vector3(2,3,4);
        Vector3 V1 = A.transformVertex(V);
        double delta = 0;
        //Assertions.assertEquals(new Vector3(2,3,4),A.transformVertex(V));
        assertEquals(V1.getX(), V.getX(), delta);
        assertEquals(V1.getY(), V.getY(), delta);
        assertEquals(V1.getZ(), V.getZ(), delta);
    }

    @Test
    void transformVertex02() {
        AffineTransf A = new AffineTransf();
        A.setRz(-90);
        double delta = 1e-6;
        Vector3 V = new Vector3(0,1,0);
        Vector3 V1 = A.transformVertex(V);
        Vector3 V2 = new Vector3(-1,0,0);
        // Assertions.assertEquals(new Vector3(-1,0,0),A.transformVertex(V));
        assertEquals(V1.getX(), V2.getX(), delta);
        assertEquals(V1.getY(), V2.getY(), delta);
        assertEquals(V1.getZ(), V2.getZ(), delta);
    }

    @Test
    void transformVertex02a() {
        AffineTransf A = new AffineTransf();
        A.setRy(-90);
        Vector3 V = new Vector3(1,7,0);
        Vector3 V1 =  A.transformVertex(V);
        Vector3 V2 = new Vector3(0,7,1);
        double delta = 0.00001;
        //  Assertions.assertEquals(new Vector3(0,7,1),A.transformVertex(V));
        assertEquals(V1.getX(), V2.getX(), delta);
        assertEquals(V1.getY(), V2.getY(), delta);
        assertEquals(V1.getZ(), V2.getZ(), delta);
    }


    @Test
    void transformVertex03() {
        AffineTransf A = new AffineTransf(OrderRotation.ZYX,2,6,0.5F,0,0,-60,11,12,15);

        Vector3 V = new Vector3((double) (Math.sqrt(3)/4),(double) 1/12,4);
        Vector3 V1 = A.transformVertex(V);
        Vector3 V2 = new Vector3(11,13,17);
        double delta = 0.00001;
        // Assertions.assertEquals(new Vector3(11,13,17),A.transformVertex(V));
        assertEquals(V1.getX(), V2.getX(), delta);
        assertEquals(V1.getY(), V2.getY(), delta);
        assertEquals(V1.getZ(), V2.getZ(), delta);
    }


    @Test
    void transformVertex04() {
        double delta = 0.00001;
        AffineTransf A = new AffineTransf();
        A.setOr(OrderRotation.ZYX);
        A.setRz(-45);
        A.setRy(-90);
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
        double delta = 0.00001;
        AffineTransf A = new AffineTransf();
        A.setOr(OrderRotation.YXZ);
        A.setRz(-45);
        A.setRy(-90);
        Vector3 V = new Vector3(1,0,0);
        Vector3 V1 = A.transformVertex(V);
        Vector3 V2 = new Vector3(0,0,1);
        // Assertions.assertEquals(new Vector3(0,0,1),A.transformVertex(V));
        assertEquals(V1.getX(), V2.getX(), delta);
        assertEquals(V1.getY(), V2.getY(), delta);
        assertEquals(V1.getZ(), V2.getZ(), delta);


    }
}