package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * A standard melee weapon. Increases damage dealt by Character
 * represents an equipped or unequipped sword in the backend world
 */
public class Sword extends Weapon {

    /**
     * Specifiable instantiation of a Sword, takes in coordinates, name, 
     * cost and SellPrice
     * @param x
     * @param y
     * @param name
     * @param cost
     * @param sellPrice
     */
    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y, String name,
            int cost, int sellPrice) {
        super(x, y, name, cost, sellPrice);
        this.setAttackDamage(5);
        this.setDurability(3);
    }

    /**
     * Default instantiation of Sword 
     * @param x
     * @param y
     */
    public Sword(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Sword", 10, 5);
        this.setAttackDamage(5);
        this.setDurability(3);
    }
    /**
     * Getter for damage 
     */
    public int returnDamage(BasicEnemy enemy) {
        return this.getAttackDamage();
    }
    
}
