package characters;

import common.AppConstants;
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

    //These to limit the player's health to their initial value.
    protected int maxHealth;
    protected int maxShield;
    protected int maxStamina;

    protected int stamina;
    protected double strength;

    protected int tag;
    protected List<Item> items;
    protected Weapon weapon;

    // HashMap for pre-made players categorized by difficulty
    public static final Map<String, List<Player>> preMadePlayers = new HashMap<>();

    //Static so it's only created once
    static {
        createPreMadePlayers();
    }

    private static void createPreMadePlayers() {
        preMadePlayers.put("Easy", List.of(
                new Player("Trump", 50, 40, 50, 0.9),
                new Player("Bush", 50, 30, 45, 0.9),
                new Player("Carter", 45, 35, 40, 0.85),
                new Player("Ford", 48, 38, 35, 0.88)
        ));
        preMadePlayers.put("Normal", List.of(
                new Player("Jamal", 40, 30, 50, 0.8),
                new Player("Barack", 45, 35, 40, 0.9),
                new Player("Clinton", 38, 32, 45, 0.83),
                new Player("Franklin", 42, 33, 40, 0.85)
        ));
        preMadePlayers.put("Hard", List.of(
                new Player("Kamala", 30, 35, 25, 0.7),
                new Player("Lincoln", 28, 25, 20, 0.6),
                new Player("Roosevelt", 32, 30, 22, 0.65),
                new Player("Jackson", 27, 24, 28, 0.68)
        ));
        preMadePlayers.put("Joe Must Die", List.of(
                new Player("Joe", 15, 20, 15, 0.5)
        ));
    }

    public Player(String name, int health, int shield, int stamina, double strength, List<Item> items, Weapon weapon, int tag) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.maxShield = shield;
        this.maxStamina = stamina;
        this.shield = shield;
        this.stamina = stamina;
        this.strength = strength;
        this.tag = tag;
        this.weapon = weapon;

        // Initialize items to new lists if null else copy the existing lists
        this.items = (items == null) ? new ArrayList<>() : new ArrayList<>(items);
    }

    public Player(String name, int health, int shield, int stamina, double strength) {
        this(name, health, shield, stamina, strength, null, null, -1);
    }

    public Player(Player other) {
        this.name = other.name;
        this.health = other.health;
        this.maxHealth = other.health;
        this.maxShield = other.shield;
        this.maxStamina = other.stamina;
        this.shield = other.shield;
        this.stamina = other.stamina;
        this.strength = other.strength;
        this.tag = other.tag;
        this.items = new ArrayList<>(other.items);
        this.weapon = other.weapon;
    }

    public void setHealth(int health) {
        if (health > maxHealth) {
            health = maxHealth;
        } else if (health < 0) {
            health = 0;
        }
        this.health = health;
    }

    public void setShield(int shield) {
        if (shield > maxShield) {
            shield = maxShield;
        } else if (shield < 0) {
            shield = 0;
        }
        this.shield = shield;
    }

    public void setStamina(int stamina) {
        if (stamina > maxStamina) {
            stamina = maxStamina;
        } else if (stamina < 0) {
            stamina = 0;
        }
        this.stamina = stamina;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
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

    public Item getItem(int i) {
        return items.get(i);
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

    public void addItem(Item item) {
        if (items.size() < AppConstants.MAX_ITEMS) {
            if (tag == 0) {
                System.out.println(item.getName() + " added to inventory.");
            }
            items.add(item);
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void takeDamage(int damage, boolean isHuman) {
        double effectiveDamage = damage * strength;

        if (shield > 0) {
            int previousArmor = shield;
            shield -= (int) effectiveDamage;

            setShield(shield);

            if (isHuman) {
                System.out.println(" -> Player's armor absorbed " + Math.min(effectiveDamage, previousArmor) + " damage.");
            }
        } else {
            health -= (int) effectiveDamage;
            setHealth(health);

            if (isHuman) {
                System.out.println(" -> Player takes " + effectiveDamage + " damage.");
            }
        }
    }

    public void takeStormDamage(int damage, boolean isHuman) {
        health -= damage;
        setHealth(health);

        if (isHuman) {
            System.out.println(" -> Player takes " + damage + " damage.");
        }
    }

    public void attack(Player target, boolean isHuman) {
        int damage = weapon.getDamage(this);

        if (isHuman) {
            System.out.println(" -> " + name + " attacks Player " + target.getTag() + " with " + weapon.getName() + " for " + damage + " damage!");
        }

        target.takeDamage(damage, isHuman);
    }
}
