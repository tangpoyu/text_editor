package model;

import java.awt.*;

public class WinMenu extends Menu{
    private final Color FOREGROUND = Color.BLACK;
    private final Color BACKGROUND = new Color(246, 246, 246, 255);
    private final Color SELECTED_FOREGROUND = Color.WHITE;
    private final Color SELECTED_BACKGROUND = new Color(75, 110, 175, 255);

    public WinMenu(){
        this.setOpaque(true);
        this.setBackground(BACKGROUND);
        this.setForeground(FOREGROUND);

        this.addChangeListener(e -> {
            if (e.getSource() instanceof Menu) {
                Menu item = (Menu) e.getSource();
                if (item.isSelected() || item.isArmed()) {
                    item.setBackground(SELECTED_BACKGROUND);
                    item.setForeground(SELECTED_FOREGROUND);
                } else {
                    item.setBackground(BACKGROUND);
                    item.setForeground(FOREGROUND);
                }
            }
        });
    }

    @Override
    public void setDescription(String description) {
        super.setText(description);
    }

    @Override
    public void addMenuItem(MenuItem m) {
        this.add(m);
    }
}
