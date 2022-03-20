package pattern;

import model.WidgetFactory;

import javax.swing.*;
import java.awt.*;

public abstract class Window{
    WindowImp impl;
    public Window(WindowImp impl){
        this.impl = impl;
    }
    public String getEnvironment(){ return this.impl.getEnvironment(); }
    public Font getSystemFont(){ return this.impl.getSystemFont(); }
    public JFrame drawFrame(){ return this.impl.drawFrame(); }
    public WidgetFactory getWidgetFactory(){ return this.impl.getWidgetFactory(); }
    abstract public boolean run();
}
