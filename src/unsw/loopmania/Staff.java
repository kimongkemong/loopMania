package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Staff 
 * A melee weapon with very low stats (lower than both the sword and stake),
 *  which has a random chance of inflicting a trance, 
 *  which transforms the attacked enemy into an allied soldier temporarily (and fights alongside the Character).
 */
public class Staff extends Weapon {

    /**
     * Staff can be instantiated with a specified drop coords, name, cost and sellPrice
     * @param x
     * @param y
     * @param name
     * @param cost
     * @param sellPrice
     */
    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y, String name,
            int cost, int sellPrice) {
        super(x, y, name, cost, sellPrice);
        this.setAttackDamage(2);
        this.setDurability(3);
    }
    /**
     * Instantiate with coords only.
     * @param x
     * @param y
     */
    public Staff(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Staff", 5, 2);
        this.setAttackDamage(2);
        this.setDurability(3);
    }
    
    /**
     * Returns the damage that will be dealt the enemy.
     */
    public int returnDamage(BasicEnemy enemy) {
        return this.getAttackDamage();
    }
}
