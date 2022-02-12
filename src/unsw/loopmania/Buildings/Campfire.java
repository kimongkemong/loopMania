package unsw.loopmania.Buildings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.Building;
import unsw.loopmania.Character;
import unsw.loopmania.MovingEntity;

import org.javatuples.Pair;

/** The campfire modifies the characters damage while the character is in range*/
public class Campfire extends Building{

    /**
     * List of campfire battleRadius 
     */
    private List<Pair<Integer, Integer>> battleRad = new ArrayList<>();
    private boolean doubled = false;

    public Campfire(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    /**
     * Character deals double damage within campfire battle radius
     * @param entity
     */
    public void campfireHeal(Character entity) {
        if (!doubled && buildingRadius(entity)) {
            entity.setDamage(entity.getDamage() * 2);
            doubled = true;
        } 
        if (doubled && !buildingRadius(entity)) {
            doubled = false;
            entity.setDamage(entity.getDamage()/2);
        }
    }

    /**
     * Check if Character is under campFire battleRadius
     */
    public boolean buildingRadius(MovingEntity entity) {
        updateBattleRad(this, battleRad);
        Character c = (Character) entity;
        Pair<Integer, Integer> charCurrPos = new Pair <Integer, Integer> (c.getX(),c.getY());
        for (Pair<Integer, Integer> pair: battleRad) {
            if (pair.equals(charCurrPos)) {
                return true;
            }
        }
        return false;
    }
    
    /** 
     * @return ImageView
     */
    @Override
    public ImageView getImage() {
        Image campfireBuildImage = new Image((new File("src/images/campfire.png")).toURI().toString());        
        ImageView view = new ImageView(campfireBuildImage);
        return view;
    }

}
