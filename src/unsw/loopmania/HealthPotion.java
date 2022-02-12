package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/** 
 * Consumable Item that restores a characters health to full
 */
public class HealthPotion extends Item{

    public HealthPotion(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "HealthPotion", 7, 0);
    }
    
}
