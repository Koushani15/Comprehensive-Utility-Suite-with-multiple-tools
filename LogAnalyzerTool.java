package com.koushani.utility.tools.loganalyzer;

import com.koushani.utility.tools.Tool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LogAnalyzerTool implements Tool {
    @Override
    public void run(Scanner scanner) throws Exception {
        System.out.println("-- Log Analyzer --");
        System.out.print("Enter log file path: ");
        Path p = Paths.get(scanner.nextLine().trim());
        if (!Files.exists(p)) {
            System.out.println("File does not exist");
            return;
        }
        analyze(p);
    }

    private void analyze(Path p) throws IOException {
        List<String> lines = Files.lines(p).collect(Collectors.toList());
        long errorCount = lines.stream().filter(l -> l.toLowerCase().contains("error")).count();
        long warnCount = lines.stream().filter(l -> l.toLowerCase().contains("warn")).count();
        System.out.println("Total lines: " + lines.size());
        System.out.println("Errors: " + errorCount);
        System.out.println("Warnings: " + warnCount);
        System.out.println("Last 10 lines:");
        lines.stream().skip(Math.max(0, lines.size() - 10)).forEach(System.out::println);
    }
}
