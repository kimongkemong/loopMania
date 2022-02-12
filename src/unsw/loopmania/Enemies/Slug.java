package unsw.loopmania.Enemies;

import unsw.loopmania.BasicEnemy;
import unsw.loopmania.PathPosition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

/**The most occuring basic enemy a slug*/
public class Slug extends BasicEnemy    {
    
    private static final Integer BASE_DAMAGE_SLUG = 5;
    private static final Integer BASE_HEALTH_SLUG = 30;
    private static final Integer BASE_XP_DROP_SLUG = 100; 
    private static final Integer BASE_GOLD_DROP_SLUG = 1;

    /**
     * A standard enemy type. Low health and low damage.
     * The battle radius is the same as the support radius for a slug.
     * Spawns randomly on path tiles
     * @param position
     */
    public Slug(PathPosition position) {
        super(position, BASE_HEALTH_SLUG, BASE_DAMAGE_SLUG, BASE_XP_DROP_SLUG, BASE_GOLD_DROP_SLUG);
        this.setBattleRadius(1);
        this.setSupportRadius(2);
    }

    @Override
    public ImageView getImage() {
        Image slugImage = new Image((new File("src/images/slug.png")).toURI().toString());
        ImageView view = new ImageView(slugImage);
        return view;
    }
    
}
