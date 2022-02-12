package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;

public class ComplexGoal implements Goal {
    private String type;
    private List<Goal> goals = new ArrayList<Goal>();;

    public ComplexGoal(String type) {
        this.type = type;
    }

    public void addGoal(Goal goal) {
        goals.add(goal);
    }

    /**
     * Checks if the game is won by checking the smaller goals recursively.
     * @param exp
     * @param gold
     * @param numBossesKilled
     * @param cycles
     */
    @Override
    public boolean gameWon(int exp, int gold, int numBossesKilled, int cycles) {
        switch(type) {
            case "AND":
                for (Goal g : goals) {
                    if (!g.gameWon(exp, gold, numBossesKilled, cycles)) {
                        return false;
                    }
                }
                return true;
            case "OR":
                for (Goal g : goals) {
                    if (g.gameWon(exp, gold, numBossesKilled, cycles)) {
                        return true;
                    }
                }
                return false; 
        }
        return false;
    }
}
