package pattern;

import model.WidgetFactory;
import model.WidgetFactoryProducer;

import javax.swing.*;
import java.awt.*;

public class WinWindowImp implements WindowImp{
    private final String operating = "Win";
    private Font systemFont = new Font("微軟正黑體", Font.PLAIN, 12);

    @Override
    public String getEnvironment() {
        return operating;
    }

    @Override
    public JFrame drawFrame() {
        // 設定JFrame
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch(Exception ex) {}

        setUIFont();

        return new JFrame();
    }

    @Override
    public void setSystemFont(Font systemFont){
        this.systemFont = systemFont;
        setUIFont();
    }

    @Override
    public Font getSystemFont(){
        return this.systemFont;
    }

    @Override
    public WidgetFactory getWidgetFactory() {
        return WidgetFactoryProducer.getFactory(operating);
    }

    public void setUIFont() {
        UIManager.put("CheckBoxMenuItem.acceleratorFont", systemFont);
        UIManager.put("Button.font", systemFont);
        UIManager.put("ToggleButton.font", systemFont);
        UIManager.put("RadioButton.font", systemFont);
        UIManager.put("CheckBox.font", systemFont);
        UIManager.put("ColorChooser.font", systemFont);
        UIManager.put("ComboBox.font", systemFont);
        UIManager.put("Label.font", systemFont);
        UIManager.put("List.font", systemFont);
        UIManager.put("MenuBar.font", systemFont);
        UIManager.put("Menu.acceleratorFont", systemFont);
        UIManager.put("RadioButtonMenuItem.acceleratorFont", systemFont);
        UIManager.put("MenuItem.acceleratorFont", systemFont);
        UIManager.put("MenuItem.font", systemFont);
        UIManager.put("RadioButtonMenuItem.font", systemFont);
        UIManager.put("CheckBoxMenuItem.font", systemFont);
        UIManager.put("OptionPane.buttonFont", systemFont);
        UIManager.put("OptionPane.messageFont", systemFont);
        UIManager.put("Menu.font", systemFont);
        UIManager.put("PopupMenu.font", systemFont);
        UIManager.put("OptionPane.font", systemFont);
        UIManager.put("Panel.font", systemFont);
        UIManager.put("ProgressBar.font", systemFont);
        UIManager.put("ScrollPane.font", systemFont);
        UIManager.put("Viewport.font", systemFont);
        UIManager.put("TabbedPane.font", systemFont);
        UIManager.put("Slider.font", systemFont);
        UIManager.put("Table.font", systemFont);
        UIManager.put("TableHeader.font", systemFont);
        UIManager.put("TextField.font", systemFont);
        UIManager.put("Spinner.font", systemFont);
        UIManager.put("PasswordField.font", systemFont);
        UIManager.put("TextArea.font", systemFont);
        UIManager.put("TextPane.font", systemFont);
        UIManager.put("EditorPane.font", systemFont);
        UIManager.put("TabbedPane.smallFont", systemFont);
        UIManager.put("TitledBorder.font", systemFont);
        UIManager.put("ToolBar.font", systemFont);
        UIManager.put("ToolTip.font", systemFont);
        UIManager.put("Tree.font", systemFont);
        UIManager.put("FormattedTextField.font", systemFont);
        UIManager.put("IconButton.font", systemFont);
        UIManager.put("InternalFrame.optionDialogTitleFont", systemFont);
        UIManager.put("InternalFrame.paletteTitleFont", systemFont);
        UIManager.put("InternalFrame.titleFont", systemFont);
    }
}
