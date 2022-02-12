package unsw.loopmania.ActionCards;


import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.Building;
import unsw.loopmania.Card;

import unsw.loopmania.Buildings.RedWildEntity;

/**
 * The Red Wildcard is used to delete all potions from inventory.
 */
public class Red_wildcard extends Card {

    public Red_wildcard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public ImageView getImage() {
        Image Red_WildCardImage = new Image((new File("src/images/WildCard.png")).toURI().toString());        
        ImageView view = new ImageView(Red_WildCardImage);
        return view;
    }


    @Override
    public Building createBuildingfromCard(int x, int y) {
        return new RedWildEntity(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }
    
}
