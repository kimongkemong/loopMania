package unsw.loopmania.Buildings;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import unsw.loopmania.Building;
import unsw.loopmania.MovingEntity;

/** The Removalist is used to restore land to its original state 
 *  and free it of any buildings.
 */
public class Removalist extends Building {

    public Removalist(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    
    /** 
     * @return ImageView
     */
    @Override
    public ImageView getImage() {
        Image removalistImage = new Image((new File("src/images/Removalist.png")).toURI().toString());        
        ImageView view = new ImageView(removalistImage);
        return view;
    }

    
    /** 
     * @param entity
     * @return boolean
     */
    @Override
    public boolean buildingRadius(MovingEntity entity) {
        return true;
    }

    /**
     * Remove the building below the entity removalist
     * @param buildings
     */
    public void doAction(List<Building> buildings) {
        Iterator<Building> build = buildings.iterator();
        while (build.hasNext()) {
            Building b = build.next();
            if (b instanceof Removalist) {
                continue;
            }
            if(this.getX() == b.getX() && this.getY() == b.getY()){
                b.destroy();
                build.remove();
            }
        }
    }
    
}
