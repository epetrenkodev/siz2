package by.epetrenkodev.siz.ui.tool;

public class ToolItem {
    private String name;
    private final int cardCount;
    private final int realCount;

    public ToolItem(String name, int cardCount, int realCount) {
        this.name = name;
        this.cardCount = cardCount;
        this.realCount = realCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCardCount() {
        return cardCount;
    }

    public int getRealCount() {
        return realCount;
    }

    public int getTotal() {
        return realCount - cardCount;
    }
}
