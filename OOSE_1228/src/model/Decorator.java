package model;

import pattern.Visitor;

public abstract class Decorator extends Glyph{
    protected Glyph glyph;  //Decoratee
    @Override
    public void insert(Glyph g) {
        glyph.insert(g);
    }
    @Override
    public void remove(int i) {
        glyph.remove(i);
    }
    @Override
    public void accept(Visitor visitor) { glyph.accept(visitor); }
    @Override

    public Glyph getChild(int i) {
        return glyph.getChild(i);
    }
    @Override
    public int getChildSize() { return glyph.getChildSize(); }

    @Override
    public void setAttribute(String attribute) { glyph.setAttribute(attribute); }
    @Override
    public void setContent(String content) { glyph.setContent(content); }

    @Override
    public String getAttribute() { return glyph.getAttribute(); }
    @Override
    public String getContent() { return glyph.getContent(); }
    @Override
    public String getTagname() { return glyph.getTagname(); }

    @Override
    public boolean isSingleTag() { return glyph.isSingleTag(); }
}
