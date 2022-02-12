package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Armour extends Item {

    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y, String name,
            int cost, int sellPrice) {
        super(x, y, name, cost, sellPrice);
        this.setDurability(3);
    }
    
    public Armour(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Armour", 10, 5);
        this.setDurability(3);
    }

    /**
     * Returns the damage dealt to the character when equipped with a shield.
     * @param damage
     * @return
     */
    public int reduceDamage(int damage) {
        return damage/2;
    }
}
