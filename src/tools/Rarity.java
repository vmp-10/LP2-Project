package tools;

public enum Rarity {
    LEGENDARY(5),    // 5% chance
    EPIC(20),        // 20% chance
    RARE(50),        // 50% chance
    COMMON(100);     // 100% chance

    private final int percentage;

    Rarity(int percentage) {
        this.percentage = percentage;
    }

    public int getPercentage() {
        return percentage;
    }
}
