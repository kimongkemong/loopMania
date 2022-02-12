package unsw.loopmania.Buildings;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.Building;
import unsw.loopmania.MovingEntity;

/** The Blue wild entity spawns a random card on the selected slot */
public class BlueWildEntity extends Building {

    public BlueWildEntity(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    
    /** 
     * @return ImageView
     */
    @Override
    public ImageView getImage() {
        Image white_WildCardImage = new Image((new File("src/images/WildCard_blue.png")).toURI().toString());        
        ImageView view = new ImageView(white_WildCardImage);
        return view;
    }

    
    /** 
     * @param entity
     * @return boolean
     */
    @Override
    public boolean buildingRadius(MovingEntity entity) {
        if (entity.getX() == this.getX() && entity.getY() == this.getY()) {
            return true;
        } 
        return false;
    }

    public void doAction() {
        this.destroy();
    }    
}
