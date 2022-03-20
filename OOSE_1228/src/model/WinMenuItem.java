package model;

import java.awt.*;

public class WinMenuItem extends MenuItem{
    private final Color FOREGROUND = Color.BLACK;
    private final Color BACKGROUND = new Color(246, 246, 246, 255);

    public WinMenuItem(){
        this.setOpaque(true);
        this.setBackground(BACKGROUND);
        this.setForeground(FOREGROUND);
    }

    public void setDescription(String description) {
        this.setText(description);
    }
}
