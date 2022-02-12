package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Helmet is a superSet of Item and can be used to protect the character from damage from enemies.
 */
public class Helmet extends Item {

    /**
     * Helmet can be initialised with a specified cost, name, and sellPrice 
     * @param x
     * @param y
     * @param name
     * @param cost
     * @param sellPrice
     */
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y, String name,
            int cost, int sellPrice) {
        super(x, y, name, cost, sellPrice);
        this.setDurability(3);
    }

    /**
     * Specifies Helmet with default cost, name and sellPrice
     * @param x
     * @param y
     */
    public Helmet(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Helmet", 5, 2);
        this.setDurability(3);

    }

    /**
     * Returns the damage the character will take when wearing a helmet.
     * @param damage
     * @return
     */
    public int reduceDamage(int damage) {
        return damage - 4;
    }
    
}
