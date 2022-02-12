package unsw.loopmania;

public class CharacterConvertAttack implements Attacks {

     /**
     * This attack will allow the character to convert an enemy to an ally temporarily.
     * @param  attacker 
     * @param  attacked
     */
    public void attack(MovingEntity attacker, MovingEntity attacked) {
        Character character = (Character) attacker;
        BasicEnemy enemy = (BasicEnemy) attacked;
        enemy.setConverted(true);

        
        if (character.getWeapon() instanceof Staff) {
            enemy.setConverted(true);
        } else {
            enemy.setHealth(enemy.getHealth() - character.getDamage(enemy));
        }
    }
}
