package characters;

import tools.Item;

import java.util.ArrayList;
import java.util.List;

public class Player {
    protected String name;
    protected int health;
    protected int armor;
    protected int stamina;
    protected double strength;
    protected List<Item> items;

    public static final List<Player> preMadePlayers = new ArrayList<>();

    static {
        createPreMadePlayers();
    }

    private static void createPreMadePlayers() {
        preMadePlayers.add(new Player("Trump", 100, 150, 100, 0.9));
        preMadePlayers.add(new Player("Bush", 100, 100, 150, 0.9));
        preMadePlayers.add(new Player("Jamal", 100, 100, 175, 0.8));
        preMadePlayers.add(new Player("Barack", 100, 120, 125, 0.9));
        preMadePlayers.add(new Player("Kamala", 75, 120, 100, 0.7));
        preMadePlayers.add(new Player("Joe", 50, 95, 50, 0.5));
    }

    public Player(String name, int health, int armor, int stamina, double strength, List<Item> items) {
        this.name = name;
        this.health = health;
        this.armor = armor;
        this.stamina = stamina;
        this.strength = strength;

        //Items if null are new arraylist, if not arraylist with items
        this.items = items == null ? new ArrayList<>() : new ArrayList<>(items);
    }


    public Player(String name, int health, int armor, int stamina, double strength) {
        this(name, health, armor, stamina, strength, new ArrayList<>());
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
    public List<Item> getItems(){
        return items;
    }

    public double getStrength() {
        return strength;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean hasItems() {
        return !items.isEmpty();
    }

    public void removeItem(int i){
        items.remove(i);
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
