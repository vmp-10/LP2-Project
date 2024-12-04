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
    protected boolean isHuman; //boolean for setting the npcs as npc (ishuman=false)

    // Static counter for unique tags
    private static int tagCounter = 0;

    // HashMap for pre-made players categorized by difficulty
    public static final Map<String, List<Player>> preMadePlayers = new HashMap<>();

    static {
        createPreMadePlayers();
    }

    private static void createPreMadePlayers() {
        preMadePlayers.put("Easy", List.of(
                new Player("Trump", 100, 150, 100, 0.9,false),
                new Player("Bush", 100, 100, 150, 0.9,false)
        ));
        preMadePlayers.put("Normal", List.of(
                new Player("Jamal", 100, 100, 175, 0.8,false),
                new Player("Barack", 100, 120, 125, 0.9,false)
        ));
        preMadePlayers.put("Hard", List.of(
                new Player("Kamala", 75, 120, 100, 0.7,false),
                new Player("Joe", 50, 95, 50, 0.5,false)
        ));
        preMadePlayers.put("Joe Must Die", List.of(
                new Player("Lincoln", 75, 100, 80, 0.6,false)
        ));
    }

    //added the isHuman flag for knowing if is a human or NPC
    public Player(String name, int health, int armor, int stamina, double strength, List<Item> items, boolean isHuman) {
        this.name = name;
        this.health = health;
        this.armor = armor;
        this.stamina = stamina;
        this.strength = strength;

        //Items if null are new arraylist, if not arraylist with items
        this.items = items == null ? new ArrayList<>() : new ArrayList<>(items);
        this.tag = tagCounter++;
        
        this.isHuman=isHuman;
    }


    public Player(String name, int health, int armor, int stamina, double strength, boolean isHuman) {
        this(name, health, armor, stamina, strength, new ArrayList<>(),isHuman);
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

    public List<Item> getItems() {
        return items;
    }

    public double getStrength() {
        return strength;
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
    
    public boolean isHuman() {
    	return isHuman;
    }
    
    public void setHuman(boolean flag) {
    	isHuman=flag;
    }

    public void removeItem(int i) {
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
