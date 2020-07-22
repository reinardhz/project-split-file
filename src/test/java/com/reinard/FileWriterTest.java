package com.reinard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileWriterTest {

    private String resourceFile;
    private String newFile;

    @Before
    public void setUp() {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.indexOf("win") >= 0) {
            //windows path
            resourceFile = ".\\src\\test\\resources\\test.txt";
            newFile = ".\\src\\test\\resources\\part1";
        } else {
            //unix path
            resourceFile = "./src/test/resources/test.txt";
            newFile = "./src/test/resources/part1";
        }
    }

    @Test
    public void testRun() throws Exception {

        Path path = Paths.get(new File(resourceFile).getAbsolutePath());

        byte[] originalFileBytes = Files.readAllBytes(path);
        int startIndex = 0;
        int endIndex = 256;

        FileWriter fileWriter = new FileWriter(originalFileBytes, newFile, startIndex, endIndex);
        fileWriter.run();

        File file = new File(newFile);
        assertTrue(file.exists());
        assertEquals(256L, file.length());
    }

    @Test
    public void testRun2() throws Exception {
        Path path = Paths.get(new File(resourceFile).getAbsolutePath());

        byte[] originalFileBytes = Files.readAllBytes(path);
        int startIndex = 256;
        int endIndex = 512;

        FileWriter fileWriter = new FileWriter(originalFileBytes, newFile, startIndex, endIndex);
        fileWriter.run();

        File file = new File(newFile);
        assertTrue(file.exists());
        assertEquals(256L, file.length());
    }

    @After
    public void cleanUp() {
        File file = new File(newFile);
        file.delete();
    }

}