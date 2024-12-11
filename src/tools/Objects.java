package tools;

import java.util.List;

public final class Objects {
    // Melee Weapons
    public static final Weapon DEFAULT_MELEE = new Weapon("Axe", Rarity.COMMON, 5);
    public static final Weapon MUD_SWORD = new Weapon("Mud Sword", Rarity.COMMON, 8);
    public static final Weapon COPPER_SWORD = new Weapon("Copper Sword", Rarity.RARE, 12);
    public static final Weapon DAMASCUS_STEEL_SWORD = new Weapon("Damascus Steel Sword", Rarity.EPIC, 25);
    public static final Weapon IRIDIUM_SWORD = new Weapon("Iridium Sword", Rarity.LEGENDARY, 30);
    public static final Weapon SPIKED_CLUB = new Weapon("Spiked Club", Rarity.RARE, 10);
    public static final Weapon FLAMBERGE = new Weapon("Flamberge", Rarity.RARE, 20);
    public static final Weapon WAR_HAMMER = new Weapon("War Hammer", Rarity.EPIC, 35);
    public static final Weapon BATTLE_AXE = new Weapon("Battle Axe", Rarity.LEGENDARY, 40);
    public static final Weapon DRAGON_SLAYER_SWORD = new Weapon("Dragon Slayer Sword", Rarity.LEGENDARY, 50);

    // Ranged Weapons
    public static final Weapon DEFAULT_RANGE = new Weapon("Beretta", Rarity.COMMON, 10);
    public static final Weapon GLOCK_18 = new Weapon("Glock-18", Rarity.RARE, 15);
    public static final Weapon AK_47 = new Weapon("AK-47", Rarity.EPIC, 20);
    public static final Weapon M16 = new Weapon("M16 Rifle", Rarity.EPIC, 25);
    public static final Weapon SNIPER_RIFLE = new Weapon("Sniper Rifle", Rarity.LEGENDARY, 50);
    public static final Weapon CROSSBOW = new Weapon("Crossbow", Rarity.RARE, 12);
    public static final Weapon COMPOUND_BOW = new Weapon("Compound Bow", Rarity.RARE, 18);
    public static final Weapon DESERT_EAGLE = new Weapon("Desert Eagle", Rarity.RARE, 22);
    public static final Weapon ROCKET_LAUNCHER = new Weapon("Rocket Launcher", Rarity.LEGENDARY, 60);
    public static final Weapon FLAMETHROWER = new Weapon("Flamethrower", Rarity.LEGENDARY, 55);

    // Potions
    public static final Potion HEALTH_POTION = new Potion("Health Potion", Rarity.RARE, "health", 30);
    public static final Potion STAMINA_POTION = new Potion("Stamina Potion", Rarity.RARE, "stamina", 30);
    public static final Potion SUPER_HEALTH_POTION = new Potion("Super Health Potion", Rarity.EPIC, "health", 70);
    public static final Potion SUPER_STAMINA_POTION = new Potion("Super Stamina Potion", Rarity.EPIC, "stamina", 70);

    // Defense Items
    public static final Defense CLOTH_ARMOR = new Defense("Cloth Armor", Rarity.COMMON, 10);
    public static final Defense LEATHER_ARMOR = new Defense("Leather Armor", Rarity.RARE, 15);
    public static final Defense CHAIN_ARMOR = new Defense("Chain Armor", Rarity.EPIC, 20);
    public static final Defense DIAMOND_ARMOR = new Defense("Diamond Armor", Rarity.LEGENDARY, 30);

    public static final List<Weapon> WEAPONS = List.of(
            DEFAULT_MELEE, MUD_SWORD, COPPER_SWORD, DAMASCUS_STEEL_SWORD, IRIDIUM_SWORD,
            SPIKED_CLUB, FLAMBERGE, WAR_HAMMER, BATTLE_AXE, DRAGON_SLAYER_SWORD,
            DEFAULT_RANGE, GLOCK_18, AK_47, M16, SNIPER_RIFLE,
            CROSSBOW, COMPOUND_BOW, DESERT_EAGLE, ROCKET_LAUNCHER, FLAMETHROWER
    );

    public static final List<Potion> POTIONS = List.of(
            HEALTH_POTION, STAMINA_POTION, SUPER_HEALTH_POTION, SUPER_STAMINA_POTION
    );

    public static final List<Defense> DEFENSES = List.of(
            CLOTH_ARMOR, LEATHER_ARMOR, CHAIN_ARMOR, DIAMOND_ARMOR
    );
}
