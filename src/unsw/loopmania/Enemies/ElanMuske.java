package unsw.loopmania.Enemies;

import unsw.loopmania.PathPosition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

/** Elon Muske is a boss that spawns after round 40, has a passive ability to 
 * Sink the price of DoggieCoin.
 */
public class ElanMuske extends Boss    {
    
    private static final Integer BASE_DAMAGE_ELAN = 30;
    private static final Integer BASE_HEALTH_ELAN = 150;
    private static final Integer BASE_XP_DROP_ELAN = 1000; 
    private static final Integer BASE_GOLD_DROP_ELAN = 20;

    /**
     * A standard enemy type. Low health and low damage.
     * The battle radius is the same as the support radius for a ELAN.
     * Spawns randomly on path tiles
     * @param position
     */
    public ElanMuske(PathPosition position) {
        super(position, BASE_HEALTH_ELAN, BASE_DAMAGE_ELAN, BASE_XP_DROP_ELAN, BASE_GOLD_DROP_ELAN);
        this.setBattleRadius(1);
        this.setSupportRadius(1);
    }

    
    /** 
     * @return ImageView
     */
    @Override
    public ImageView getImage() {
        Image elanImage = new Image((new File("src/images/elan.png")).toURI().toString());
        ImageView view = new ImageView(elanImage);
        return view;
    }
    
}