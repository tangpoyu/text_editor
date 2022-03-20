package parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Character;
import model.Decorator;
import model.Glyph;
import model.GlyphFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GlyphParser implements Parser<Glyph> {

    @Override
    public Glyph parse(String oosefile_string) {
        Gson gson = new Gson();
        Map<String, Object> document = gson.fromJson(oosefile_string, new TypeToken<Map<String, Object>>(){}.getType()); //Type type = ;
        return getParse((Map<String, Object>)document.get("content"));
    }

    public Glyph getParse(Map<String, Object> entry){
        //若元素為空，回傳空Glyph
        if(entry == null) return null;

        //取得
        String tag = (String) entry.get("tag");
        String attribute = (String) entry.get("attribute");
        String content = (String) entry.get("content");
        ArrayList<Map<String, Object>> childs = (ArrayList<Map<String, Object>>)entry.get("child");

        //取得Glyph Factory並產生Glyph
        GlyphFactory myGlyphFactory = new GlyphFactory();
        Glyph glyphDecorateeElement = isDecorator(tag)? getParse((Map<String, Object>) entry.get("decoratee")): null;
        Glyph glyphElement = myGlyphFactory.getGlyphInstance(tag, glyphDecorateeElement);

        //設定Glyph屬性
        try {
            glyphElement.setAttribute(attribute.replace("\"", "\\\"").replaceAll(" style=\\\\?[\\\"'].*\\\\?['\\\"]", ""));
            glyphElement.setContent(content);
        } catch(Exception ex) {}

        //插入子元素
        if(!(glyphElement instanceof Character || glyphElement instanceof Decorator)) {
            for (Map<String, Object> child : childs) {
                Glyph childGlyph = getParse(child);
                try { if(childGlyph!=null) glyphElement.insert(childGlyph); }
                catch (Exception ex) {}
            }
        }

        //回傳Glyph
        return glyphElement;
    }

    public boolean isDecorator(String tagName){
        List<String> normalFactory = List.of("span", "p", "img", "body", "character");
        List<String> decoratorFactory = List.of("b", "i", "u", "font");
        return decoratorFactory.contains(tagName);
    }
}
