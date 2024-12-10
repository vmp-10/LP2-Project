package common;

import characters.Player;
import tools.Item;

import java.util.List;

public final class AppConstants {

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
            """.formatted(AppConstants.createBox("Credits"), EQUALS_DIVIDER);

    public static final String CHARACTERS_EASY = """
            1. Trump   -> [HP 100, ARMOR 150, STAMINA 100, STRENGTH 0.9]
            2. Bush    -> [HP 100, ARMOR 100, STAMINA 150, STRENGTH 0.9]
            %s
            Option:\s""".formatted(EQUALS_DIVIDER);

    public static final String CHARACTERS_NORMAL = """
            1. Jamal   -> [HP 100, ARMOR 100, STAMINA 175, STRENGTH 0.8]
            2. Barack  -> [HP 100, ARMOR 120, STAMINA 125, STRENGTH 0.9]
            %s
            Option:\s""".formatted(EQUALS_DIVIDER);

    public static final String CHARACTERS_HARD = """
            1. Kamala  -> [HP 75, ARMOR 120, STAMINA 100, STRENGTH 0.7]
            2. Lincoln  -> [HP 75, ARMOR 100, STAMINA 80, STRENGTH 0.6]
            %s
            Option:\s""".formatted(EQUALS_DIVIDER);

    public static final String CHARACTERS_JOE_MUST_DIE = """
            1. Joe     -> [HP 50, ARMOR 95, STAMINA 50, STRENGTH 0.5]
            %s
            Option:\s""".formatted(EQUALS_DIVIDER);

    public static final String CHARACTERS_CUSTOM = """
            1. Empty     -> [HP 0, ARMOR 0, STAMINA 0, STRENGTH 0.0]
            %s
            Option:\s""".formatted(EQUALS_DIVIDER);


    public static final String DIFFICULTY = """
            1. Easy Mode
            2. Normal Mode
            3. Hard Mode
            4. Joe Must Die Mode
            5. Custom Mode
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




    public static String displayItems(int numItems, List<Item> items) {
        StringBuilder itemList = new StringBuilder();

        int last = 0;
        for (int i = 1; i <= numItems; i++) {
            itemList.append(i).append(". " + items.get(i) + "\n");
            last = i;
        }

        return """
                %s
                You have %d items:
                %s
                %d. Don't use any item. 
                %s
                """.formatted(EQUALS_DIVIDER, numItems, itemList.toString().trim(), last, EQUALS_DIVIDER);
    }

    public static String displayStats(Player player) {
        StringBuilder stats = new StringBuilder();

        stats.append("[HP: " + player.getHealth() + "]");
        stats.append("[Armor: " + player.getArmor() + "]");
        stats.append("[Stamina: " + player.getStamina() + "]");

        return stats.toString().trim();
    }

    public static String createBox(String text) {
        StringBuilder box = new StringBuilder();
        int padding = 2; // Space between text and the box edges
        int width = Math.max(20, text.length() + padding * 2); // Minimum width is 20 or enough to fit the text
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
