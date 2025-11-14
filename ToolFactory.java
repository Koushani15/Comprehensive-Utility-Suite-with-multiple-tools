package com.koushani.utility.factory;

import com.koushani.utility.tools.Tool;
import com.koushani.utility.tools.filemanager.FileManagerTool;
import com.koushani.utility.tools.loganalyzer.LogAnalyzerTool;
import com.koushani.utility.tools.network.NetworkTool;
import com.koushani.utility.tools.system.SystemMonitorTool;
import com.koushani.utility.tools.text.TextFormatterTool;

public class ToolFactory {
    public static Tool getTool(String option) {
        return switch (option) {
            case "1" -> new FileManagerTool();
            case "2" -> new LogAnalyzerTool();
            case "3" -> new TextFormatterTool();
            case "4" -> new SystemMonitorTool();
            case "5" -> new NetworkTool();
            default -> null;
        };
    }
}
