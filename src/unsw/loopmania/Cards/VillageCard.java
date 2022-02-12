package unsw.loopmania.Cards;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.Building;
import unsw.loopmania.Card;
import unsw.loopmania.Buildings.Village;

/** The VillageCard is used to spawn in a Village  */
public class VillageCard extends Card{
    
    public VillageCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    } 

    public ImageView getImage(){
       Image villageCardImage = new Image((new File("src/images/village_card.png")).toURI().toString());
       ImageView view = new ImageView(villageCardImage);
       return view;
    }

    /**
     * Creating the correct building from the Card
     */
    public Building createBuildingfromCard(int x, int y) {
        Building newBuilding = new Village(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        return newBuilding;
    }
    
    // /**
    //  * Check if the postion of the building is correct as required
    //  */
    // public boolean isCorrectPlacement(int buildingX, int buildingY, List<Pair<Integer, Integer>> orderedPath) {
    //     Pair<Integer, Integer> buildingPair = new Pair<Integer, Integer>(buildingX, buildingY);
    //     if (orderedPath.contains(buildingPair)) {
    //         return true;
    //     }
    //     return false;
    // }

}