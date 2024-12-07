package tools;

import characters.Character;

public class Weapon extends Item {
    private int baseDamage;

    public Weapon(String name, Rarity rarity, int baseDamage) {
        super(name, "weapon", rarity);
        this.baseDamage = baseDamage;
    }

    public int getDamage(Character character) {
        return (int) (baseDamage * character.getStrength());
    }

    @Override
    public void useItem() {
        System.out.println("You use " + name + ", but no specific character was provided.");
    }
    public void useItem(Character character) {
        System.out.println("You use " + name + ", dealing " + getDamage(character) + " damage.");
    }
}
