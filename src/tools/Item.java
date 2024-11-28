package tools;

import java.util.ArrayList;
import java.util.List;

public abstract class Item {
    protected String name;
    protected String type;
    private static List<Item> items = new ArrayList<>();

    public Item(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public abstract void useItem(); // Abstract to be implemented by subclasses

    public static void add(Item item) {
        items.add(item);
    }

    public static Item get(int i) {
        if (i >= 0 && i < items.size()) {
            return items.get(i);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + i);
        }
    }

    public static void remove(int i) {
        if (i >= 0 && i < items.size()) {
            items.remove(i);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + i);
        }
    }
}
