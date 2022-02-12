package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/** 
 * A class used to indicate the healthPoints of both the character and 
 * Enemy entities. 
 */
public class HealthPoints {

    private SimpleIntegerProperty healthPoints;

    public HealthPoints(SimpleIntegerProperty healthPoints) {
        this.healthPoints = healthPoints;
    }

    public SimpleIntegerProperty getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(SimpleIntegerProperty healthPoints) {
        this.healthPoints = healthPoints;
    }

    
    
}
