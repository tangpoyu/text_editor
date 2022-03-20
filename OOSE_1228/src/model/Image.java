package model;

import pattern.Visitor;

public class Image extends Glyph{
    private String tagname = "img";
    private String attribute = "";
    public Image(){}

    @Override
    public int getChildSize() {
        return 0;
    }
    @Override
    public void accept(Visitor visitor) { visitor.visit(this); }

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
    public boolean isSingleTag() { return true; }
}
