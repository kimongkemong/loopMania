package unsw.loopmania;

import java.util.Random;

import unsw.loopmania.Enemies.Vampire;

public class CritAttack implements Attacks {

    /**
     * This will cause a critical strike based on chance. 
     * @param  attacker 
     * @param  attacked
     */
    public void attack(MovingEntity attacker, MovingEntity attacked) {
        Vampire vamp = (Vampire) attacker;
        if (vamp.getTotalCritRounds() == 0) {
            vamp.setTotalCritRounds(new Random().nextInt(5) + 1);
            vamp.setCritdmg(new Random().nextInt(3) + 1);
        }
        if (attacked instanceof Character) {
            Character character = (Character) attacked;
            character.setHealth(attacked.getHealth() - vamp.getDamage(), vamp);
        } else {
            attacked.setHealth(attacked.getHealth() - vamp.getDamage());
        }
    }
}
