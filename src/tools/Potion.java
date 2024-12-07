package tools;

import characters.Character;

public class Potion extends Item {
    private String effect;
    private int effectValue;

    public Potion(String name, Rarity rarity, String effect, int effectValue) {
        super(name, "potion", rarity);
        this.effect = effect;
        this.effectValue = effectValue;
    }

    public String getEffect() {
        return effect;
    }

    @Override
    public void useItem() {

        System.out.println("This is a " + name + " potion. It restores " + effectValue + " " + effect + ".");
    }


    public void applyToCharacter(Character character) {
        if (effect.equalsIgnoreCase("health")) {
            character.setHealth(character.getHealth() + effectValue);
            System.out.println("You drink a " + name + " potion and restore " + effectValue + " health.");
        } else if (effect.equalsIgnoreCase("stamina")) {
            character.setStamina(character.getStamina() + effectValue);
            System.out.println("You drink a " + name + " potion and restore " + effectValue + " stamina.");
        } else {
            System.out.println("Unknown effect: " + effect);
        }
    }
}
