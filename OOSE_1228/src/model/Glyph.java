package model;

import pattern.Iterator;
import pattern.Visitor;

public abstract class Glyph{
    //----------Composite Object才能用----------
    public void insert(Glyph g) { throw new UnsupportedOperationException(); }
    public void remove(int i) { throw new UnsupportedOperationException(); }
    public void accept(Visitor visitor) { throw new UnsupportedOperationException(); }
    public Glyph getChild(int i) { throw new UnsupportedOperationException(); }
    public int getChildSize() { throw new UnsupportedOperationException(); }
    public Iterator getIterator()  { throw new UnsupportedOperationException(); }

    //----------Leaf Object 和 Composite Object都能用----------
    public void setAttribute(String attribute) { throw new UnsupportedOperationException(); }
    public void setContent(String content) { throw new UnsupportedOperationException(); }
    public String getTagname() { throw new UnsupportedOperationException(); }
    public String getAttribute() { throw new UnsupportedOperationException(); }
    public String getContent() { throw new UnsupportedOperationException(); }
    public boolean isSingleTag() { throw new UnsupportedOperationException(); }

}
