package unsw.loopmania.Buildings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Building;
import unsw.loopmania.MovingEntity;

import org.javatuples.Pair;

/** Towers can be used to attack enemy units during a battle */
public class Tower extends Building{

    private List<Pair<Integer, Integer>> battleRad = new ArrayList<>();
    
    private static final Integer TOWER_DAMAGE = 8;
    private Integer damage = TOWER_DAMAGE;

    public Tower(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    /**
     *  During a battle within its shooting radius, enemies will be attacked by the tower
     * only if battle between character is happening
     * @param enemies
     */
    public void towerDamage(List<BasicEnemy> enemies) {
        for(BasicEnemy enemy : enemies) {
            if (buildingRadius(enemy)) {
                enemy.reduceHealthBy(getDamage());
            }
        }
    }

    
    /** 
     * @param enemy
     */
    public void towerDamage(BasicEnemy enemy) {
        enemy.reduceHealthBy(getDamage());
    }

    
    /** 
     * @return Integer
     */
    public Integer getDamage() {
        return damage;
    }

    /**
     * Character must be under Tower's radius for tower to attack enemy 
     */
    @Override
    public boolean buildingRadius(MovingEntity movingEntity) {
        updateBattleRad(this, battleRad);
        Pair<Integer, Integer> entityCurrPos = new Pair <Integer, Integer> (movingEntity.getX(),movingEntity.getY());
        for (Pair<Integer, Integer> pair: battleRad) {
            if (pair.equals(entityCurrPos)) {
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
        Image towerBuildImage = new Image((new File("src/images/tower.png")).toURI().toString());        
        ImageView view = new ImageView(towerBuildImage);
        return view;
    }
   
}
