package tools;

public class Defense extends Item {
    private int blockPercentage;

    public Defense(String name, Rarity rarity, int blockPercentage) {
        super(name, "defense", rarity);
        this.blockPercentage = blockPercentage;
    }

    public int getBlockPercentage() {
        return blockPercentage;
    }

    public int reduceDamage(int incomingDamage) {
        int reducedDamage = incomingDamage - (incomingDamage * blockPercentage / 100);
        return Math.max(reducedDamage, 0);
    }

    @Override
    public void useItem() {

        System.out.println("This item passively reduces incoming damage by " + blockPercentage + "%.");
    }
}
