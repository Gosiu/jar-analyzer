/*
 * GPLv3 License
 *
 * Copyright (c) 2023-2025 4ra1n (Jar Analyzer Team)
 *
 * This project is distributed under the GPLv3 license.
 *
 * https://github.com/jar-analyzer/jar-analyzer/blob/master/LICENSE
 */

package me.n1ar4.jar.analyzer.config;

public class ConfigFile {
    private String jarPath;
    private String dbPath;
    private String tempPath;
    private String dbSize;
    private String totalJar;
    private String totalClass;
    private String totalMethod;
    private String lang;
    private String theme;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getDbSize() {
        return dbSize;
    }

    public void setDbSize(String dbSize) {
        this.dbSize = dbSize;
    }

    public String getTotalJar() {
        return totalJar;
    }

    public void setTotalJar(String totalJar) {
        this.totalJar = totalJar;
    }

    public String getTotalClass() {
        return totalClass;
    }

    public void setTotalClass(String totalClass) {
        this.totalClass = totalClass;
    }

    public String getTotalMethod() {
        return totalMethod;
    }

    public void setTotalMethod(String totalMethod) {
        this.totalMethod = totalMethod;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "ConfigFile{" +
                "jarPath='" + jarPath + '\'' +
                ", dbPath='" + dbPath + '\'' +
                ", tempPath='" + tempPath + '\'' +
                ", dbSize='" + dbSize + '\'' +
                ", totalJar='" + totalJar + '\'' +
                ", totalClass='" + totalClass + '\'' +
                ", totalMethod='" + totalMethod + '\'' +
                ", lang='" + lang + '\'' +
                ", theme='" + theme + '\'' +
                '}';
    }
}
