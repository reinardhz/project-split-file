package com.reinard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws IOException {
        if (args.length < 5) {
            warn();
        }

        if (!args[0].equals("-b")) {
            warn();
        }

        if (!isNumber(args[1])) {
            warn();
        }

        if (!args[2].equals("-t")) {
            warn();
        }

        if (!isNumber(args[3])) {
            warn();
        }

        //1. Find out the split file and remaining bytes
        final int splitBytes = Integer.valueOf(args[1]);
        final byte[] originalFileBytes = Files.readAllBytes(Paths.get(args[4]));
        final int originalFileSize = originalFileBytes.length;
        final int splitFile = originalFileSize / splitBytes;
        final int remainingBytes = originalFileSize % splitBytes;

        final Queue<Map<String, Object>> jobQueue = new ConcurrentLinkedQueue();

        //2. Determine the start index and end index to copy
        generateJobData(originalFileSize, splitBytes, splitFile, remainingBytes, jobQueue);

        //3. Use multithread to generate part files
        generateSplitFile(Integer.valueOf(args[3]), jobQueue, originalFileBytes);
    }

    private static boolean isNumber(String string) {
        try {
            Integer.valueOf(string);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private static void warn() {
        System.out.println("Usage: java -Xmx[memory_in_mega_bytes]M -jar split-1.0.jar -b [bytes] -t [number_of_threads] [file_path]");
        System.out.println("Example: java -Xmx2000M -jar split-1.0.jar -b 256 -t 4 \"./test.txt\"");
        System.out.println("Note: You should increase java run time memory (heap) more than the size of the file that you want to split.");
        System.exit(0);
    }

    static Queue generateJobData(final int originalFileSize, final int splitBytes, int splitFile, int remainingBytes, Queue jobQueue) {
        int startIndex = 0;
        int endIndex = splitBytes;

        int i;
        for (i = 0; i < splitFile; i++) {

            if (i == 0) {
                startIndex = 0;
                endIndex = splitBytes;
                Map<String, Object> map = new HashMap();
                map.put("fileName", "part" + (i + 1));
                map.put("startIndex", startIndex);
                map.put("endIndex", endIndex);
                jobQueue.add(map);
                continue;
            }

            startIndex = endIndex;
            endIndex = startIndex + splitBytes;
            Map<String, Object> map = new HashMap();
            map.put("fileName", "part" + (i + 1));
            map.put("startIndex", startIndex);
            map.put("endIndex", endIndex);
            jobQueue.add(map);
        }

        if (remainingBytes > 0) {
            startIndex = endIndex;
            Map<String, Object> map = new HashMap();
            map.put("fileName", "part" + (i + 1));
            map.put("startIndex", startIndex);
            map.put("endIndex", originalFileSize);
            jobQueue.add(map);
        }

        return jobQueue;
    }

    static void generateSplitFile(final int thread, Queue<Map<String, Object>> jobQueue, final byte[] originalFileBytes) {
        ExecutorService pool = Executors.newFixedThreadPool(thread);

        do {
            Map<String, Object> job = jobQueue.poll();

            if (job == null) {
                break;
            }

            pool.submit(new FileWriter(originalFileBytes, job.get("fileName").toString(),
                    (Integer) job.get("startIndex"), (Integer) job.get("endIndex")));

        } while (true);

        pool.shutdown();
    }
}


