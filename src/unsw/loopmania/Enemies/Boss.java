package unsw.loopmania.Enemies;

import unsw.loopmania.BasicEnemy;
import unsw.loopmania.PathPosition;

/** Basic Boss class used in Milestone 3 for Elon, and Doggie */
public class Boss extends BasicEnemy {
    
    public Boss(PathPosition position, int health, int damage, int XPdrop, int goldDrop) {
        super(position, health, damage, XPdrop, goldDrop);
    }
}
