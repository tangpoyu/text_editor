package parse;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTML;
import java.awt.event.ActionEvent;

public class InsertImageAction {
    private String absPath;
    private String className;

    public InsertImageAction(String absPath,String className) {
        this.absPath = absPath;
        this.className = className;
    }

    public AttributeSet actionPerformed(ActionEvent ae) {
        // 設定attribute
        SimpleAttributeSet attr = new SimpleAttributeSet();
        attr.addAttribute(HTML.Attribute.SRC, "file:///"+absPath);
        attr.addAttribute(HTML.Attribute.CLASS, className);
        attr.addAttribute(HTML.Attribute.SIZE,"100");
        // 設定tag
        MutableAttributeSet outerAttr = new SimpleAttributeSet();
        outerAttr.addAttribute(HTML.Tag.IMG, attr);
        // 回傳
        return outerAttr;
    }
}