package parse;

public class parseArgs {
    private String startingTag = "";
    private String closingTag = "";

    public void setStartingTag(String startingTag) {
        this.startingTag = startingTag;
    }
    public void setClosingTag(String closingTag) {
        this.closingTag = closingTag;
    }
    public void setFullTag(String fullTag) { this.startingTag = fullTag; closingTag = ""; }
    public String getStartingTag(){
        return startingTag;
    }
    public String getClosingTag(){
        return closingTag;
    }
    public String getFullTag() { return startingTag + closingTag; }
}
