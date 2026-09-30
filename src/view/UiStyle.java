package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

final class UiStyle {
    static final Color NAVY = new Color(20, 48, 76);
    static final Color BLUE = new Color(28, 126, 190);
    static final Color PALE = new Color(239, 244, 248);
    static final Color INK = new Color(38, 52, 66);
    private UiStyle() {}

    static JPanel root() { JPanel p = new JPanel(new BorderLayout(0, 18)); p.setBackground(PALE); p.setBorder(new EmptyBorder(24, 30, 24, 30)); return p; }
    static JPanel header(String title, String subtitle) {
        JPanel p = new JPanel(new BorderLayout()); p.setBackground(NAVY); p.setBorder(new EmptyBorder(18, 24, 18, 24));
        JLabel t = new JLabel(title); t.setForeground(Color.WHITE); t.setFont(new Font("SansSerif", Font.BOLD, 24));
        JLabel s = new JLabel(subtitle); s.setForeground(new Color(205, 222, 235));
        JPanel labels = new JPanel(new GridLayout(2,1,0,5)); labels.setOpaque(false); labels.add(t); labels.add(s); p.add(labels); return p;
    }
    static JButton button(String label, boolean primary) { JButton b = new JButton(label); b.setFocusPainted(false); b.setFont(new Font("SansSerif", Font.BOLD, 13)); b.setBackground(primary ? BLUE : Color.WHITE); b.setForeground(primary ? Color.WHITE : NAVY); return b; }
    static void styleTable(JTable table) { table.setRowHeight(30); table.setFont(new Font("SansSerif", Font.PLAIN, 13)); table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13)); table.setSelectionBackground(new Color(207, 231, 247)); table.setSelectionForeground(INK); }
}
