package unsw.loopmania;

import unsw.loopmania.Item;
import unsw.loopmania.Enemies.Boss;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;

public class TreeStump extends Shield {

    private Random rand = new Random(412315);

    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y, String name,
        int cost, int sellPrice) {
        super(x, y, name, cost, sellPrice);
        this.setDurability(10);
    }

    public TreeStump(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "TreeStump", 10, 5);
        this.setDurability(10);
    }

    /**
     * Returns the damage dealt to the character when this shield is equipped.
     * This shield has a higher chance of blocking attacks from bosses.
     * @param damage
     * @param enemy
     */
    @Override
    public int reduceDamage(int damage, BasicEnemy enemy) {
        // higher chance to block if the enemy is a boss
        if (enemy instanceof Boss) {
            if (rand.nextInt(4) == 0) {
                return 0;
            } 
        } else if (rand.nextInt(10) == 0) {
            return 0;
        }
        return damage;  
    }
}
