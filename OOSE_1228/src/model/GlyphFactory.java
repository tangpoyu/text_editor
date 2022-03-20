package model;


public class GlyphFactory {
    public GlyphFactory(){}
    public Glyph getGlyphInstance(String type, Glyph decoratee){
        switch(type){
            case "b": return new Bold(decoratee);
            case "i": return new Italic(decoratee);
            case "u": return new Underline(decoratee);
            case "font": return new Font(decoratee);

            case "body": return new Root();
            case "p": return new Paragraph();
            case "span": return new Span();
            case "img": return new Image();
            default: return new Character();
        }
    }
}
