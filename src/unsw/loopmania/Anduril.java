package unsw.loopmania;

import unsw.loopmania.Enemies.Boss;
import javafx.beans.property.SimpleIntegerProperty;


public class Anduril extends Weapon {

    /**
     * Anduril is a raresword which is stronger against bosses. It can be
     * instantiated with coords, name, cost and sellprice.
     * @param x
     * @param y
     * @param name
     * @param cost
     * @param sellPrice
     */
    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y, String name,
            int cost, int sellPrice) {
        super(x, y, name, cost, sellPrice);
        this.setAttackDamage(15); 
        this.setDurability(10);
    }
    /**
     * Instantiate with coords only.
     * @param x
     * @param y
     */
    public Anduril(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "AndurilFlameoftheWest", 100, 50);
        this.setAttackDamage(15);
        this.setDurability(10);
    }
    
    /**
     * Returns the damage dealt based on the enemy being hit.
     * @param enemy
     */
    public int returnDamage(BasicEnemy enemy) {
        if (enemy instanceof Boss) {
            return this.getAttackDamage() * 3;
        }
        return this.getAttackDamage();
    }

}
