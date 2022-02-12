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
import unsw.loopmania.Enemies.Zombie;


public class ZombiePit extends Building {
    private Integer previousCycle = 0;

    public ZombiePit(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     * Produces zombies every one cycle of the path completed by the Character
     * spawning nearby on the path
     * @param cycle
     * @param possibleSpawnPosition
     * @param enemies
     * @param updateEnemy
     */
    public void spawnZombie(int cycle, PathPosition possibleSpawnPosition, List<BasicEnemy> enemies, List<BasicEnemy> updateEnemy) {
        if (previousCycle != cycle) {
            if (possibleSpawnPosition != null){
                BasicEnemy zomb = new Zombie(possibleSpawnPosition);
                enemies.add(zomb);
                updateEnemy.add(zomb);
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
        // spawning nearby
        return false;
    }

    
    /** 
     * This function gets the ImageView for a ZombiePit, for the frontend
     * @return ImageView
     */
    @Override
    public ImageView getImage() {
        Image zombiePitBuildImage = new Image((new File("src/images/zombie_pit.png")).toURI().toString());        
        ImageView view = new ImageView(zombiePitBuildImage);
        return view;
    }

}
