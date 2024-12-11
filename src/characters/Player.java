package characters;

import tools.Defense;
import tools.Item;
import tools.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    protected String name;
    protected int health;
    protected int shield;
    protected int stamina;
    protected double strength;

    private static final int MAX_ITEMS = 4;
    private static final int MAX_WEAPONS = 2;

    protected int tag;
    protected List<Item> items;
    protected List<Weapon> weapons;

    // HashMap for pre-made players categorized by difficulty
    public static final Map<String, List<Player>> preMadePlayers = new HashMap<>();

    //Static so it's only created once
    static {
        createPreMadePlayers();
    }

    private static void createPreMadePlayers() {
        preMadePlayers.put("Easy", List.of(
                new Player("Trump", 100, 150, 100, 0.9),
                new Player("Bush", 100, 100, 150, 0.9),
                new Player("Carter", 100, 120, 130, 0.85),
                new Player("Ford", 100, 140, 110, 0.88)
        ));
        preMadePlayers.put("Normal", List.of(
                new Player("Jamal", 100, 100, 175, 0.8),
                new Player("Barack", 100, 120, 125, 0.9),
                new Player("Clinton", 90, 110, 150, 0.83),
                new Player("Franklin", 95, 115, 140, 0.85)
        ));
        preMadePlayers.put("Hard", List.of(
                new Player("Kamala", 75, 120, 100, 0.7),
                new Player("Lincoln", 75, 100, 80, 0.6),
                new Player("Roosevelt", 80, 110, 90, 0.65),
                new Player("Jackson", 70, 95, 110, 0.68)
        ));
        preMadePlayers.put("Joe Must Die", List.of(
                new Player("Joe", 50, 95, 50, 0.5)
        ));
    }

    public Player(String name, int health, int shield, int stamina, double strength, List<Item> items, List<Weapon> weapons, int tag) {
        this.name = name;
        this.health = health;
        this.shield = shield;
        this.stamina = stamina;
        this.strength = strength;
        this.tag = tag;

        // Initialize items and weapons to new lists if null, otherwise copy the existing lists
        this.items = (items == null) ? new ArrayList<>() : new ArrayList<>(items);
        this.weapons = (weapons == null) ? new ArrayList<>() : new ArrayList<>(weapons);
    }

    public Player(String name, int health, int shield, int stamina, double strength) {
        this(name, health, shield, stamina, strength, null, null, -1);
    }

    public Player(Player other) {
        this.name = other.name;
        this.health = other.health;
        this.shield = other.shield;
        this.stamina = other.stamina;
        this.strength = other.strength;
        this.tag = other.tag;
        this.items = new ArrayList<>(other.items);
        this.weapons = new ArrayList<>(other.weapons);
    }

    public void setHealth(int health) {
        if (health > 150) {
            health = 150;
        } else if (health < 0) {
            health = 0;
        }
        this.health = health;
    }

    public void setShield(int shield) {
        if (shield > 150) {
            shield = 150;
        } else if (shield < 0) {
            shield = 0;
        }
        this.shield = shield;
    }

    public void setStamina(int stamina) {
        if (stamina > 150) {
            stamina = 150;
        } else if (stamina < 0) {
            stamina = 0;
        }
        this.stamina = stamina;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getShield() {
        return shield;
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

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addItem(Item item){
        if (items.size() < MAX_ITEMS) {
            items.add(item);
        }
    }

    public void removeWeapon(int i) {
        weapons.remove(i);
    }

    public Weapon getWeapon(int i) {
        return weapons.get(i);
    }

    public void removeWeapon(Weapon weapon) {
        weapons.remove(weapon);
    }

    public void addWeapon(Weapon weapon){
        if (weapons.size() < MAX_WEAPONS) {
            weapons.add(weapon);
        }
    }

    public void takeDamage(int damage, boolean isHuman) {
        double effectiveDamage = damage * strength;

        if (shield > 0) {
            int previousArmor = shield;
            shield -= (int) effectiveDamage;

            setShield(shield);

            if (isHuman) {
                System.out.println("Player's armor absorbed " + Math.min(effectiveDamage, previousArmor) +" damage.");
            }
        }
        else {
            health -= (int) effectiveDamage;
            setHealth(health);

            if (isHuman) {
                System.out.println("Player takes " + effectiveDamage + " damage.");
            }
        }
    }

    public void attack(Player target, boolean isHuman) {
        int damage = (int) (strength * 10); // Replace 10 for tool damage
        if (isHuman) {
            System.out.println(name + " attacks " + target.getTag() + " for " + damage + " damage.");
        }
        target.takeDamage(damage, isHuman);
    }
}
