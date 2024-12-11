package tools;

import characters.Player;

public class Defense extends Item {
    private final double blockPercentage;

    public Defense(String name, Rarity rarity, int blockPercentage) {
        super(name, "Defense", String.valueOf(rarity));
        this.blockPercentage = blockPercentage;
    }

    public double getBlockPercentage() {
        return blockPercentage;
    }

    public int reduceDamage(int incomingDamage) {
        double reducedDamage = incomingDamage - (incomingDamage * blockPercentage / 100);
        return (int) Math.max(reducedDamage, 0);
    }

    @Override
    public void use(Player player) {
        //TODO: Create some specific items, like you did for the potions, and if the player has them, apply the buff.
        //TODO: Use this method solely as a add item to inventory or something
        if (player.getTag() == 0) {
            System.out.println("This item passively reduces incoming damage by " + blockPercentage + "%.");
        }
    }
}
