package com.koushani.utility.tools.network;

import com.koushani.utility.tools.Tool;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class NetworkTool implements Tool {
    @Override
    public void run(Scanner scanner) throws Exception {
        System.out.println("-- Network Utility --");
        System.out.print("Host to ping (e.g. google.com): ");
        String host = scanner.nextLine().trim();
        ping(host);
    }

    private void ping(String host) {
        try {
            InetAddress addr = InetAddress.getByName(host);
            System.out.println("Address: " + addr.getHostAddress());
            boolean reachable = addr.isReachable(3000); // 3 seconds
            System.out.println("Reachable: " + reachable);
        } catch (IOException e) {
            System.out.println("Ping failed: " + e.getMessage());
        }
    }
}
