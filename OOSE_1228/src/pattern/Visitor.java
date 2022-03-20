package pattern;

import model.*;
import model.Character;

public interface Visitor {
    void visit(Root root);
    void visit(Paragraph paragraph);
    void visit(Span span);
    void visit(Image image);
    void visit(Character character);
    void visit(Bold bold);
    void visit(Italic italic);
    void visit(Underline underline);
    void visit(Font font);
    void visit(Glyph glyph);
}
