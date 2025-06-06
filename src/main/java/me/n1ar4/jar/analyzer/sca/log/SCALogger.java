/*
 * GPLv3 License
 *
 * Copyright (c) 2023-2025 4ra1n (Jar Analyzer Team)
 *
 * This project is distributed under the GPLv3 license.
 *
 * https://github.com/jar-analyzer/jar-analyzer/blob/master/LICENSE
 */

package me.n1ar4.jar.analyzer.sca.log;

import javax.swing.*;

public class SCALogger {
    public static SCALogger logger;
    private final JTextArea logArea;

    public SCALogger(JTextArea area) {
        this.logArea = area;
    }

    public void print(String s) {
        this.logArea.append(s);
        this.logArea.setCaretPosition(this.logArea.getDocument().getLength());
    }

    private void log(String level, String msg) {
        String logInfo = "[" + level + "] " + msg + "\n";
        this.logArea.append(logInfo);
        this.logArea.setCaretPosition(this.logArea.getDocument().getLength());
    }

    public void info(String msg) {
        log("INFO", msg);
    }

    public void warn(String msg) {
        log("WARN", msg);
    }

    public void error(String msg) {
        log("ERROR", msg);
    }
}
