package unsw.loopmania.Buildings;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.Building;
import unsw.loopmania.Item;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.HealthPotion;

/** The RedWildEntity will remove all healthpotions from inventory */
public class RedWildEntity extends Building {

    public RedWildEntity(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /** 
     * @return ImageView
     */
    @Override
    public ImageView getImage() {
        Image red_WildCardImage = new Image((new File("src/images/WildCard_Red.png")).toURI().toString());        
        ImageView view = new ImageView(red_WildCardImage);
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

    /**R emove all health potion in inventory once the character passed throguh redWildEntity
     * @precondition Card already positioned in the correct place
     */
    public void doAction(List<Item> items) {
        Iterator<Item> it = items.iterator();
        while(it.hasNext()) {
            Item item = it.next();
            if(item instanceof HealthPotion) {
                item.destroy();
                it.remove();
            }
        }
        this.destroy();
    }
    
}
