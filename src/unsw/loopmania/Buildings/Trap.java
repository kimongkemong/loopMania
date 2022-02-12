package unsw.loopmania.Buildings;

import java.io.File;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Building;
import unsw.loopmania.MovingEntity;

/** Trap buildings can be used to damage and potentially kill enemies */
public class Trap extends Building {

    private static final Integer TRAP_DAMAGE = 20;
    private Integer damage = TRAP_DAMAGE;

    public Trap(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    public Integer getDamage() {
        return damage;
    }

    /**
     * When an enemy steps on a trap, the enemy is damaged 
     * (and potentially killed if it loses all health) and 
     * the trap is destroyed
     * @param enemies
     * @return
     */
    public boolean trapDamage(List<BasicEnemy> enemies) {
        for(BasicEnemy enemy : enemies){
            if (buildingRadius(enemy)) {
                enemy.reduceHealthBy(getDamage());
                if (enemy.getHealth() <= 0) {
                    enemy.destroy();
                    enemies.remove(enemy);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Enemy need to be pass through the building activate trap
     */
    @Override
    public boolean buildingRadius(MovingEntity entity) {
        //when passing through
        if (entity.getX() == this.getX() && entity.getY() == this.getY()) {
            return true;
        } 
        return false;
        
    }

    @Override
    public ImageView getImage() {
        Image trapBuildImage = new Image((new File("src/images/trap.png")).toURI().toString());        
        ImageView view = new ImageView(trapBuildImage);
        return view;
    }

}
