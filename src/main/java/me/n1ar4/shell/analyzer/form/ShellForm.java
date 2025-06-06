/*
 * GPLv3 License
 *
 * Copyright (c) 2023-2025 4ra1n (Jar Analyzer Team)
 *
 * This project is distributed under the GPLv3 license.
 *
 * https://github.com/jar-analyzer/jar-analyzer/blob/master/LICENSE
 */

package me.n1ar4.shell.analyzer.form;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.n1ar4.agent.dto.SourceResult;
import com.n1ar4.agent.dto.UrlInfo;
import com.n1ar4.agent.dto.UrlInfoAndDescMapValue;
import me.n1ar4.jar.analyzer.gui.MainForm;
import me.n1ar4.jar.analyzer.gui.util.ProcessDialog;
import me.n1ar4.shell.analyzer.model.ClassObj;
import me.n1ar4.shell.analyzer.model.InfoObj;
import me.n1ar4.shell.analyzer.start.SocketHelper;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jetbrains.java.decompiler.main.decompiler.ConsoleDecompiler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

public class ShellForm {
    private static final String DEFAULT_PASSWD = "P4sSW0rD";

    class CommonMouse extends MouseAdapter {
        public void mouseClicked(MouseEvent evt) {
            JList<?> list = (JList<?>) evt.getSource();
            if (evt.getClickCount() == 2) {
                core(evt, list);
            }
        }
    }

    class PanelInfo {
        public String title;
        public JPanel panel;
        public JList<ClassObj> dataList;
        public DefaultListModel<ClassObj> dataModel;
        public List<ClassObj> dataCache;

        public PanelInfo(String title) {
            this.title = title;
            this.panel = new JPanel();
            panel.setLayout(new GridLayoutManager(1, 1,
                    new Insets(0, 0, 0, 0), -1, -1));
            panel.setBorder(BorderFactory.createTitledBorder(null, "",
                    TitledBorder.DEFAULT_JUSTIFICATION,
                    TitledBorder.DEFAULT_POSITION, null, null));
            JScrollPane newJScroll = new JScrollPane();
            this.dataList = new JList<>();
            panel.add(newJScroll, new GridConstraints(0, 0,
                    1, 1, GridConstraints.ANCHOR_CENTER,
                    GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW,
                    null, null,
                    null, 0, false));
            newJScroll.setViewportView(this.dataList);
            this.dataList.addMouseListener(new CommonMouse());
            this.dataModel = new DefaultListModel<>();
            this.dataCache = new ArrayList<>();
        }

        public void dataSort() {
            Collections.sort(this.dataCache);
            for (ClassObj co : this.dataCache) {
                this.dataModel.addElement(co);
            }
        }
    }

    class UrlInfoMouse extends MouseAdapter {
        public void mouseClicked(MouseEvent evt) {
            JList<?> list = (JList<?>) evt.getSource();
            if (evt.getClickCount() == 2) {
                doUrlInfo(evt, list);
            }
        }
    }

    public static ShellForm instance;
    private JPanel shellPanel;
    private JPanel rootPanel;
    private JPanel topPanel;
    private JButton attachButton;
    private JTabbedPane tabbedPane;
    private JPanel normalPanel;
    private JPanel codePanel;
    private JPanel logPanel;
    private JScrollPane logScroll;
    private JTextArea logArea;
    private JTextField passText;
    private JLabel passLabel;
    private JButton genButton;
    private JTextField targetIPText;
    private JTextField targetPortText;
    private JLabel targetIPLabel;
    private JLabel targetPortLabel;
    private JTextArea cmdArea;
    private JPanel initPanel;
    private JTextField scNameText;
    private JPanel infoPanel;
    private JLabel urlLabel;
    private JTextField scText;
    private JLabel scLabel;
    private JScrollPane urlScroll;
    private JList<InfoObj> urlList;

    private final HashMap<String, PanelInfo> srpMap = new HashMap<>();

    private static final DefaultListModel<InfoObj> infoModel = new DefaultListModel<>();

    private static final Map<String, List<SourceResult>> staticMap = new HashMap<>();

    private void analyze() {
        try {
            srpMap.clear();
            List<SourceResult> sourceResults = SocketHelper.getSourceResults();
            for (SourceResult sourceResult : sourceResults) {
                if (sourceResult.getSourceClass().equals("null")) {
                    continue;
                }
                String sourceResultType = sourceResult.type.name();
                PanelInfo panelInfo = srpMap.get(sourceResultType);
                if (panelInfo == null) {
                    panelInfo = new PanelInfo(sourceResultType);
                    srpMap.put(sourceResultType, panelInfo);
                }
                ClassObj co = new ClassObj(sourceResult.getSourceClass(), sourceResultType);
                if (!panelInfo.dataCache.contains(co)) {
                    panelInfo.dataCache.add(co);
                }
                // ADD TO MAP
                if (staticMap.get(sourceResult.getSourceClass()) == null) {
                    List<SourceResult> list = new ArrayList<>();
                    list.add(sourceResult);
                    staticMap.put(sourceResult.getSourceClass(), list);
                } else {
                    staticMap.get(sourceResult.getSourceClass()).add(sourceResult);
                }
            }
        } catch (Exception ex) {
            log("cannot get message : " + ex.getMessage());
        }
        try {
            List<String> valves = SocketHelper.getAllValves();
            PanelInfo panelInfo = srpMap.get("Value");
            if (panelInfo == null) {
                panelInfo = new PanelInfo("Valve");
                srpMap.put("Valve", panelInfo);
            }
            for (String v : valves) {
                ClassObj co = new ClassObj(v, "VALVE");
                panelInfo.dataCache.add(co);
            }
        } catch (Exception ex) {
            log("cannot get message : " + ex.getMessage());
        }
        tabbedPane.removeAll();
        for (PanelInfo nowPanelInfo : srpMap.values()) {
            nowPanelInfo.dataSort();
            nowPanelInfo.dataList.setModel(nowPanelInfo.dataModel);
            tabbedPane.add(nowPanelInfo.title, nowPanelInfo.panel);
        }
        urlList.setModel(infoModel);
    }

    private static RSyntaxTextArea codeArea;

    @SuppressWarnings("all")
    public ShellForm() {
        codeArea = new RSyntaxTextArea();
        codeArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        codeArea.setCodeFoldingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(codeArea);
        codePanel.add(sp, new GridConstraints(0, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null, 0, false));

        genButton.addActionListener(e -> {
            String command = "-javaagent:agent.jar=port=%s;password=%s";
            command = String.format(command, targetPortText.getText(), passText.getText());

            String output = "1. 远程启动你的 JAVA 程序\n" +
                    "请在你的启动参数中添加 " + command + "\n" +
                    "对于 Tomcat 来说通常是修改 startup.bat/startup.sh 部分代码\n" +
                    "2. 配置 IP 和 PASSWORD 信息后点击 CONNECT\n" +
                    "3. 如果没有自动显示信息可以尝试点击右侧的 刷新 按钮\n" +
                    "注意：在 Tomcat 中修改的是 catalina.bat/sh 参考弹出图片";

            cmdArea.setText(output);

            InputStream tomcatIs = ShellForm.class.getClassLoader().getResourceAsStream("img/tomcat.png");
            try {
                BufferedImage image = ImageIO.read(tomcatIs);
                JFrame frame = new JFrame("config");
                frame.setSize(882, 539);
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                frame.add(imageLabel);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception ignored) {
            }
        });

        attachButton.addActionListener(e -> {
            String host = targetIPText.getText();
            String port = targetPortText.getText();
            String pass = passText.getText();
            if (pass.length() != 8) {
                JOptionPane.showMessageDialog(shellPanel, "请输入长度为 8 的密码");
                return;
            }

            SocketHelper.setHost(host);
            SocketHelper.setPort(port);
            SocketHelper.setPass(pass);

            staticMap.clear();

            JDialog dialog = ProcessDialog.createProgressDialog(MainForm.getInstance().getMasterPanel());
            new Thread(() -> dialog.setVisible(true)).start();
            new Thread() {
                @Override
                public void run() {
                    if (SocketHelper.check()) {
                        log("tcp connect success");
                        dialog.dispose();
                        analyze();
                    } else {
                        log("cannot connect to target");
                        dialog.dispose();
                        JOptionPane.showMessageDialog(rootPanel, "无法建立连接");
                    }
                }
            }.start();
        });

        urlList.addMouseListener(new UrlInfoMouse());
    }

    public void doUrlInfo(MouseEvent evt, JList<?> list) {
        String pass = passText.getText();
        if (pass.length() != 8) {
            JOptionPane.showMessageDialog(shellPanel, "请输入密码");
        }
        SocketHelper.setPass(pass);

        int index = list.locationToIndex(evt.getPoint());
        InfoObj res = (InfoObj) list.getModel().getElementAt(index);
        MessageForm.start0(res);
    }

    @SuppressWarnings("all")
    public void core(MouseEvent evt, JList<?> list) {
        String pass = passText.getText();
        if (pass.length() != 8) {
            JOptionPane.showMessageDialog(shellPanel, "请输入密码");
        }
        SocketHelper.setPass(pass);

        int index = list.locationToIndex(evt.getPoint());
        ClassObj res = (ClassObj) list.getModel().getElementAt(index);
        infoModel.clear();

        List<InfoObj> infoCache = new ArrayList<>();

        // 渲染具体信息
        List<SourceResult> results = staticMap.get(res.getClassName());
        if (results != null && !results.isEmpty()) {
            SourceResult sr = results.get(0);
            scText.setText(sr.getSourceClass());
            scNameText.setText(sr.getName());
            HashMap<String, UrlInfoAndDescMapValue> sourceTagMapForUrlInfosAndDesc =
                    sr.getSourceTagMapForUrlInfosAndDesc();
            for (UrlInfoAndDescMapValue value : sourceTagMapForUrlInfosAndDesc.values()) {
                for (UrlInfo u : value.urlInfos) {
                    InfoObj infoObj = new InfoObj();
                    infoObj.setUrl(u.url);
                    infoObj.setUrlDesc(u.description);
                    infoObj.setHash(value.tag);
                    infoObj.setGlobalDesc(value.desc);
                    infoCache.add(infoObj);
                }
            }

            Collections.sort(infoCache);

            for (InfoObj infoObj : infoCache) {
                infoModel.addElement(infoObj);
            }
        } else {
            scText.setText("NONE");
            scNameText.setText("NONE");
            infoModel.clear();
        }

        log("get bytecode : " + res.getClassName());

        new Thread(() -> {
            try {
                SocketHelper.getBytecode(res.getClassName());
            } catch (Exception ex) {
                log("cannot connect to target : " + ex.getMessage());
            }
            String classPath = "test.class";
            String javaDir = ".";
            Path javaPathPath = Paths.get("test.java");
            if (!Files.exists(Paths.get(classPath))) {
                log("unknown error");
                return;
            }
            try {
                Files.delete(javaPathPath);
            } catch (Exception ignored) {
            }
            String tips = "error";
            new Thread(() -> {
                String total;
                String[] args = new String[]{
                        classPath,
                        javaDir
                };
                try {
                    Files.delete(javaPathPath);
                } catch (IOException ignored) {
                }
                ConsoleDecompiler.main(args);
                try {
                    total = new String(Files.readAllBytes(javaPathPath));
                    if (total.trim().isEmpty()) {
                        total = tips;
                    } else {
                        total = "// Decompiled by FernFlower\n" +
                                "// Tomcat Analyzer from Jar Analyzer V2 @ 4ra1n\n" +
                                "// PROJECT: https://github.com/jar-analyzer/jar-analyzer\n" +
                                total;
                    }
                } catch (Exception ignored) {
                    total = tips;
                }
                try {
                    Files.delete(javaPathPath);
                } catch (IOException ignored) {
                }
                // 2024/09/24 FIX
                // DELETE TEMP CLASS FILE
                try {
                    Files.delete(Paths.get(classPath));
                } catch (IOException ignored) {
                }

                total = total.replace("\r\n", "\n");
                codeArea.setText(total);
            }).start();
        }).start();
    }

    public static void log(String l) {
        String text = String.format("[*] %s\n", l);
        instance.logArea.append(text);
    }

    public static void start0() {
        JFrame frame = new JFrame("tomcat-analyzer by 4ra1n");
        instance = new ShellForm();

        instance.passText.setText(DEFAULT_PASSWD);
        instance.targetIPText.setText("127.0.0.1");
        instance.targetPortText.setText("10033");

        frame.setContentPane(instance.shellPanel);

        frame.pack();

        frame.setLocationRelativeTo(MainForm.getInstance().getMasterPanel());

        frame.setResizable(false);
        frame.setSize(1600, 800);

        frame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        shellPanel = new JPanel();
        shellPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(2, 8, new Insets(0, 0, 0, 0), -1, -1));
        shellPanel.add(rootPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayoutManager(3, 5, new Insets(0, 0, 0, 0), -1, -1));
        topPanel.setEnabled(false);
        rootPanel.add(topPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        topPanel.setBorder(BorderFactory.createTitledBorder(null, "START", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        attachButton = new JButton();
        attachButton.setText("CONNECT");
        topPanel.add(attachButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        initPanel = new JPanel();
        initPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        topPanel.add(initPanel, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        passLabel = new JLabel();
        passLabel.setText("TOKEN");
        initPanel.add(passLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        genButton = new JButton();
        genButton.setText("GENERATE CMD");
        initPanel.add(genButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passText = new JTextField();
        initPanel.add(passText, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        targetIPLabel = new JLabel();
        targetIPLabel.setText("TARGET IP");
        topPanel.add(targetIPLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        targetIPText = new JTextField();
        topPanel.add(targetIPText, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        targetPortLabel = new JLabel();
        targetPortLabel.setText("PORT");
        topPanel.add(targetPortLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        targetPortText = new JTextField();
        topPanel.add(targetPortText, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cmdArea = new JTextArea();
        cmdArea.setLineWrap(true);
        topPanel.add(cmdArea, new GridConstraints(2, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        normalPanel = new JPanel();
        normalPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(normalPanel, new GridConstraints(0, 1, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(450, -1), null, null, 0, false));
        normalPanel.setBorder(BorderFactory.createTitledBorder(null, "COMPONENTS", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        tabbedPane = new JTabbedPane();
        normalPanel.add(tabbedPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 1, false));
        codePanel = new JPanel();
        codePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(codePanel, new GridConstraints(1, 1, 1, 7, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(500, 400), null, null, 0, false));
        codePanel.setBorder(BorderFactory.createTitledBorder(null, "DECOMPILE CODE", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        logPanel = new JPanel();
        logPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(logPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(200, -1), null, null, 0, false));
        logPanel.setBorder(BorderFactory.createTitledBorder(null, "LOG", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        logScroll = new JScrollPane();
        logPanel.add(logScroll, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        logArea = new JTextArea();
        logArea.setBackground(new Color(-14211289));
        logArea.setEditable(false);
        Font logAreaFont = this.$$$getFont$$$("Consolas", -1, 12, logArea.getFont());
        if (logAreaFont != null) logArea.setFont(logAreaFont);
        logArea.setForeground(new Color(-16711895));
        logScroll.setViewportView(logArea);
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(infoPanel, new GridConstraints(0, 6, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        infoPanel.setBorder(BorderFactory.createTitledBorder(null, "INFO", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        urlLabel = new JLabel();
        urlLabel.setText("SOURCE NAME");
        infoPanel.add(urlLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scNameText = new JTextField();
        scNameText.setEditable(false);
        infoPanel.add(scNameText, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, -1), null, 0, false));
        scLabel = new JLabel();
        scLabel.setText("SOURCE CLASS");
        infoPanel.add(scLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scText = new JTextField();
        scText.setEditable(false);
        infoPanel.add(scText, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        urlScroll = new JScrollPane();
        infoPanel.add(urlScroll, new GridConstraints(2, 0, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        urlList = new JList();
        urlScroll.setViewportView(urlList);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return shellPanel;
    }


}
