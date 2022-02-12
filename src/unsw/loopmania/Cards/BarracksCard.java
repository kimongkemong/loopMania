package unsw.loopmania.Cards;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.Building;
import unsw.loopmania.Card;
import unsw.loopmania.Buildings.Barracks;

/** Barracks card used to spawn in Barracks */
public class BarracksCard extends Card{
    public BarracksCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }
    
    public ImageView getImage(){
       Image barracksCardImage = new Image((new File("src/images/barracks_card.png")).toURI().toString());
       ImageView view = new ImageView(barracksCardImage);
       return view;
    }

    /**
     * Creating the correct building from the Card
     */
    public Building createBuildingfromCard(int x, int y) {
        Building newBuilding = new Barracks(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
        return newBuilding;
    }

}
