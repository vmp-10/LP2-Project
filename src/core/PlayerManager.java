package core;

import characters.Player;
import tools.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerManager {
    private final ArrayList<Player> players;

    public PlayerManager() {
        this.players = new ArrayList<>();
    }

    public void initializePlayers(int numPlayers, Player chosenPlayer, String difficulty) {
        players.clear(); // Reset players list
        generatePlayers(numPlayers, chosenPlayer, difficulty);
    }

    private void generatePlayers(int numNPCs, Player chosenPlayer, String difficulty) {
        Random random = new Random();
        List<Player> availablePlayers = Player.preMadePlayers.get(difficulty);


        for (int i = 0; i < numNPCs; i++) {
            if (i == 0) {
                players.add(chosenPlayer); // Add the user's chosen character in index[0]
                chosenPlayer.setTag(0);
            } else {
                Player temp = availablePlayers.get(random.nextInt(availablePlayers.size()));
                Player randomPlayer = new Player(temp);
                players.add(randomPlayer);
                players.get(i).setTag(i);
            }
            players.get(i).addWeapon(Objects.DEFAULT_MELEE);
            players.get(i).addWeapon(Objects.DEFAULT_RANGE);
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
