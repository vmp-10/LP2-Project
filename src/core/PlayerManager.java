package core;

import characters.Player;
import tools.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerManager {
    private final ArrayList<Player> players;
    private int numHumanPlayers;

    public PlayerManager() {
        this.players = new ArrayList<>();
        this.numHumanPlayers = -1;
    }

    public int getNumHumanPlayers() {
        return numHumanPlayers;
    }

    public void setNumHumanPlayers(int numHumanPlayers) {
        this.numHumanPlayers = numHumanPlayers;
    }

    public void initializePlayers(int numPlayers, List<Player> humanPlayers, String difficulty) {
        players.clear(); // Reset players list
        generatePlayers(numPlayers, humanPlayers, difficulty);
    }

    private void generatePlayers(int numPlayers, List<Player> humanPlayers, String difficulty) {
        Random random = new Random();
        List<Player> availablePlayers = Player.preMadePlayers.get(difficulty);

        for (int i = 0; i < numHumanPlayers; i++) {
            Player current = humanPlayers.get(i);
            players.add(current); // Add the user's chosen character in index[0]
            current.setTag(i);

            players.get(i).setWeapon(Objects.DEFAULT_RANGE);
        }

        for (int i = numHumanPlayers; i < numPlayers; i++) {
            Player temp = availablePlayers.get(random.nextInt(availablePlayers.size()));
            Player randomPlayer = new Player(temp);
            players.add(randomPlayer);
            players.get(i).setTag(i);

            players.get(i).setWeapon(Objects.DEFAULT_RANGE);
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
