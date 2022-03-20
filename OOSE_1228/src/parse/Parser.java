package parse;

public interface Parser<Glyph> {
    Glyph parse(String s);
}