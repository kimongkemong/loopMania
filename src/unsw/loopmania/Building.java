package unsw.loopmania;

import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Building extends StaticEntity implements BuildingBehaviour{

    public Building(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    /**
     * Update battle radius of each building that has building radius, such as
     * tower and campfire. 
     * @param entity
     * @param radius
     */
    public void updateBattleRad(StaticEntity entity, List<Pair<Integer, Integer>> radius) {
        for(int i = -1; i <= 1; i++) {
            for(int j = -1; j <= 1; j++) {
                Pair <Integer, Integer> pair = new Pair <Integer, Integer> (entity.getX() + i, entity.getY() + j);
                radius.add(pair);
            }
        }
    }

}
