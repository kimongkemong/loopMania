package unsw.loopmania;

import unsw.loopmania.Enemies.Vampire;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * A melee weapon with lower stats than the sword, but causes very high damage to vampires
 */
public class Stake extends Weapon{

    /**
     * Stake can be instantiated with coords, name, cost and sellPrice
     * @param x
     * @param y
     * @param name
     * @param cost
     * @param sellPrice
     */
    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y, String name, 
            int cost, int sellPrice) {
        super(x, y, name, cost, sellPrice);
        this.setAttackDamage(3);
        this.setDurability(3);
    }
    /**
     * Default - instantiated with just coordinates.
     * @param x
     * @param y
     */
    public Stake(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Stake", 7, 3);
        this.setAttackDamage(3);
        this.setDurability(3);
    }
    /**
     * Returns extra damage if fighting against a Vampire.
     */
    public int returnDamage(BasicEnemy enemy) {
        if (enemy instanceof Vampire) {
            return this.getAttackDamage() + 7;
        }
        return this.getAttackDamage();
    }
}
