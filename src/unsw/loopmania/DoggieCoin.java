package unsw.loopmania;

import java.util.Random;

import javafx.beans.property.SimpleIntegerProperty;
public class DoggieCoin extends Item {

    public DoggieCoin(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y, "DoggieCoin", 0, 100);
    }   


    /**
     * This method randomised the DogeCoin price
     */
    public void priceUpdate()   {
        Random DogeToTheMoon = new Random();
        this.setSellPrice(DogeToTheMoon.nextInt(100));
    }

    /**
     * Increase the price of doggiecoin by some multiple.
     * @param multiplier
     */
    public void increasePrice(int multiplier) {
        this.setSellPrice(this.getSellPrice() * multiplier);
    }

    /**
     * Decrease the price of doggiecoin by some multiple
     * @param multiplier
     */
    public void decreasePrice(int multiplier) {
        this.setSellPrice(this.getSellPrice() / multiplier);
    }

}
