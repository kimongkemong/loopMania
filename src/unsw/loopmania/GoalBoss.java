package unsw.loopmania;

public class GoalBoss implements Goals{
    private int bossesDefeated;
    
    public GoalBoss(int bossesDefeated) {
        this.bossesDefeated = bossesDefeated;
    }

    @Override
    public boolean checkGoals(LoopManiaWorld loopManiaWorld) {
        if (bossesDefeated > 10) {
            return true;
        }
        return false;
    }
    
}
