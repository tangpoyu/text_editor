package model;

public interface WidgetFactory {
    ScrollBar createScrollbar();
    MenuBar createMenuBar();
    Menu createMenu();
    MenuItem createMenuItem();
}
