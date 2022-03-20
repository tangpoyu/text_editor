package pattern;

import model.Glyph;

public interface Iterator {
    boolean hasNext();
    Glyph next();
    boolean hasPrevious();
    Glyph previous();
    void remove();
}
