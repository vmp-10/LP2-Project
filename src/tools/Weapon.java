 package tools;

import characters.Player;
import core.PlayerManager;

public class Weapon extends Item {
    private final int baseDamage;

    public Weapon(String name, Rarity rarity, int baseDamage) {
        super(name, "Weapon", String.valueOf(rarity));
        this.baseDamage = baseDamage;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getDamage(Player character) {
        return (int) (baseDamage + (baseDamage * (1 - character.getStrength())));
    }

    public String getRarity(){
        return rarity;
    }

    //TODO: Offre please fix, idk wtf to do here.
    @Override
    public void use(Player player, PlayerManager playerManager) {
        if (playerManager.isHuman(player)) {
            System.out.println("You use " + name + ", dealing " + getDamage(player) + " damage.");
        }
    }
}
