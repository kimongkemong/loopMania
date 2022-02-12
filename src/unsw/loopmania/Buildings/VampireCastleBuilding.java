package unsw.loopmania.Buildings;

import java.io.File;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Building;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.Vampire;


/**
 * a basic form of building in the world
 */
public class VampireCastleBuilding extends Building {
    private Integer previousCycle = 0;

    public VampireCastleBuilding(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);

    }
    /**
     * Produces vampires every 5 cycles of the path completed by the Character,
     * spawning nearby on the path
     * @param cycle
     * @param possibleSpawnPosition
     * @param enemies
     * @param updateEnemy
     */
    public void spawnVampire(int cycle, PathPosition possibleSpawnPosition, List<BasicEnemy> enemies, List<BasicEnemy> updateEnemy) {
        if (previousCycle != cycle) {
            if (cycle % 5 == 0) {
                if (possibleSpawnPosition != null){
                    BasicEnemy vamp = new Vampire(possibleSpawnPosition);
                    enemies.add(vamp);
                    updateEnemy.add(vamp);
                }
            } 
            previousCycle = cycle;
        }      
    }

    
    /** 
     * @param movingEntity
     * @return boolean
     */
    @Override
    public boolean buildingRadius(MovingEntity movingEntity) {
        return true;
    }

    
    /** Getter function to retrieve Vampire castle image 
     * @return ImageView
     */
    @Override
    public ImageView getImage() {
        Image vampireBuildImage = new Image((new File("src/images/vampire_castle_building_purple_background.png")).toURI().toString());        
        ImageView view = new ImageView(vampireBuildImage);
        return view;
    }

}
