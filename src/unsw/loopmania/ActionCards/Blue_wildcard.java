package unsw.loopmania.ActionCards;

import unsw.loopmania.Building;
import unsw.loopmania.Card;
import unsw.loopmania.Buildings.BlueWildEntity;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The Blue WildCard is a card used to create a random building
 */
public class Blue_wildcard extends Card {
    public Blue_wildcard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public ImageView getImage() {
        Image white_WildCardImage = new Image((new File("src/images/WildCard.png")).toURI().toString());        
        ImageView view = new ImageView(white_WildCardImage);
        return view;
    }

    @Override
    public Building createBuildingfromCard(int x, int y) {
        return new BlueWildEntity(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));
    }


}
