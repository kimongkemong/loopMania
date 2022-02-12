package unsw.loopmania;

public class BasicGoal implements Goal {
    private int quantity;
    private String type;

    public BasicGoal(String type, int quantity) {
        this.quantity = quantity;
        this.type = type;
    }

    
    /**
     * Checks if the game is won by checking if the goals are satisfied.
     * @param exp
     * @param gold
     * @param numBossesKilled
     * @param cycles
     */
    @Override
    public boolean gameWon(int exp, int gold, int numBossesKilled, int cycles) {
        switch(type) {
            case "experience":
                if (exp >= quantity) {
                    return true;
                }
                break;
            case "gold":
                if (gold >= quantity) {
                    return true;
                } 
                break;
            case "cycles":
                if (cycles >= quantity) {
                    return true;
                }
                break; 
            case "bosses":
                if (numBossesKilled == 2) {
                    return true;
                }
        }
        return false;
    }

    


}
