/*
 * GPLv3 License
 *
 * Copyright (c) 2023-2025 4ra1n (Jar Analyzer Team)
 *
 * This project is distributed under the GPLv3 license.
 *
 * https://github.com/jar-analyzer/jar-analyzer/blob/master/LICENSE
 */

package me.n1ar4.jar.analyzer.gui.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class IconManager {
    public static ImageIcon showIcon;
    public static ImageIcon javaIcon;
    public static ImageIcon dbIcon;
    public static ImageIcon startIcon;
    public static ImageIcon startUpIcon;
    public static ImageIcon jarIcon;
    public static ImageIcon curIcon;
    public static ImageIcon auIcon;
    public static ImageIcon ausIcon;
    public static ImageIcon githubIcon;
    public static ImageIcon whiteIcon;
    public static ImageIcon nextIcon;
    public static ImageIcon prevIcon;
    public static ImageIcon gsIcon;
    public static ImageIcon luceneIcon;
    public static ImageIcon systemIcon;
    public static ImageIcon stringIcon;
    public static ImageIcon callIcon;
    public static ImageIcon pubIcon;
    public static ImageIcon fileIcon;
    public static ImageIcon engineIcon;
    public static ImageIcon cleanIcon;
    public static ImageIcon remoteIcon;
    public static ImageIcon debugIcon;
    public static ImageIcon proxyIcon;
    public static ImageIcon tomcatIcon;
    public static ImageIcon jdIcon;
    public static ImageIcon jdStartIcon;

    static {
        try {
            showIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/logo.png"))));
            Image image = showIcon.getImage();
            Image resizedImage = image.getScaledInstance(90, 75, Image.SCALE_SMOOTH);
            showIcon = new ImageIcon(resizedImage);

            javaIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/java.png"))));

            dbIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/db.png"))));

            startIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/start.png"))));

            startUpIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/startup.png"))));

            jarIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/jar.png"))));

            curIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/cur.png"))));

            auIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/au.png"))));
            image = auIcon.getImage();
            resizedImage = image.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            auIcon = new ImageIcon(resizedImage);
            resizedImage = image.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            ausIcon = new ImageIcon(resizedImage);

            githubIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/github.png"))));

            whiteIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/list.png"))));

            prevIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/prev.png"))));
            resizedImage = prevIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
            prevIcon = new ImageIcon(resizedImage);

            nextIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/next.png"))));
            resizedImage = nextIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
            nextIcon = new ImageIcon(resizedImage);

            gsIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/gs.png"))));
            resizedImage = gsIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            gsIcon = new ImageIcon(resizedImage);

            luceneIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/lucene.png"))));
            resizedImage = luceneIcon.getImage().getScaledInstance(90, 30, Image.SCALE_SMOOTH);
            luceneIcon = new ImageIcon(resizedImage);

            systemIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/system.png"))));
            resizedImage = systemIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            systemIcon = new ImageIcon(resizedImage);

            stringIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/string.png"))));

            callIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/call.png"))));
            resizedImage = callIcon.getImage().getScaledInstance(70, 30, Image.SCALE_SMOOTH);
            callIcon = new ImageIcon(resizedImage);

            pubIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/pub.png"))));

            fileIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/file.png"))));
            resizedImage = fileIcon.getImage().getScaledInstance(70, 30, Image.SCALE_SMOOTH);
            fileIcon = new ImageIcon(resizedImage);

            engineIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/eng.png"))));
            resizedImage = engineIcon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
            engineIcon = new ImageIcon(resizedImage);

            cleanIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/clean.png"))));
            resizedImage = cleanIcon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
            cleanIcon = new ImageIcon(resizedImage);

            remoteIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/remote.png"))));
            resizedImage = remoteIcon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
            remoteIcon = new ImageIcon(resizedImage);

            debugIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/dbg.png"))));
            resizedImage = debugIcon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
            debugIcon = new ImageIcon(resizedImage);

            tomcatIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/tl.png"))));
            resizedImage = tomcatIcon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
            tomcatIcon = new ImageIcon(resizedImage);

            proxyIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/proxy.png"))));
            resizedImage = proxyIcon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH);
            proxyIcon = new ImageIcon(resizedImage);

            jdIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/jd.png"))));
            resizedImage = jdIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            jdIcon = new ImageIcon(resizedImage);

            jdStartIcon = new ImageIcon(ImageIO.read(
                    Objects.requireNonNull(IconManager.class
                            .getClassLoader().getResourceAsStream("img/jd.png"))));
            resizedImage = jdStartIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            jdStartIcon = new ImageIcon(resizedImage);
        } catch (Exception ignored) {
        }
    }
}
