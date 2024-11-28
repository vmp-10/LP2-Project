package characters;

public class Character {
    private int health, armor, stamina;
    private double strength;

    /*
        characters.MC -> 6 diff characters:
            Trump       -> HP 100, ARMOR 150, STAMINA 100, STRENGTH 0.9                 (Easy Mode)
            Bush        -> HP 100, ARMOR 100, STAMINA 150, STRENGTH 0.9                 (Easy Mode)
            Jamal       -> HP 100, ARMOR 100, STAMINA 175, STRENGTH 0.8                 (Normal Mode)
            Barack      -> HP 100, ARMOR 120, STAMINA 125, STRENGTH 0.9                 (Normal Mode)
            Kamala      -> HP 75, ARMOR 120, STAMINA 100, STRENGTH 0.7                  (Hard Mode)
            Joe         -> HP 50, ARMOR 95, STAMINA 50, STRENGTH 0.5                    (Joe Must Die Mode)
            Custom      -> HP 50-100, ARMOR 80-150, STAMINA 50-200, STRENGTH 0.5-1.5    (Custom Mode)
        characters.NPC -> HP 100, ARMOR 100, STAMINA 100, STRENGTH 1
    */

    public Character(int health, int armor, int stamina, int strength) {
        this.health = health;
        this.armor = armor;
        this.stamina = stamina;
        this.strength = strength;
    }

    public Character(String name) {
        switch (name) {
            case "Trump": {
                this.health = 100;
                this.armor = 150;
                this.stamina = 100;
                this.strength = 0.9;
            }
            break;
            case "Bush": {
                this.health = 100;
                this.armor = 100;
                this.stamina = 150;
                this.strength = 0.9;
            }
            break;
            case "Jamal": {
                this.health = 100;
                this.armor = 100;
                this.stamina = 175;
                this.strength = 0.8;
            }
            break;
            case "Barack": {
                this.health = 100;
                this.armor = 120;
                this.stamina = 125;
                this.strength = 0.9;
            }
            break;
            case "Kamala": {
                this.health = 75;
                this.armor = 120;
                this.stamina = 100;
                this.strength = 0.7;
            }
            break;
            case "Joe": {
                this.health = 50;
                this.armor = 95;
                this.stamina = 50;
                this.strength = 0.5;
            }
            break;
        }
    }


    public int getHealth() {
        return health;
    }

    public int getArmor() {
        return armor;
    }

    public int getStamina() {
        return stamina;
    }

    public double getStrength() {
        return strength;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
