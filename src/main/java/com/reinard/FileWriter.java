package com.reinard;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class FileWriter implements Runnable {

    private final byte[] originalFileBytes;
    private final String fileName;
    private final int startIndex;
    private final int endIndex;

    public FileWriter(byte[] originalFileBytes, String fileName, int startIndex, int endIndex) {
        this.originalFileBytes = originalFileBytes;
        this.fileName = fileName;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        byte[] result = Arrays.copyOfRange(originalFileBytes, startIndex, endIndex);
        File file = new File(fileName);
        try {
            Files.write(file.toPath(), result);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
