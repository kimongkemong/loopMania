package unsw.loopmania.ActionCards;

import java.io.File;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.Building;
import unsw.loopmania.Card;
import unsw.loopmania.Buildings.Removalist;

/**
 * The removalistCard can remove any currently postioned building 
 * mid game, drastically changing the experience
 */
public class RemovalistCard extends Card{

    public RemovalistCard(SimpleIntegerProperty x, SimpleIntegerProperty y) {
        super(x, y);
    }

    @Override
    public ImageView getImage() {
        Image RemovalistImage = new Image((new File("src/images/Removalist.png")).toURI().toString());        
        ImageView view = new ImageView(RemovalistImage);
        return view;
    }

    @Override
    public Building createBuildingfromCard(int x, int y) {
        return new Removalist(new SimpleIntegerProperty(x), new SimpleIntegerProperty(y));

    }
}
