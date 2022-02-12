package unsw.loopmania;

import javafx.scene.image.ImageView;

public interface BuildingBehaviour {
    
    /**
     * Getting the image for GUI of building
     * @return
     */
    public ImageView getImage();
    public boolean buildingRadius(MovingEntity entity);
}
