package core;

import characters.MC;
import characters.NPC;

import java.util.ArrayList;

@Deprecated
public class Game {
    private final int difficulty, numNPCs;
    private final MC playableCharacter;
    private ArrayList<NPC> NPCs = new ArrayList<>();

    public Game(int difficulty, int numNPCs, MC playableCharacter, ArrayList<NPC> NPCs) {
        this.difficulty = difficulty;
        this.numNPCs = numNPCs;
        this.playableCharacter = playableCharacter;
        this.NPCs = NPCs;
    }

    public ArrayList<NPC> getNPCs() {
        return NPCs;
    }

    public MC getPlayableCharacter() {
        return playableCharacter;
    }
}
