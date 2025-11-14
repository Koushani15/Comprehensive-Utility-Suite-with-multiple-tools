package com.koushani.utility.tools.text;

import com.koushani.utility.tools.Tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple CSV -> JSON converter (no external dependency).
 * Note: It is minimal and assumes commas are column separators without quoting logic.
 */
public class TextFormatterTool implements Tool {
    @Override
    public void run(Scanner scanner) throws Exception {
        System.out.println("-- Text Formatter --");
        System.out.println("a) CSV -> JSON (simple)");
        System.out.print("Choose: ");
        String c = scanner.nextLine().trim();
        if (c.equals("a")) csvToJson(scanner);
        else System.out.println("Unknown option");
    }

    private void csvToJson(Scanner sc) throws IOException {
        System.out.print("CSV file path: ");
        Path csv = Paths.get(sc.nextLine().trim());
        if (!Files.exists(csv)) { System.out.println("File not found"); return; }
        try (BufferedReader br = Files.newBufferedReader(csv)) {
            String headerLine = br.readLine();
            if (headerLine == null) { System.out.println("Empty file"); return; }
            String[] headers = headerLine.split(",");
            List<Map<String, String>> rows = br.lines().map(line -> {
                String[] cols = line.split(",");
                Map<String, String> map = new LinkedHashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    String key = headers[i].trim();
                    String val = i < cols.length ? cols[i].trim() : "";
                    map.put(key, val);
                }
                return map;
            }).collect(Collectors.toList());

            // print JSON-like output
            System.out.println("[");
            for (int i = 0; i < rows.size(); i++) {
                Map<String,String> r = rows.get(i);
                String obj = r.entrySet().stream()
                        .map(e -> "\"" + e.getKey() + "\": \"" + escape(e.getValue()) + "\"")
                        .collect(Collectors.joining(", "));
                System.out.println("  {" + obj + "}" + (i < rows.size()-1 ? "," : ""));
            }
            System.out.println("]");
        }
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
