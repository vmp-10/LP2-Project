package tools;

import characters.Player;

public class Weapon extends Item {
    private final int baseDamage;

    public Weapon(String name, Rarity rarity, int baseDamage) {
        super(name, "Weapon", String.valueOf(rarity));
        this.baseDamage = baseDamage;
    }

    public int getDamage(Player character) {
        return (int) (baseDamage * character.getStrength());
    }

    @Override
    public void use(Player player) {
        System.out.println("You use " + name + ", dealing " + getDamage(player) + " damage.");
    }
}
