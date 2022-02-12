package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * The one Ring is a specifiable rare Item with an extremely low dropRate
 */
public class TheOneRing extends Item {

    /**
     * The one Wring is always instantiated with its coords, name, cost and sellPrice
     * @param x
     * @param y
     * @param name
     * @param cost
     * @param sellPrice
     */
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y, String name,
            int cost, int sellPrice) {
        super(x, y, name, cost, sellPrice);
        this.setDurability(10);
    }



     /**
     * Default instantiation of Sword 
     * @param x
     * @param y
     */
    public TheOneRing(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "TheOneRing", 100, 5);
    }
    
}
