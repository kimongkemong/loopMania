package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Weapon is a subclass of Item, and consists of Sword, Staff and Stake items 
 */
public abstract class Weapon extends Item{
    private int attackDamage;
    
    /**
     * Any weapon can be instantiated with coordinates, name, cost and sellPrice 
     * @param x
     * @param y
     * @param name
     * @param cost
     * @param sellPrice
     */
    public Weapon(SimpleIntegerProperty x, SimpleIntegerProperty y, String name,
            int cost, int sellPrice) {
        super(x, y, name, cost, sellPrice);
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Returns the damage dealt to the specific enemy taking into account the weapons special
     * effects.
     * @param enemy
     * @return
     */
    public abstract int returnDamage(BasicEnemy enemy);

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

}
