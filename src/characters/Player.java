package characters;

import tools.Item;

import java.util.ArrayList;

public class Player {
    protected String name;
    protected int health;
    protected int armor;
    protected int stamina;
    protected double strength;
    protected Item[] items;

    public static final ArrayList<Player> preMadePlayers = new ArrayList<>();

    static {
        preMadePlayers.add(new Player("Trump", 100, 150, 100, 0.9, new Item[0]));
        preMadePlayers.add(new Player("Bush", 100, 100, 150, 0.9, new Item[0]));
        preMadePlayers.add(new Player("Jamal", 100, 100, 175, 0.8, new Item[0]));
        preMadePlayers.add(new Player("Barack", 100, 120, 125, 0.9, new Item[0]));
        preMadePlayers.add(new Player("Kamala", 75, 120, 100, 0.7, new Item[0]));
        preMadePlayers.add(new Player("Joe", 50, 95, 50, 0.5, new Item[0]));
    }

    public Player(String name, int health, int armor, int stamina, double strength, Item[] items) {
        this.name = name;
        this.health = health;
        this.armor = armor;
        this.stamina = stamina;
        this.strength = strength;
        this.items = new Item[0];
    }

    public void setName(String name) {
        this.name = name;
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
    public void setStrength(double strength) {
        this.strength = strength;
    }
    public String getName() {
        return name;
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

    public boolean isAlive() {
        return health > 0;
    }

    public boolean hasItems() {
        return items.length > 0;
    }

    public void takeDamage(int damage) {
        double effectiveDamage = damage * strength;
        health -= (int) effectiveDamage;
        System.out.println(name + " takes " + effectiveDamage + " damage! Health is now " + health);
    }

    public void attack(Player target) {
        int damage = (int) (strength * 10); // Replace 10 for tool damage
        System.out.println(name + " attacks " + target.getName() + " for " + damage + " damage!");
        target.takeDamage(damage);
    }
}
