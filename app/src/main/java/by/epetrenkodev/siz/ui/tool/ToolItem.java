package by.epetrenkodev.siz.ui.tool;

public class ToolItem {
    private String name;
    private int cardCount;
    private int realCount;

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

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public int getRealCount() {
        return realCount;
    }

    public void setRealCount(int realCount) {
        this.realCount = realCount;
    }

    public int getTotal() {
        return realCount - cardCount;
    }
}
