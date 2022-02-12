package unsw.loopmania;

public class BasicAttack implements Attacks {
    
    /**
     * Basic attack used by all enemies usually. Just inflicts their flat amount of damage.
     * @param  attacker 
     * @param  attacked
     */
    public void attack(MovingEntity attacker, MovingEntity attacked) {
        if (attacked instanceof Character) {
            Character character = (Character) attacked;
            character.setHealth(attacked.getHealth() - attacker.getDamage(), (BasicEnemy) attacker);
        } else {
            attacked.setHealth(attacked.getHealth() - attacker.getDamage());
        }
   }
}
