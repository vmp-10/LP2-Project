package common;

import characters.Player;
import tools.Item;
import tools.Weapon;

import java.util.ArrayList;
import java.util.List;

public final class AppConstants {
    public static final int MAX_ITEMS = 4;
    public static final int MAX_WEAPONS = 2;
    
    public static final String STAR_DIVIDER = "**********************";
    public static final String EQUALS_DIVIDER = "=======================";

    public static final String CHARACTER_HEADER = createHeader("Available characters: ");
    public static final String TOOLS_HEADER = createHeader("Available tools: ");
    public static final String WEAPONS_HEADER = createHeader("Available weapons: ");

    public static final String MENU = """
            %s
            *     Welcome to     *
            *  the Battle Royal  *
            %s
            1. Start game
            2. Load file
            3. Credits
            %s
            Option:\s""".formatted(STAR_DIVIDER, STAR_DIVIDER, EQUALS_DIVIDER);

    public static final String CREDITS = """
            %s
            * Andrea Offredi
            * Fernando Alonso
            * Javier Suarez-Barcena
            * Vlad Prisacaru
            ---------------------------
            Thank you for playing!
            %s
            """.formatted(AppConstants.createBox("Credits", 25), EQUALS_DIVIDER);

    public static String createCharacterString(String difficulty) {
        List<Player> players = Player.preMadePlayers.getOrDefault(difficulty, new ArrayList<>());

        StringBuilder builder = new StringBuilder();
        builder.append(difficulty).append(" characters:\n");
        int index = 1;

        for (Player player : players) {
            builder.append(index++)
                    .append(". ")
                    .append(player.getName())
                    .append(" -> [HP ")
                    .append(player.getHealth())
                    .append(", ARMOR ")
                    .append(player.getShield())
                    .append(", STAMINA ")
                    .append(player.getStamina())
                    .append(", STRENGTH ")
                    .append(player.getStrength())
                    .append("]\n");
        }

        builder.append(EQUALS_DIVIDER).append("\nOption: ");
        return builder.toString();
    }

    public static final String DIFFICULTY = """
            1. Easy Mode
            2. Normal Mode
            3. Hard Mode
            4. Joe Must Die Mode
            %s
            Option:\s""".formatted(EQUALS_DIVIDER);


    public static String createHeader(String title) {
        return """
                %s
                %s
                %s
                """.formatted(EQUALS_DIVIDER, title, EQUALS_DIVIDER);
    }

    public static String createSelection(String title) {
        return """
                %s
                %s
                %s
                Option:\s""".formatted(EQUALS_DIVIDER, title, EQUALS_DIVIDER);
    }

    public static String displayItems(Player player) {
        StringBuilder itemList = new StringBuilder();

        List<Item> items = player.getItems();
        int numItems = items.size();

        for (int i = 0; i < numItems; i++) {
            itemList.append(i + 1).append(". ").append(items.get(i).getName()).append("\n");
        }

        return """
            %s
            Items:
            %s
            %s
            """.formatted(EQUALS_DIVIDER, itemList.toString().trim(), EQUALS_DIVIDER);
    }


    public static String displayItemChoice(Player player) {
        StringBuilder itemList = new StringBuilder();

        List<Item> items = player.getItems();
        int numItems = items.size();

        for (int i = 0; i < numItems; i++) {
            itemList.append(i + 1).append(". ").append(items.get(i).getName()).append("\n");
        }

        itemList.append(numItems + 1).append(". Don't use any item.");

        return """
            %s
            You have %d items:
            %s
            %s
            """.formatted(EQUALS_DIVIDER, numItems, itemList.toString().trim(), EQUALS_DIVIDER);
    }


    public static String displayWeaponsChoice(List<Weapon> weapons) {
        StringBuilder stringBuilder = new StringBuilder();

        int last = 0;
        for (int i = 0; i < weapons.size(); i++) {
            stringBuilder.append(i+1).append(". "+ displayWeaponStats(weapons.get(i)) + "\n");
            last = i+1;
        }
        
        if(weapons.isEmpty()) {
        	return "No weapons available!!";
        }

        return """
                %s
                Weapons:
                %s
                %s
                """.formatted(EQUALS_DIVIDER, stringBuilder.toString().trim(), last, EQUALS_DIVIDER);
    }

    public static String displayPlayerStats(Player player) {
        StringBuilder stats = new StringBuilder();

        stats.append("[HP: " + player.getHealth() + "]");
        stats.append("[Armor: " + player.getShield() + "]");
        stats.append("[Stamina: " + player.getStamina() + "]");

        return stats.toString().trim();
    }

    public static String displayWeapons(Player player) {
        StringBuilder stats = new StringBuilder();
        Weapon weapon0 = player.getWeapon(0);
        Weapon weapon1 = player.getWeapon(1);

        stats.append("[" + weapon0.getName() + ": " + weapon0.getBaseDamage() + "]");
        stats.append("[" + weapon1.getName() + ": " + weapon1.getBaseDamage() + "]");

        return stats.toString().trim();
    }

    public static String displayWeaponStats(Weapon weapon) {
        StringBuilder stats = new StringBuilder();
        
        stats.append("[" + weapon.getName() + ": " + weapon.getBaseDamage() + "]");

        return stats.toString().trim();
    }

    public static String compareWeapons(Weapon weapon0, Weapon weapon1) {
        StringBuilder stats = new StringBuilder();

        stats.append("[" + weapon0.getName() +"/" + weapon1.getName() + " | " + weapon0.getBaseDamage() +"/" + weapon1.getBaseDamage() + "]");

        return stats.toString().trim();
    }

    public static String createBox(String text, int width) {
        StringBuilder box = new StringBuilder();
        int padding = 2; // Space between text and the box edges

        // Ensure the width is sufficient to fit the text with padding
        width = Math.max(width, text.length() + padding * 2);
        String border = "*".repeat(width);

        // Add the top border
        box.append(border).append("\n");

        // Add the centered text
        int totalPadding = width - 2 - text.length(); // Space left after subtracting text length and border
        int paddingLeft = totalPadding / 2;
        int paddingRight = totalPadding - paddingLeft;

        String centeredText = " ".repeat(paddingLeft) + text + " ".repeat(paddingRight);
        box.append("*").append(centeredText).append("*").append("\n");

        // Add the bottom border
        box.append(border);

        return box.toString();
    }


    private AppConstants() {
    }

    
}
