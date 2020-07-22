package com.reinard;

import org.junit.Test;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.assertEquals;

public class AppTest {

    @Test
    public void testGenerateJobData() {
        final int originalFileSize = 1000;
        final int splitBytes = 256;
        final int splitFile = 3;
        final int remainingBytes = 232;

        Queue<Map<String, Object>> jobQueue = new ConcurrentLinkedQueue();

        App.generateJobData(originalFileSize, splitBytes, splitFile, remainingBytes, jobQueue);

        Map<String, Object> job1 = jobQueue.poll();
        assertEquals("part1", job1.get("fileName"));
        assertEquals(new Integer(0), job1.get("startIndex"));
        assertEquals(new Integer(256), job1.get("endIndex"));

        Map<String, Object> job2 = jobQueue.poll();
        assertEquals("part2", job2.get("fileName"));
        assertEquals(new Integer(256), job2.get("startIndex"));
        assertEquals(new Integer(512), job2.get("endIndex"));

        Map<String, Object> job3 = jobQueue.poll();
        assertEquals("part3", job3.get("fileName"));
        assertEquals(new Integer(512), job3.get("startIndex"));
        assertEquals(new Integer(768), job3.get("endIndex"));

        Map<String, Object> job4 = jobQueue.poll();
        assertEquals("part4", job4.get("fileName"));
        assertEquals(new Integer(768), job4.get("startIndex"));
        assertEquals(new Integer(1000), job4.get("endIndex"));
    }

    @Test
    public void testGenerateJobDataWithNoRemainingBytes() {
        final int originalFileSize = 768;
        final int splitBytes = 256;
        final int splitFile = 3;
        final int remainingBytes = 0;

        Queue<Map<String, Object>> jobQueue = new ConcurrentLinkedQueue();

        App.generateJobData(originalFileSize, splitBytes, splitFile, remainingBytes, jobQueue);

        Map<String, Object> job1 = jobQueue.poll();
        assertEquals("part1", job1.get("fileName"));
        assertEquals(new Integer(0), job1.get("startIndex"));
        assertEquals(new Integer(256), job1.get("endIndex"));

        Map<String, Object> job2 = jobQueue.poll();
        assertEquals("part2", job2.get("fileName"));
        assertEquals(new Integer(256), job2.get("startIndex"));
        assertEquals(new Integer(512), job2.get("endIndex"));

        Map<String, Object> job3 = jobQueue.poll();
        assertEquals("part3", job3.get("fileName"));
        assertEquals(new Integer(512), job3.get("startIndex"));
        assertEquals(new Integer(768), job3.get("endIndex"));
    }
}
