package unsw.loopmania;

public class StunAttack implements Attacks {

    /**
     * This attack will stun the enemy and cause them damage.
     * @param  attacker 
     * @param  attacked
     */
    public void attack(MovingEntity attacker, MovingEntity attacked) {
        attacked.setStunned(true);
        if (attacked instanceof Character) {
            Character character = (Character) attacked;
            character.setHealth(attacked.getHealth() - attacker.getDamage(), (BasicEnemy) attacker);
        } else {
            attacked.setHealth(attacked.getHealth() - attacker.getDamage());
        }
    }
}
