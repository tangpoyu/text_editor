package pattern;

import javax.swing.*;

public class DialogWindow extends Window{
    private JFrame frame;
    public DialogWindow(WindowImp impl) {
        super(impl);
        frame = super.drawFrame();
    }

    @Override
    public boolean run() {
        return true;
    }

    public void showDialog(String message, String title){
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}