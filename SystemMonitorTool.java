package com.koushani.utility.tools.system;

import com.koushani.utility.tools.Tool;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Scanner;

public class SystemMonitorTool implements Tool {
    @Override
    public void run(Scanner scanner) throws Exception {
        System.out.println("-- System Monitor --");
        System.out.println("Showing JVM memory usage (updates 5 times)...");
        MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
        for (int i = 0; i < 5; i++) {
            MemoryUsage heap = mem.getHeapMemoryUsage();
            MemoryUsage nonHeap = mem.getNonHeapMemoryUsage();
            System.out.printf("Heap: used=%d MB, max=%d MB | NonHeap used=%d MB%n",
                    heap.getUsed() / (1024 * 1024),
                    heap.getMax() / (1024 * 1024),
                    nonHeap.getUsed() / (1024 * 1024));
            Thread.sleep(1000);
        }
        System.out.println("Done.");
    }
}
