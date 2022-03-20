package pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Character;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GlyphToOOSEFILEVisitor implements Visitor{
    private Stack<Map<String, Object>> parseStack = new Stack<>();
    private ArrayList<Map<String, Object>> childList;
    @Override
    public void visit(Root root) {pushCompositeToStack(root);}
    @Override
    public void visit(Paragraph paragraph) {pushCompositeToStack(paragraph);}
    @Override
    public void visit(Span span) {pushCompositeToStack(span);}
    @Override
    public void visit(Image image) {pushLeafToStack(image);}
    @Override
    public void visit(Character character) {pushLeafToStack(character);}
    @Override
    public void visit(Bold bold) {pushDecorateToStack(bold);}
    @Override
    public void visit(Italic italic) {pushDecorateToStack(italic);}
    @Override
    public void visit(Underline underline) {pushDecorateToStack(underline);}
    @Override
    public void visit(Font font) {pushDecorateToStack(font);}
    @Override
    public void visit(Glyph glyph) {pushCompositeToStack(glyph);}

    public Map<String, Object> generateGlyphElem(Glyph g) {
        //先將子元素依序組合起來 （兄節點會在弟節點之前被push進去，故弟節點會先poped出來，所以要 pop() + childParseStrings）
        childList = new ArrayList<>();
        for(int i = 0; i < g.getChildSize(); i++){
            childList.add(0, parseStack.pop());
        }
        Map<String, Object> elem = new HashMap<>();
        elem.put("tag", g.getTagname());
        elem.put("attribute", g.getAttribute().replace("\\\"", "\""));
        elem.put("content", g.getContent());
        return elem;
    }

    public void pushDecorateToStack(Glyph g){
        //若目前Glyph有子元素，且子元素數目 >= 目前堆疊內項目數
        if(g.getChildSize()>0 && parseStack.size() >= g.getChildSize()){
            Map<String, Object> elem = generateGlyphElem(g);
            elem.put("decoratee", childList.get(0));
            elem.put("child", new ArrayList<>());
            //把目前Glyph給push回去堆疊裡
            parseStack.push(elem);
        } else {
            pushLeafToStack(g);
        }
    }

    public void pushCompositeToStack(Glyph g){
        //若目前Glyph有子元素，且子元素數目 >= 目前堆疊內項目數
        if(g.getChildSize()>0 && parseStack.size() >= g.getChildSize()){
            Map<String, Object> elem = generateGlyphElem(g);
            elem.put("decoratee", null);
            elem.put("child", childList);
            //把目前Glyph給push回去堆疊裡
            parseStack.push(elem);
        } else {
            pushLeafToStack(g);
        }
    }

    public void pushLeafToStack(Glyph g){
        Map<String, Object> elem = new HashMap<>();
        elem.put("tag", g.getTagname());
        elem.put("attribute", g.getAttribute().replace("\\\"", "\""));
        elem.put("content", g.getContent());
        elem.put("decoratee", null);
        elem.put("child", new ArrayList<>());
        parseStack.push(elem);
    }

    public String getParseString(){
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(parseStack.peek());
    }
}
