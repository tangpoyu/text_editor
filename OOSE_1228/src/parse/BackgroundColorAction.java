package parse;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BackgroundColorAction extends StyledEditorKit.StyledTextAction {
    private Color bg;

    public BackgroundColorAction(String nm, Color bg) {
        super(nm);
        this.bg = bg;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        JEditorPane editor = getEditor(ae);
        System.out.println(editor);
        if (editor == null) return;
        // 設定attribute
        SimpleAttributeSet attr = new SimpleAttributeSet();
        if(this.bg != null) attr.addAttribute(HTML.Attribute.BGCOLOR, getHTMLColor(bg));
        // 設定tag
        MutableAttributeSet outerAttr = new SimpleAttributeSet();
        outerAttr.addAttribute(HTML.Tag.FONT, attr);
        // 傳送變換背景色的指令給editor
        setCharacterAttributes(editor, outerAttr, false);
    }

    public String getHTMLColor(Color color) {
        if (color == null) return "";
        return "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
    }
}