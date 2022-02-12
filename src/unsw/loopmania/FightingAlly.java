package unsw.loopmania;

/** The fighting Ally is static entity which 
 *  assists the character during a battle
 */
public class FightingAlly extends MovingEntity {
    private boolean converted = false;
    private int roundsInTrance = 0;

    /**
     * Converts a static version of the ally into a fighting ally to allow
     * them to take part in battles.
     * @param pos
     * @param health
     * @param damage
     */
    public FightingAlly(PathPosition pos, int health, int damage) {
        super(pos);
        this.setHealth(health);
        this.setDamage(damage);
        this.setAttack(new BasicAttack());

    }

    /**
     * Converts a static version of the ally into a fighting ally to allow
     * them to take part in battles.
     * @param pos
     */
    public FightingAlly(PathPosition pos) {
        super(pos);
        this.setHealth(100);
        this.setDamage(10);
        this.setAttack(new BasicAttack());

    }
    /**
     * Returns True if is converted, else false
     * @return
     */
    public boolean isConverted() {
        return converted;
    }

    public void setConverted(boolean converted) {
        this.converted = converted;
    }
    /**
     * Getter for rounds that a fighting Ally is under 
     * the characters trance 
     * @return
     */
    public int getRoundsInTrance() {
        return roundsInTrance;
    }

    public void setRoundsInTrance(int roundsInTrance) {
        this.roundsInTrance = roundsInTrance;
    }

 

    
}
