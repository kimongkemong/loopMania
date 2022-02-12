package unsw.loopmania.Cards;

import java.io.File;
import java.util.List;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.Building;
import unsw.loopmania.Card;
import unsw.loopmania.Buildings.ZombiePit;

/** Zombie card used to spawn a zombie pit */
public class ZombiePitCard extends Card{
    
    public ZombiePitCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    } 

    public ImageView getImage(){
       Image zombiePitCardImage = new Image((new File("src/images/zombie_pit_card.png")).toURI().toString());
       ImageView view = new ImageView(zombiePitCardImage);
       return view;
    }

    /**
     * Creating the correct building from the Card
     */
    public Building createBuildingfromCard(int x, int y) {
        Building newBuilding = new ZombiePit(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        return newBuilding;
    }

    /**
     * Check if the postion of the building is correct as required
     */
    @Override
    public boolean isCorrectPlacement(int buildingX, int buildingY, List<Pair<Integer, Integer>> orderedPath) {
        Pair<Integer, Integer> buildingPair = new Pair<Integer, Integer>(buildingX, buildingY);
        if (getAdjacentToPath(orderedPath).contains(buildingPair)) {
            return true;
        }
        return false;
    }

}
