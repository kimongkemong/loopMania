package unsw.loopmania.Buildings;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.Building;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.Character;



public class Village extends Building {
    
    public Village(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Character regains full health when passing through village
     * @param entity
     */
    public void villageHeal(Character entity) {
        // Character regains health 
        if (buildingRadius(entity)) {
            entity.setHealth(100);
        }
        
    }

    /**
     * Character needs to pass through village to activate village
     */
    public boolean buildingRadius(MovingEntity entity) {
        if (entity.getX() == this.getX() && entity.getY() == this.getY()) {
            return true;
        } 
        return false;
    }

    
    /** 
     * Getter function for frontend to load village image
     * @return ImageView
     */
    @Override
    public ImageView getImage() {
        Image villageBuildImage = new Image((new File("src/images/village.png")).toURI().toString());        
        ImageView view = new ImageView(villageBuildImage);
        return view;
    }

}
