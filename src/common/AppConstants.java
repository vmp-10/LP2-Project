package common;

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
            3. Create character
            %s
            Option:\s""".formatted(STAR_DIVIDER, STAR_DIVIDER, EQUALS_DIVIDER);

    public static final String CHARACTERS = """
            1. Trump   -> [HP 100, ARMOR 150, STAMINA 100, STRENGTH 0.9]
            2. Bush    -> [HP 100, ARMOR 100, STAMINA 150, STRENGTH 0.9]
            3. Jamal   -> [HP 100, ARMOR 100, STAMINA 175, STRENGTH 0.8]
            4. Barack  -> [HP 100, ARMOR 120, STAMINA 125, STRENGTH 0.9]
            5. Kamala  -> [HP 75, ARMOR 120, STAMINA 100, STRENGTH 0.7]
            6. Joe     -> [HP 50, ARMOR 95, STAMINA 50, STRENGTH 0.5]
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
                %s:
                %s
                """.formatted(EQUALS_DIVIDER, title, EQUALS_DIVIDER);
    }

    public static final String CROWN = """
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣟⣀⣽⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠈⣿⠁⠀⠀⠀⠀⠀⠀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣟⣋⡇⠀⠀⠀⠀⠀⢀⠟⡄⠀⠀⠀⠀⠀⣾⣯⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀
            ⢠⠴⡄⠀⠀⠀⠀⠀⠀⠈⣿⡀⠀⠀⠀⠀⠀⡸⠀⢧⠀⠀⠀⠀⠀⢀⣏⠁⠀⠀⠀⠀⠀⠀⣠⣦⡄
            ⠘⠓⠻⣤⡀⠀⠀⠀⠀⠀⡏⢣⠀⠀⠀⠀⢀⠇⠀⠸⡄⠀⠀⠀⠀⡜⢸⠀⠀⠀⠀⠀⠀⣠⡾⠟⠃
            ⠀⠀⠀⢣⠙⠦⡀⠀⠀⢠⠃⠈⢇⠀⠀⠀⡞⠀⠀⠀⢣⠀⠀⠀⡼⠁⢸⡄⠀⠀⢀⡴⠊⡞⠀⠀⠀
            ⠀⠀⠀⠈⡆⠀⠙⢦⠀⠸⠀⠀⠈⢆⠀⢰⠁⠀⠀⠀⠈⣇⠀⡰⠁⠀⠈⣇⠀⡰⠋⠀⢰⠀⠀⠀⠀
            ⠀⠀⠀⠀⢁⠀⠀⠀⠱⡇⠀⠀⠀⠈⢦⠇⠀⠀⠀⠀⠀⠘⡶⠁⠀⠀⠀⢸⠞⠀⠀⠀⡾⠀⠀⠀⠀
            ⠀⠀⠀⠀⢸⠀⠀⢀⣀⣀⣀⣤⣤⣤⣴⣤⣤⣤⣤⣤⣤⣤⣤⣤⣤⣀⣀⣀⣀⠀⠀⠀⡇⠀⠀⠀⠀
            ⠀⠀⠀⠀⣼⠶⢿⣟⠛⠉⠉⢩⡟⢧⠀⠀⠀⣴⠛⣦⠀⠀⢠⠞⢫⡉⠉⠙⢛⡟⠿⠶⡷⠀⠀⠀⠀
            ⠀⠀⠀⠀⠹⡄⠸⣽⣃⣀⣀⣈⣿⣯⣤⣤⣤⣬⣾⣥⣤⣤⣬⣷⣯⣀⣀⣀⣻⡼⠀⢰⠃⠀⠀⠀⠀
            ⠀⠀⠀⠀⢼⠗⠛⠋⠉⠉⠉⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠉⠉⠉⠉⠛⠛⢻⡦⠀⠀⠀⠀
            ⠀⠀⠀⠀⠈⠉⠑⠒⠒⠂⠤⠤⠤⠤⠤⠤⠤⠤⠤⠤⠤⠤⠤⠤⠤⠤⠤⠒⠒⠒⠊⠉⠁⠀⠀⠀⠀
            
            """;

    private AppConstants() {
    }

    public static String displayItems(int numItems, List<Item> items) {
        StringBuilder itemList = new StringBuilder();

        for (int i = 1; i <= numItems; i++) {
            itemList.append(i).append(". " + items.get(i) + "\n");
        }

        return """
            %s
            You have %d items:
            %s
            %s
            """.formatted(EQUALS_DIVIDER, numItems, itemList.toString().trim(), EQUALS_DIVIDER);
    }

}
