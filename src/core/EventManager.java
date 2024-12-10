package core;

import characters.Player;

import java.util.ArrayList;
import java.util.Random;

public class EventManager {

    public static void generateEvent(ArrayList<Player> players, int index) {
        Random random = new Random();
        int event = random.nextInt(0, 100); // Generate a number from 0 to 99

        if (event < 5) {                                // 5%
            Events.dropLandedNearby(players.get(index));
        } else if (event >= 5 && event < 30) {          // 25%
            Events.outOfSafeZone(players.get(index));
        } else if (event >= 30 && event < 40) {         // 10%
            Events.allQuiet(players.get(index));
        } else if (event >= 40 && event < 55) {         // 15%
            Events.enemyFound(players.get(index), players.get(random.nextInt(players.size())));
        } else if (event >= 55 && event < 70) {         // 15%
            Events.weaponFound(players.get(index));
        } else if (event >= 70 && event < 85) {         // 15%
            Events.itemFound(players.get(index));
        } else {                                        // 15%
            Events.trapFound(players.get(index));
        }
    }
}
