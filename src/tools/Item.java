package tools;

import characters.Player;

public abstract class Item {
    protected String name;
    protected String type;
    protected String rarity;

    public Item(String name, String type, String rarity) {
        this.name = name;
        this.type = type;
        this.rarity = rarity;
    }


    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }

    public abstract void use(Player player);
}
