package core;

import characters.Player;

import java.util.ArrayList;
import java.util.Random;

public class EventManager {
    public EventManager(){

    }

    public void generateEvent(ArrayList<Player> players, int index,
                              TurnHandler turnHandler, GameInputHandler inputHandler, PlayerManager playerManager) {
        Random random = new Random();
        int event = random.nextInt(0, 100); // Generate a number from 0 to 99

        /*
        TODO: Create a "you get shot at" event -> Chance to actually get hit, you get prompted to fight or escape
            Event probabilities:
            - 5% (0-4): Drop lands nearby
            - 25% (5-29): Out of safe zone
            - 10% (30-39): All quiet
            - 15% (40-54): Enemy found
            - 15% (55-69): Weapon found
            - 15% (70-84): Item found
            - 15% (85-99): Trap found
        */

        if (event < 5) {
            Events.dropLandedNearby(players.get(index),turnHandler, inputHandler, playerManager);
        } else if (event < 30) {
            Events.outOfSafeZone(players.get(index), playerManager);
        } else if (event < 40) {
            Events.allQuiet(players.get(index), turnHandler, inputHandler, playerManager);
        } else if (event < 55) {
            Events.enemyFound(players.get(index), players.get(random.nextInt(players.size())), inputHandler, playerManager);
        } else if (event < 70) {
            Events.weaponFound(players.get(index), turnHandler, inputHandler, playerManager);
        } else if (event < 85) {
            Events.itemFound(players.get(index), turnHandler, inputHandler, playerManager);
        } else {
            Events.trapFound(players.get(index), playerManager);
        }
    }
}
