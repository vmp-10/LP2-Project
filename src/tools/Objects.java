package tools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Objects {
    // Weapons
    public static final Weapon BOW_ARROW = new Weapon("Bow & Arrow", Rarity.COMMON, 8);
    public static final Weapon DEFAULT_RANGE = new Weapon("Beretta", Rarity.COMMON, 10);

    public static final Weapon CROSSBOW = new Weapon("Crossbow", Rarity.RARE, 12);
    public static final Weapon GLOCK_18 = new Weapon("Glock-18", Rarity.RARE, 15);
    public static final Weapon COMPOUND_BOW = new Weapon("Compound Bow", Rarity.RARE, 18);
    public static final Weapon SHOTGUN = new Weapon("Shotgun", Rarity.RARE, 18);
    public static final Weapon BLUNDERBUSS = new Weapon("Blunderbuss", Rarity.RARE, 20);
    public static final Weapon DESERT_EAGLE = new Weapon("Desert Eagle", Rarity.RARE, 22);

    public static final Weapon AK_47 = new Weapon("AK-47", Rarity.EPIC, 20);
    public static final Weapon M16 = new Weapon("M16 Rifle", Rarity.EPIC, 25);
    public static final Weapon ASSAULT_RIFLE = new Weapon("Assault Rifle", Rarity.EPIC, 30);
    public static final Weapon LASER_GUN = new Weapon("Laser Gun", Rarity.EPIC, 35);

    public static final Weapon MACHINE_GUN = new Weapon("Machine Gun", Rarity.LEGENDARY, 45);
    public static final Weapon PLASMA_RIFLE = new Weapon("Plasma Rifle", Rarity.LEGENDARY, 50);
    public static final Weapon SNIPER_RIFLE = new Weapon("Sniper Rifle", Rarity.LEGENDARY, 50);
    public static final Weapon FLAMETHROWER = new Weapon("Flamethrower", Rarity.LEGENDARY, 55);
    public static final Weapon GRENADE_LAUNCHER = new Weapon("Grenade Launcher", Rarity.LEGENDARY, 55);
    public static final Weapon ROCKET_LAUNCHER = new Weapon("Rocket Launcher", Rarity.LEGENDARY, 60);

    // Potions
    public static final Potion HEALTH_POTION = new Potion("Health Potion", Rarity.RARE, "Health", 0.25);
    public static final Potion STAMINA_POTION = new Potion("Stamina Potion", Rarity.RARE, "Stamina", 0.25);
    public static final Potion SUPER_HEALTH_POTION = new Potion("Super Health Potion", Rarity.EPIC, "Health", 0.5);
    public static final Potion SUPER_STAMINA_POTION = new Potion("Super Stamina Potion", Rarity.EPIC, "Stamina", 0.5);

    public static final List<Weapon> WEAPONS = List.of(
            BOW_ARROW, DEFAULT_RANGE, CROSSBOW, GLOCK_18, COMPOUND_BOW,
            SHOTGUN, BLUNDERBUSS, DESERT_EAGLE, AK_47, M16, ASSAULT_RIFLE,
            LASER_GUN, MACHINE_GUN, PLASMA_RIFLE, SNIPER_RIFLE, FLAMETHROWER,
            GRENADE_LAUNCHER, ROCKET_LAUNCHER
    );

    public static final List<Potion> POTIONS = List.of(HEALTH_POTION, STAMINA_POTION, SUPER_HEALTH_POTION, SUPER_STAMINA_POTION);

    public static Map<Rarity, List<Weapon>> weaponsByRarity = new HashMap<>();
    public static Map<Rarity, List<Potion>> potionsByRarity = new HashMap<>();
    
    static {
        weaponsByRarity.put(Rarity.COMMON, Arrays.asList(BOW_ARROW, DEFAULT_RANGE));
        weaponsByRarity.put(Rarity.RARE, Arrays.asList(CROSSBOW, GLOCK_18, COMPOUND_BOW, SHOTGUN, BLUNDERBUSS, DESERT_EAGLE));
        weaponsByRarity.put(Rarity.EPIC, Arrays.asList(AK_47, M16, ASSAULT_RIFLE, LASER_GUN));
        weaponsByRarity.put(Rarity.LEGENDARY, Arrays.asList(MACHINE_GUN, PLASMA_RIFLE, SNIPER_RIFLE, FLAMETHROWER, GRENADE_LAUNCHER, ROCKET_LAUNCHER));

        potionsByRarity.put(Rarity.RARE, Arrays.asList(HEALTH_POTION, STAMINA_POTION));
        potionsByRarity.put(Rarity.EPIC, Arrays.asList(SUPER_HEALTH_POTION, SUPER_STAMINA_POTION));
    }
}
