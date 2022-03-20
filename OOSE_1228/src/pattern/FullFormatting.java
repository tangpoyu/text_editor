package pattern;

import model.Character;
import model.Glyph;
import model.Image;
import parse.parseArgs;

public class FullFormatting implements Formatting{
    private final String stylesheet = "style=\"font-family:'微軟正黑體' !important;\"";
    private final String TYPE = "Full";

    @Override
    public parseArgs parse(Glyph glyph) {
        parseArgs parseArgs = new parseArgs();
        String startringTag = "";
        String closingTag = "";
        if(glyph instanceof Character) {
            startringTag = glyph.getContent();
            parseArgs.setFullTag(startringTag);
        } else {
            startringTag = "<" + glyph.getTagname() + " " + glyph.getAttribute().replace("\\\"", "\"") + " " + stylesheet + " >";

            closingTag = "</" + glyph.getTagname() + ">";
            if(glyph instanceof Image) {
                startringTag = startringTag.replaceAll(" width=\\\\?[\\\"'].{1,3}\\\\?['\\\"] ", " width=\"600\" ");
                startringTag = startringTag.replaceAll(" src=\\\\?[\\\"']#*", " src=\"");
            }
            if(glyph.isSingleTag()) closingTag = "";
            parseArgs.setStartingTag(startringTag);
            parseArgs.setClosingTag(closingTag);
        }
        return parseArgs;
    }

    @Override
    public String getTYPE() {
        return TYPE;
    }
}
