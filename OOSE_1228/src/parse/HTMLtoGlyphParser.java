package parse;

import model.GlyphFactory;
import model.*;
import model.Character;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.util.ArrayList;
import java.util.List;

public class HTMLtoGlyphParser implements Parser<Glyph> {
    @Override
    public Glyph parse(String html) {
        Document document = Jsoup.parse(html.replaceAll("\\s{2,}"," "));
        Element body = document.body();
        body.insertChildren(body.childNodeSize()-2, new Element(Tag.valueOf("p"), ""));
        return getParse(body);
    }

    public Glyph getParse(Element elem){
        //若元素為空，回傳空Glyph
        if(elem == null) return null;

        //取得Glyph Factory並產生Glyph
        GlyphFactory myGlyphFactory = new GlyphFactory();
        Glyph glyphSpanElement = isDecorator(elem.tagName())? getDecorateeGlyphs(elem): null;
        Glyph glyphElement = myGlyphFactory.getGlyphInstance(elem.tagName(), glyphSpanElement);

        //設定Glyph屬性
        try {
            glyphElement.setAttribute(elem.attributes().toString().replace("\"", "\\\"").replaceAll(" style=\\\\?[\\\"'].*\\\\?['\\\"]", ""));
            glyphElement.setContent(elem.ownText());
        } catch(Exception ex) {}

        //插入子元素
        if(!(glyphElement instanceof Character || glyphElement instanceof Decorator)) {
            ArrayList<Glyph> childGlyphs = getChildListParse(elem.childNodes());
            for (Glyph glyph : childGlyphs) {
                try { glyphElement.insert(glyph); }
                catch (Exception ex) {}
            }
        }

        //回傳Glyph
        return glyphElement;
    }

    public ArrayList<Glyph> getChildListParse(List<Node> elems){
        //遍歷每個Child並產生Glyph存入清單
        ArrayList<Glyph> glyphList = new ArrayList<>();

        for (Node node : elems) {
            //處理文字節點
            if(node instanceof TextNode){
                //將字串依字元建為Jsoup Element Node
                for (String s: preProcessedText(((TextNode) node).toString()).split("")) {
                    if(!((TextNode) node).isBlank()) {
                        Element textElement = new Element(Tag.valueOf("character"), "");
                        if(s.equals(" "))  s = "&nbsp;";    //空白字元處理
                        textElement.text(s);
                        Glyph g = getParse(textElement);
                        glyphList.add(g);
                    }
                }
            }
            //處理其他節點
            else {
                Glyph g = getParse((Element) node);
                if (g != null) glyphList.add(g);
            }
        }
        return glyphList;
    }

    public Glyph getDecorateeGlyphs(Element elem){
        //產生span Glyph
        Glyph glyphSpanElement = getParse(new Element(Tag.valueOf("span"), ""));
        //把此elem的所有子元素的Glyph放入span形成一個Glyph集合
        ArrayList<Glyph> childGlyphs = getChildListParse(elem.childNodes());
        for (Glyph glyph : childGlyphs) {
            try { glyphSpanElement.insert(glyph); }
            catch (Exception ex) {}
        }

        return glyphSpanElement;
    }

    public String preProcessedText(String input){
        //文字節點預處理
        return input.replaceAll(" ", "").replaceAll("(&nbsp;)|(&#160;)|(&amp;nbsp;)", " ");
    }

    public boolean isDecorator(String tagName){
        List<String> normalFactory = List.of("span", "p", "img", "body", "character");
        List<String> decoratorFactory = List.of("b", "i", "u", "font");
        return decoratorFactory.contains(tagName);
    }
}