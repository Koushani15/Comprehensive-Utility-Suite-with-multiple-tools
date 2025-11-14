package com.koushani.utility;

import com.koushani.utility.factory.ToolFactory;
import com.koushani.utility.tools.Tool;

import java.util.Scanner;

public class utilitySuite {
    public static void main(String[] args) {
        System.out.println("=== Utility Suite ===");
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println();
                System.out.println("Choose a tool:");
                System.out.println("1) File Manager");
                System.out.println("2) Log Analyzer");
                System.out.println("3) Text Formatter (CSV -> JSON)");
                System.out.println("4) System Monitor");
                System.out.println("5) Network Utility (Ping)");
                System.out.println("0) Exit");
                System.out.print("Enter choice: ");
                String opt = sc.nextLine().trim();
                if (opt.equals("0")) break;
                Tool tool = ToolFactory.getTool(opt);
                if (tool == null) {
                    System.out.println("Invalid option.");
                    continue;
                }
                try {
                    tool.run(sc);
                } catch (Exception e) {
                    System.out.println("Tool failed: " + e.getMessage());
                    e.printStackTrace(System.out);
                }
            }
        }
        System.out.println("Goodbye!");
    }
}
