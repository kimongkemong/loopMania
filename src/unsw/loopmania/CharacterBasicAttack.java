package unsw.loopmania;

public class CharacterBasicAttack implements Attacks {

    /**
     * Basic attack used by all character types usually. Just inflicts their flat amount of damage
     * but is affected by the weapons equipped.
     * @param  attacker 
     * @param  attacked
     */
    public void attack(MovingEntity attacker, MovingEntity attacked) {
        Character character = (Character) attacker;
        BasicEnemy enemy = (BasicEnemy) attacked;
        enemy.setHealth(enemy.getHealth() - character.getDamage(enemy));
   }
}
