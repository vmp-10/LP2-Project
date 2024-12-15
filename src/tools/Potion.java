package tools;

import characters.Player;
import core.PlayerManager;

public class Potion extends Item {
    //Potions will restore a percentage of the Player's HP

    private final String effect;
    private final double effectValue;

    public Potion(String name, Rarity rarity, String effect, double effectValue) {
        super(name, "Potion", String.valueOf(rarity));
        this.effect = effect;
        this.effectValue = effectValue;
    }

    public String getEffect() {
        return effect;
    }

    @Override
    public void use(Player player, PlayerManager playerManager) {
        if (effect.equalsIgnoreCase("Health")) {
            int restore = (int) (player.getMaxHealth() * effectValue);

            player.setHealth(player.getHealth() + restore);
            if (playerManager.isHuman(player)) {
                System.out.println("You drank a " + name + " potion and restored " + restore + " health.");
            }
        } else if (effect.equalsIgnoreCase("Stamina")) {
            int restore = (int) (player.getMaxStamina() * effectValue);

            player.setStamina(player.getStamina() + restore);
            if (playerManager.isHuman(player)) {
                System.out.println("You drank a " + name + " potion and restored " + restore + " stamina.");
            }
        }
        player.removeItem(this);
    }
}
