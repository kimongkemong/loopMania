package unsw.loopmania.Buildings;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.Building;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.Character;

/** The Castle Hero is the building in which a Hero must return to complete a cycle,
 * In addition a shop opens up ewhen a hero completes n cycles where n increase linearly.
 */
public class CastleHero extends Building {
    
    //After another 2 full cycles of the path, the Human Player will 
    //be able to access the Hero's castle again to purchase items, then again 
    //after another 3 full cycles, another after 4 cycle and so on.
    private int previousCycle = 0;
    private int counter = 2;

    public CastleHero(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * After another 2 full cycles of the path, the Human Player will 
     * be able to access the Hero's castle again to purchase items, then again 
     * after another 3 full cycles, another after 4 cycle and so on.        
     * 
     * Upon finishing the required number of cycles of the path completed by the Character, 
     * when the Character enters this castle, the Human Player is offered a window to purchase items
     * @param cycletraversed
     * @param character
     * @return
     */
    public boolean openShop(int cycletraversed, Character character) {

        int cyclediff = cycletraversed - previousCycle;
        if (buildingRadius(character) && cyclediff == counter) {
            previousCycle = cycletraversed;
            counter += 1;
            return true;
        }
        return false;
    }

    
    /** 
     * @param character
     * @param wild_blue
     * @return boolean
     */
    public boolean openShop(Character character, Boolean wild_blue) {
        if (buildingRadius(character) && wild_blue) {
            
            return true;
        }
        return false;
    }

    /**
     * Character must step over hero's castle to access heros' castle
     */
    @Override
    public boolean buildingRadius(MovingEntity movingEntity) {
        if (movingEntity.getX() == this.getX() && movingEntity.getY() == this.getY()) {
            return true;
        }
        return false;
    }

    
    /** 
     * @return ImageView
     */
    @Override
    public ImageView getImage() {
        Image castleHeroBuildImage = new Image((new File("src/images/heros_castle.png")).toURI().toString());        
        ImageView view = new ImageView(castleHeroBuildImage);
        return view;
    }

}
