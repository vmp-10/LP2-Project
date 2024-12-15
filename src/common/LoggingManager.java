package common;

import java.util.ArrayList;
import java.util.List;
public class LoggingManager {
    private final List<String> gameLog;
    private int rounds;

    public LoggingManager() {
        this.gameLog = new ArrayList<>();
        this.rounds = 1;
    }


    public List<String> getGameLog() {
        return gameLog;
    }

    public void addToGameLog(String line){
        if (gameLog.size() < AppConstants.MAX_LOGS) {
            gameLog.add(line);
        }
    }

    public int getRounds(){
        return rounds;
    }

    public void incrementRounds(){
        this.rounds++;
    }
}
