package tools;

public final class Objects {
    public static final Weapon MUD_SWORD = new Weapon("Mud Sword", Rarity.COMMON,15);
    public static final Weapon COPPER_SWORD = new Weapon("Copper Sword", Rarity.RARE,25);
    public static final Weapon DAMASCUS_STEEL_SWORD = new Weapon("Damascus Steel Sword", Rarity.EPIC,35);
    public static final Weapon IRIDIUM_SWORD = new Weapon("Iridium sword", Rarity.LEGENDARY,50);

    public static  final  Potion HEALTH_POTION = new Potion("Health Potion", Rarity.RARE, "health", 30);
    public static  final  Potion STAMINA_POTION = new Potion("Stamina Potion", Rarity.RARE, "stamina", 30);
    public static  final  Potion SUPER_HEALTH_POTION = new Potion("Super Health Potion", Rarity.EPIC, "health", 70);
    public static  final  Potion SUPER_STAMINA_POTION = new Potion("Super Stamina Potion", Rarity.EPIC, "stamina", 70);

    public  static  final Defense CLOTH_ARMOR = new Defense("Cloth Armor", Rarity.COMMON, 10);
    public  static  final Defense LEATHER_ARMOR = new Defense("Leather Armor", Rarity.RARE, 15);
    public  static  final Defense CHAIN_ARMOR = new Defense("Chain Armor", Rarity.EPIC, 20);
    public  static  final Defense DIAMOND_ARMOR = new Defense("Diamond Armor", Rarity.LEGENDARY, 30);
}
