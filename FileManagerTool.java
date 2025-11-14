package com.koushani.utility.tools.filemanager;

import com.koushani.utility.tools.Tool;
import com.koushani.utility.utils.Config;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class FileManagerTool implements Tool {
    @Override
    public void run(Scanner scanner) throws Exception {
        System.out.println("-- File Manager --");
        System.out.println("a) List files in directory");
        System.out.println("b) Copy file");
        System.out.println("c) Batch copy (multi-threaded)");
        System.out.print("Choose: ");
        String c = scanner.nextLine().trim();
        switch (c) {
            case "a" -> listFiles(scanner);
            case "b" -> copyFile(scanner);
            case "c" -> batchCopy(scanner);
            default -> System.out.println("Unknown option");
        }
    }

    private void listFiles(Scanner sc) throws IOException {
        System.out.print("Directory: ");
        Path p = Paths.get(sc.nextLine().trim());
        if (!Files.isDirectory(p)) {
            System.out.println("Not a directory");
            return;
        }
        List<Path> items = Files.list(p).collect(Collectors.toList());
        System.out.println("Found: " + items.size());
        items.forEach(path -> System.out.println(path.getFileName() + "\t " + getSize(path) + " bytes"));
    }

    private long getSize(Path path) {
        try {
            if (Files.isDirectory(path)) return -1;
            return Files.size(path);
        } catch (IOException e) {
            return -1;
        }
    }

    private void copyFile(Scanner sc) throws IOException {
        System.out.print("Source file: ");
        Path src = Paths.get(sc.nextLine().trim());
        System.out.print("Destination file: ");
        Path dst = Paths.get(sc.nextLine().trim());
        if (!Files.exists(src) || Files.isDirectory(src)) {
            System.out.println("Source not found or is a directory");
            return;
        }
        Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Copied successfully");
    }

    private void batchCopy(Scanner sc) throws IOException {
        System.out.print("Source directory: ");
        Path srcDir = Paths.get(sc.nextLine().trim());
        System.out.print("Destination directory: ");
        Path dstDir = Paths.get(sc.nextLine().trim());
        if (!Files.isDirectory(srcDir)) {
            System.out.println("Source is not a directory");
            return;
        }
        if (!Files.exists(dstDir)) Files.createDirectories(dstDir);
        List<Path> files = Files.list(srcDir).filter(Files::isRegularFile).collect(Collectors.toList());
        System.out.println("Files to copy: " + files.size());
        ExecutorService exec = Executors.newFixedThreadPool(Config.get().getMaxThreads());
        for (Path f : files) {
            Path target = dstDir.resolve(f.getFileName());
            exec.submit(() -> {
                try {
                    Files.copy(f, target, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Copied " + f.getFileName());
                } catch (IOException e) {
                    System.out.println("Failed " + f.getFileName() + ": " + e.getMessage());
                }
            });
        }
        exec.shutdown();
        while (!exec.isTerminated()) {
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        }
        System.out.println("Batch copy complete");
    }
}
