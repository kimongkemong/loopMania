package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

public class Ally extends StaticEntity{

    private final static Integer STARTING_HEALTH = 100;
    private final static Integer STARTING_DMG = 10;

    private Integer damage;
    private Integer healthPoints;

    /**
     * Ally are friendly solider which assist the Character in battles. They
     * utilise a BasicAttack and are received by the human player when the 
     * character passes through the barracks.
     * @param SimpleIntegerProperty x - x coordinate
     * @param SingleIntegerProperty y - y coordinate
     */
    public Ally(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
        this.damage = STARTING_DMG;
        this.healthPoints = STARTING_HEALTH;
    }

    public Integer getDamage() {
        return damage;
    }

    public Integer getHealthPoints() {
        return healthPoints;
    }

    
}
