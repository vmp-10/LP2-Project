    package core;

    import characters.Player;

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

            players.add(chosenPlayer); // Add the user's chosen character

            for (int i = 1; i < numNPCs; i++) {
                Player temp = availablePlayers.get(random.nextInt(availablePlayers.size()));
                Player randomPlayer = new Player(temp);
                players.add(randomPlayer);
                players.get(i).setTag(i);
            }
            players.getFirst().setTag(0);
        }

        public ArrayList<Player> getPlayers() {
            return players;
        }
    }
