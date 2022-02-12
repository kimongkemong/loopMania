package unsw.loopmania;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Enemies.Boss;
/**
 * Defends against enemy attacks, 
 * critical vampire attacks have a 60% lower chance of occurring
 */
public class Shield extends Item {

    private Random rand = new Random(412315);
    
    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y, String name,
            int cost, int sellPrice) {
        super(x, y, name, cost, sellPrice);
        this.setDurability(3);
    }

    public Shield(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "Shield", 7, 3);
        this.setDurability(3);
    }

    /**
     * Returns the damage dealt to the character when the shield is equipped.
     * @param damage
     * @param enemy
     * @return
     */
    public int reduceDamage(int damage, BasicEnemy enemy) {
        if (rand.nextInt(10) == 0) {
            return 0;
        }
        return damage;
    }
    
}
