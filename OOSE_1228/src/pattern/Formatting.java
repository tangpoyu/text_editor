package pattern;

import model.Glyph;
import parse.parseArgs;

public interface Formatting {
    parseArgs parse(Glyph glyph);   //解析Glyph
    String getTYPE();  //取得目前Formatting的類型
}
