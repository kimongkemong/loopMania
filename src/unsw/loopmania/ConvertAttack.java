package unsw.loopmania;

public class ConvertAttack implements Attacks {

     /**
     * This attack will allow the enemy to convert an allied soldier
     * to an enemy.
     * @param  attacker 
     * @param  attacked
     */
    public void attack(MovingEntity attacker, MovingEntity attacked) {
        FightingAlly ally = (FightingAlly) attacked;
        ally.setConverted(true);
    }
}
