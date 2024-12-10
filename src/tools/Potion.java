package tools;

import characters.Player;

public class Potion extends Item {
    private final String effect;
    private final int effectValue;

    public Potion(String name, Rarity rarity, String effect, int effectValue) {
        super(name, "Potion", String.valueOf(rarity));
        this.effect = effect;
        this.effectValue = effectValue;
    }

    public String getEffect() {
        return effect;
    }

    @Override
    public void use(Player player) {
        if (effect.equalsIgnoreCase("Health")) {
            player.setHealth(player.getHealth() + effectValue);
            if (player.getTag() == 0) {
                System.out.println("You drank a " + name + " potion and restore " + effectValue + " health.");
            }
        } else if (effect.equalsIgnoreCase("Stamina")) {
            player.setStamina(player.getStamina() + effectValue);
            if (player.getTag() == 0) {
                System.out.println("You drank a " + name + " potion and restore " + effectValue + " stamina.");
            }
        }
    }
}
