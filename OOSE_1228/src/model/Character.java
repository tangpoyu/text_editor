package model;

import pattern.Visitor;

public class Character extends Glyph{
    private String tagname = "character";
    private String attribute = "", content = "";
    public Character(){}

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getContent() { return content; }

    public void accept(Visitor visitor) { visitor.visit(this); }

    @Override
    public int getChildSize() {
        return 0;
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
    public String getTagname() {
        return tagname;
    }

    @Override
    public boolean isSingleTag() { return true; }

}
