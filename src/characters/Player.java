package characters;

import tools.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    protected String name;
    protected int health;
    protected int armor;
    protected int stamina;
    protected double strength;
    protected List<Item> items;
    protected int tag;

    // HashMap for pre-made players categorized by difficulty
    public static final Map<String, List<Player>> preMadePlayers = new HashMap<>();

    static {
        createPreMadePlayers();
    }

    private static void createPreMadePlayers() {
        preMadePlayers.put("Easy", List.of(
                new Player("Trump", 100, 150, 100, 0.9),
                new Player("Bush", 100, 100, 150, 0.9)
        ));
        preMadePlayers.put("Normal", List.of(
                new Player("Jamal", 100, 100, 175, 0.8),
                new Player("Barack", 100, 120, 125, 0.9)
        ));
        preMadePlayers.put("Hard", List.of(
                new Player("Kamala", 75, 120, 100, 0.7),
                new Player("Lincoln", 75, 100, 80, 0.6)
        ));
        preMadePlayers.put("Joe Must Die", List.of(
                new Player("Joe", 50, 95, 50, 0.5)
        ));
    }

    public Player(String name, int health, int armor, int stamina, double strength, List<Item> items, int tag) {
        this.name = name;
        this.health = health;
        this.armor = armor;
        this.stamina = stamina;
        this.strength = strength;
        this.tag = tag;

        // Initialize items to a new list if null, otherwise copy the existing list
        this.items = (items == null) ? new ArrayList<>() : new ArrayList<>(items);
    }

    public Player(String name, int health, int armor, int stamina, double strength) {
        this(name, health, armor, stamina, strength, null, -1);
    }

    public Player(Player other) {
        this.name = other.name;
        this.health = other.health;
        this.armor = other.armor;
        this.stamina = other.stamina;
        this.strength = other.strength;
        this.tag = other.tag;
        this.items = new ArrayList<>(other.items);
    }

    public void setHealth(int health) {
        if (health > 150) {
            health = 150;
        } else if (health < 0) {
            health = 0;
        }
        this.health = health;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setStamina(int stamina) {
        if (stamina > 150) {
            stamina = 150;
        } else if (stamina < 0) {
            stamina = 0;
        }
        this.stamina = stamina;
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

    public List<Item> getItems() {
        return items;
    }

    public int getTag() {
        return tag;
    }


    public boolean isAlive() {
        return health > 0;
    }

    public boolean hasItems() {
        return !items.isEmpty();
    }

    public void removeItem(int i) {
        items.remove(i);
    }

    public void addItem(Item item){
        if (items.size() < 4) {
            items.add(item);
        }
    }

    public void takeDamage(int damage, boolean isHuman) {
        double effectiveDamage = damage * strength;
        health -= (int) effectiveDamage;
        if (isHuman) {
            System.out.println("Player " + tag + " takes " + effectiveDamage + " damage.");
        }
    }

    public void attack(Player target, boolean isHuman) {
        int damage = (int) (strength * 10); // Replace 10 for tool damage
        if (isHuman) {
            System.out.println(name + " attacks " + target.getName() + " for " + damage + " damage.");
        }
        target.takeDamage(damage, isHuman);
    }
}
