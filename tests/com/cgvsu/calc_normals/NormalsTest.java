package com.cgvsu.calc_normals;


import com.cgvsu.model.Model;
import com.cgvsu.objreader.IncorrectFileException;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.utils.Normals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NormalsTest {
    @Test
    public void testGetSize01() throws IOException, IncorrectFileException {
        String filePath = "C:\\study\\university\\programming\\java\\2 year\\Graphics\\form\\3DModels\\SimpleModelsForReaderTests\\Test02.obj";
        String fileContent = Files.readString(Path.of(filePath));
        Model testModel1 = ObjReader.read(fileContent);
        int rightRes = testModel1.polygons.size();
        Normals normals = new Normals(testModel1);
        int result = normals.getSize();
        Assertions.assertEquals(rightRes, result);
    }
    @Test
    public void testGetSize02() throws IOException, IncorrectFileException {
        String filePath = "C:\\study\\university\\programming\\java\\2 year\\Graphics\\form\\3DModels\\SimpleModelsForReaderTests\\Test03.obj";
        String fileContent = Files.readString(Path.of(filePath));
        Model testModel2 = ObjReader.read(fileContent);
        int rightRes = testModel2.polygons.size();
        Normals normals = new Normals(testModel2);
        int result = normals.getSize();
        Assertions.assertEquals(rightRes, result);
    }
    //@Test
    /*public void testGetSize03() throws IOException, IncorrectFileException {
        String filePath = "C:\\study\\university\\programming\\java\\2 year\\Graphics\\form\\3DModels\\SimpleModelsForReaderTests\\Test04.obj";
        String fileContent = Files.readString(Path.of(filePath));
        Model testModel3 = ObjReader.read(fileContent);
        int rightRes = testModel3.polygons.size();
        Normals normals = new Normals(testModel3);
        int result = normals.getSize();
        Assertions.assertEquals(rightRes, result);
    }

     */
    @Test
    public void testGetSize04() throws IOException, IncorrectFileException {
        String filePath = "C:\\study\\university\\programming\\java\\2 year\\Graphics\\form\\3DModels\\SimpleModelsForReaderTests\\Test05.obj";
        String fileContent = Files.readString(Path.of(filePath));
        Model testModel4 = ObjReader.read(fileContent);
        int rightRes = testModel4.polygons.size();
        Normals normals = new Normals(testModel4);
        int result = normals.getSize();
        Assertions.assertEquals(rightRes, result);
    }
    @Test
    public void testGetSize05() throws IOException, IncorrectFileException {
        String filePath = "C:\\study\\university\\programming\\java\\2 year\\Graphics\\form\\3DModels\\SimpleModelsForReaderTests\\Test05.obj";
        String fileContent = Files.readString(Path.of(filePath));
        Model testModel5 = ObjReader.read(fileContent);
        int rightRes = testModel5.polygons.size();
        Normals normals = new Normals(testModel5);
        int result = normals.getSize();
        Assertions.assertEquals(rightRes, result);
    }
}
