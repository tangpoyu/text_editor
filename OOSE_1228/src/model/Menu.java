package model;

import javax.swing.*;

abstract public class Menu extends JMenu {
    abstract public void setDescription(String description);
    abstract public void addMenuItem(MenuItem m);
}
