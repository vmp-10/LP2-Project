package tools;

public class Objects {
    public static final Weapon WOODEN_SWORD = new Weapon("Wooden Sword", Rarity.COMMON,15);
    public static final Weapon STONE_SWORD = new Weapon("Stone Sword", Rarity.RARE,25);
    public static final Weapon IRON_SWORD = new Weapon("Iron Sword", Rarity.EPIC,35);
    public static final Weapon DIAMOND_SWORD = new Weapon("Diamond sword", Rarity.LEGENDARY,50);

    public static  final  Potion HEALTH_POTION = new Potion("Health Potion", Rarity.RARE, "health", 30);
    public static  final  Potion STAMINA_POTION = new Potion("Stamina Potion", Rarity.RARE, "stamina", 30);
    public static  final  Potion SUPER_HEALTH_POTION = new Potion("Super Health Potion", Rarity.EPIC, "health", 70);
    public static  final  Potion SUPER_STAMINA_POTION = new Potion("Super Stamina Potion", Rarity.EPIC, "stamina", 70);

    public  static  final Defense LEATHER_ARMOR = new Defense("Leather Armor", Rarity.COMMON, 10);
    public  static  final Defense IRON_ARMOR = new Defense("Iron Armor", Rarity.RARE, 15);
    public  static  final Defense GOLD_ARMOR = new Defense("Gold Armor", Rarity.EPIC, 20);
    public  static  final Defense DIAMOND_ARMOR = new Defense("Diamond Armor", Rarity.LEGENDARY, 30);
}
