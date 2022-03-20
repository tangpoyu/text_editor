package model;

import pattern.Visitor;

public class Font extends Decorator{
    private String tagname = "font";
    private String attribute = "";
    public Font(Glyph g){
        super.glyph = g;
    }

    @Override
    public int getChildSize() {
        return 1;
    }

    @Override
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getAttribute() {
        return attribute;
    }
    @Override
    public String getTagname() { return tagname; }
    @Override
    public String getContent() { return ""; }

    @Override
    public boolean isSingleTag() { return false; }
    @Override
    public void accept(Visitor visitor) {
        super.accept(visitor);
        visitor.visit(this);
    }
}
