package unsw.loopmania.Buildings;

import java.io.File;
import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import unsw.loopmania.Ally;
import unsw.loopmania.Building;
import unsw.loopmania.MovingEntity;


public class Barracks extends Building {

    public Barracks(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     *  Barracks Produces allied soldier to join Character in battles 
     * with enemies.
     * @param allied
     * @param entity
     * @param width
     */
    public void spawnAllied(List<Ally> allied, MovingEntity entity, Pair<Integer, Integer> pair) {
        //Produces allied soldier to join Character
        if (buildingRadius(entity) && allied.size() < 8) {
            //directly positioned the soldier in the grid position
            Ally aSoldier = new Ally(new SimpleIntegerProperty(pair.getValue0()), 
                                    new SimpleIntegerProperty(pair.getValue1()));
            allied.add(aSoldier);
        }
        
    }

    /**
     * Barracks must be step over by character to Spawn Allied Soldier
     * @param c
     */
    public boolean buildingRadius(MovingEntity c) {
        //when passing through
        if (c.getX() == this.getX() && c.getY() == this.getY()) {
            return true;
        } 
        return false;
        
    }

    
    /** 
     * Gets the Image asset for the frontend
     * @return ImageView
     */
    public ImageView getImage() {
        Image barracksBuildImage = new Image((new File("src/images/barracks.png")).toURI().toString());        
        ImageView view = new ImageView(barracksBuildImage);
        return view;
    }

}
