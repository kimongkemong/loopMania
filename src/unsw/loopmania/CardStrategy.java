package unsw.loopmania;

import java.util.List;

import org.javatuples.Pair;

import javafx.scene.image.ImageView;

public interface CardStrategy {
    //Getting the images of the cards
    ImageView getImage();
    // public boolean isCorrectPlacement(int buildingX, int buildingY, List<Pair<Integer, Integer>> orderedPath);

    default boolean isCorrectPlacement(int buildingX, int buildingY, List<Pair<Integer, Integer>> orderedPath) {
        Pair<Integer, Integer> buildingPair = new Pair<Integer, Integer>(buildingX, buildingY);
        if (orderedPath.contains(buildingPair)) {
            return true;
        }
        return false;
    }
    
    public Building createBuildingfromCard (int x, int y);
}
