package core;

import characters.Player;
import tools.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerManager {
    private final ArrayList<Player> players;
    private ArrayList<Player> forFile;
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
        players.clear();        // Reset players list
        generatePlayers(numPlayers, humanPlayers, difficulty);
        this.forFile = players; // Players to display at the file, safe of editing
    }

    private void generatePlayers(int numPlayers, List<Player> humanPlayers, String difficulty) {
        Random random = new Random();
        List<Player> availablePlayers = Player.preMadePlayers.get(difficulty);

        for (int i = 0; i < numHumanPlayers; i++) {
            Player temp = humanPlayers.get(i);
            Player current = new Player(temp);

            players.add(current);
            players.get(i).setTag(i);

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

    public ArrayList<Player> getPlayersForFile() {
        return forFile;
    }


    public Player getRandomPlayer() {
        return players.get(new Random().nextInt(players.size()));
    }

    public void removePlayers(List<Player> playersToRemove) {
        players.removeAll(playersToRemove);
    }

    public boolean isHuman(Player player) {
        return player.getTag() > 0 && player.getTag() <= numHumanPlayers;
    }
}
