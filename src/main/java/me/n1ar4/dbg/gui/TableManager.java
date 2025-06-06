/*
 * GPLv3 License
 *
 * Copyright (c) 2023-2025 4ra1n (Jar Analyzer Team)
 *
 * This project is distributed under the GPLv3 license.
 *
 * https://github.com/jar-analyzer/jar-analyzer/blob/master/LICENSE
 */

package me.n1ar4.dbg.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class TableManager {
    private static final Set<Integer> highlightedRows = new HashSet<>();
    private static long curCodeIndex = 0;
    private static long jumpLocation = -1;
    private static int jumpRow = -1;

    public static void addHighlight(int row) {
        highlightedRows.add(row);
    }

    public static void addJump(long row) {
        jumpLocation = row;
    }

    public static void addJumpRow(int row) {
        jumpRow = row;
    }

    public static long getJumpLocation() {
        return jumpLocation;
    }

    public static void reset() {
        jumpLocation = -1;
        jumpRow = -1;
    }

    public static void setCur(long index) {
        highlightedRows.clear();
        curCodeIndex = index;
    }

    public static void setBytecodeTable() {
        MainForm instance = MainForm.getInstance();
        JTable bytecodeTable = instance.getBytecodeTable();

        // IGNORE BOUNDARY
        bytecodeTable.setShowGrid(false);
        bytecodeTable.setGridColor(new Color(0, 0, 0, 0));
        bytecodeTable.setIntercellSpacing(new Dimension(0, 0));

        // RENDER ACTION
        Color defaultFontColor = UIManager.getColor("Table.foreground");
        Color lighterRed = new Color(255, 100, 100);
        Color lighterYellow = new Color(225, 202, 130);

        bytecodeTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                // DO NOT MODIFY FONT COLOR
                c.setForeground(defaultFontColor);

                boolean highlightRow = false;
                String actual = String.format("%08x", curCodeIndex);
                for (int col = 0; col < table.getColumnCount(); col++) {
                    Object cellValue = table.getValueAt(row, col);
                    if (cellValue != null && cellValue.toString().equals(actual)) {
                        highlightRow = true;
                        break;
                    }
                }

                if (highlightedRows.contains(row)) {
                    c.setBackground(lighterRed);
                } else if (highlightRow) {
                    c.setBackground(Color.CYAN);
                } else if (jumpLocation != -1 && row == jumpRow) {
                    c.setBackground(lighterYellow);
                } else {
                    c.setBackground(Color.WHITE);
                }

                return c;
            }
        });

        // MOUSE ACTION LISTENER
        bytecodeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = bytecodeTable.rowAtPoint(e.getPoint());
                int col = bytecodeTable.columnAtPoint(e.getPoint());
                if (col == 0) {
                    if (highlightedRows.contains(row)) {
                        highlightedRows.remove(row);
                    } else {
                        highlightedRows.add(row);
                    }
                    bytecodeTable.repaint();
                }
            }
        });

        // IGNORE HEADER RENDER
        bytecodeTable.setTableHeader(null);
    }
}
