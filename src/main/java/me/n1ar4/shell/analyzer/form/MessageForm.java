/*
 * MIT License
 *
 * Copyright (c) 2023-2024 4ra1n (Jar Analyzer Team)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.n1ar4.shell.analyzer.form;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.n1ar4.agent.dto.SourceResult;
import me.n1ar4.shell.analyzer.model.InfoObj;

import javax.swing.*;
import java.awt.*;

public class MessageForm {
    private JPanel rootPanel;
    private JPanel msgPanel;
    private JTextField urlText;
    private JLabel urlLabel;
    private JLabel urlDescLabel;
    private JTextArea descArea;
    private JTextField hashText;
    private JScrollPane descScroll;
    private JLabel hashLabel;
    private JTextArea globalArea;
    private JLabel globalLabel;
    private JScrollPane globalScroll;

    private static final String urlInfoDescSplitTag = "\\^&\\*\\$#@";

    public MessageForm(InfoObj obj) {
        urlText.setText(obj.getUrl());
        hashText.setText(String.format("INT: %s HEX: %s", obj.getHash(),
                Integer.toHexString(Integer.parseInt(obj.getHash()))));
        String[] data = obj.getUrlDesc().split(urlInfoDescSplitTag);
        StringBuilder sb = new StringBuilder();
        for (String d : data) {
            if (d.startsWith(SourceResult.SourceResultTag)) {
                continue;
            }
            sb.append(d);
            sb.append("\n");
        }
        descArea.setText(sb.toString().trim());
        StringBuilder gb = new StringBuilder();
        for (String d : obj.getGlobalDesc()) {
            gb.append(d.trim());
            gb.append("\n");
        }
        globalArea.setText(gb.toString().trim());
    }

    public static void start0(InfoObj obj) {
        JFrame frame = new JFrame("message");
        frame.setContentPane(new MessageForm(obj).rootPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
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
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        msgPanel = new JPanel();
        msgPanel.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(msgPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        urlLabel = new JLabel();
        urlLabel.setText("URL");
        msgPanel.add(urlLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final Spacer spacer1 = new Spacer();
        msgPanel.add(spacer1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        urlText = new JTextField();
        urlText.setEditable(false);
        msgPanel.add(urlText, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(400, -1), null, null, 0, false));
        urlDescLabel = new JLabel();
        urlDescLabel.setText("DESC");
        msgPanel.add(urlDescLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        descScroll = new JScrollPane();
        msgPanel.add(descScroll, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        descArea = new JTextArea();
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descScroll.setViewportView(descArea);
        hashLabel = new JLabel();
        hashLabel.setText("HASH");
        msgPanel.add(hashLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        hashText = new JTextField();
        hashText.setEditable(false);
        msgPanel.add(hashText, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        globalLabel = new JLabel();
        globalLabel.setText("GLOBAL DESC");
        msgPanel.add(globalLabel, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        globalScroll = new JScrollPane();
        msgPanel.add(globalScroll, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        globalArea = new JTextArea();
        globalArea.setEditable(false);
        globalScroll.setViewportView(globalArea);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}