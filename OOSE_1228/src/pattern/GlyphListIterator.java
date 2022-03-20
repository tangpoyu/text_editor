package pattern;

import model.Glyph;

public class GlyphListIterator implements Iterator {
    private int position = 0;
    private Glyph glyphComposite;
    public GlyphListIterator(Glyph glyphComposite){
        this.glyphComposite = glyphComposite;
    }

    @Override
    public boolean hasNext() {
        if(position >= glyphComposite.getChildSize() || glyphComposite.getChild(position) == null)
            return false;
        return true;
    }

    @Override
    public Glyph next() {
        return glyphComposite.getChild(position++);
    }

    @Override
    public boolean hasPrevious() {
        if(position-1 >= glyphComposite.getChildSize() || glyphComposite.getChild(position-1) == null)
            return false;
        return true;
    }

    @Override
    public Glyph previous() { return glyphComposite.getChild(--position); }

    @Override
    public void remove() {
        glyphComposite.remove(position-1);
        position -= 1;
    }
}
