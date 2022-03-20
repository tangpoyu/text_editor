package pattern;

import model.Character;
import model.Glyph;
import model.Image;
import parse.parseArgs;

public class PlaintextFormatting implements Formatting{
    private final String stylesheet = "style=\"background-color: #FFFFFF !important; color: #000000 !important; font-weight: normal !important; margin: 0; font-style: normal !important; text-decoration:none !important; font-family:'Times New Roman', '新細明體' !important;\"";
    private final String TYPE = "Plaintext";
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
                startringTag = startringTag.replaceAll(" width=\\\\?[\\\"'].{1,3}\\\\?['\\\"] ", " width=\"100\" ");
                startringTag = startringTag.replaceAll(" src=\\\\?[\\\"']#*", " src=\"##");
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
