package unsw.loopmania.Enemies;

import unsw.loopmania.BasicAttack;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import unsw.loopmania.StunAttack;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Random;

/** Doggie is a special boss that spawns only after cycle 20, drops a doggieCoin which
 * can fluctuate.
 */
public class Doggie extends Boss {
    
    private static final Integer BASE_DAMAGE_DOGGIE = 10;
    private static final Integer BASE_HEALTH_DOGGIE = 100;
    private static final Integer BASE_XP_DROP_DOGGIE = 1000; 
    private static final Integer BASE_GOLD_DROP_DOGGIE = 0;
    private Random rand = new Random(4231);

    /**
     * A boss enemy type. High health and moderate damage but is able to stun character/ally.
     * The battle radius and the support radius is the same a Slug.
     * Spawns randomly on path tiles.
     * Drops a DoggieCoin when it dies.
     * @param position
     */
    public Doggie(PathPosition position) {
        super(position, BASE_HEALTH_DOGGIE, BASE_DAMAGE_DOGGIE, BASE_XP_DROP_DOGGIE, BASE_GOLD_DROP_DOGGIE);
        this.setBattleRadius(1);
        this.setSupportRadius(1);
    }

    
    /** 
     * @return ImageView
     */
    @Override
    public ImageView getImage() {
        Image doggieImage = new Image((new File("src/images/doggie.png")).toURI().toString());
        ImageView view = new ImageView(doggieImage);
        return view;
    }

    
    /** 
     * @param e
     */
    @Override
    public void attack(MovingEntity e) {
      if (rand.nextInt(4) == 0) {
        this.setAttack(new StunAttack());
      } else {
        this.setAttack(new BasicAttack());
      }
      getAttack().attack(this, e);
    } 
    
}