package unsw.loopmania.Enemies;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.Character;
import unsw.loopmania.BasicAttack;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.ConvertAttack;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.PathPosition;
import java.util.Random;

/** Zombies are stronger than slugs and can additionally critically damage allied soldiers */
public class Zombie extends BasicEnemy{

    private static final Integer BASE_DAMAGE_ZOMBIE = 10;
    private static final Integer BASE_HEALTH_ZOMBIE = 50;
    private static final Integer BASE_XP_DROP_ZOMBIE = 200;
    private static final Integer BASE_GOLD_DROP_ZOMBIE = 3;
    private Random rand = new Random(123);

    /**
     * Zombies have low health, moderate damage, and are slower compared to other enemies. 
     * A critical bite from a zombie against an allied soldier.
     * Zombies have a higher battle radius than slugs.
     * @param position
     */
    public Zombie(PathPosition position) {
        super(position, BASE_HEALTH_ZOMBIE, BASE_DAMAGE_ZOMBIE, BASE_XP_DROP_ZOMBIE, BASE_GOLD_DROP_ZOMBIE);
        this.setBattleRadius(2);
        this.setSupportRadius(3);
    }

    
    /** 
     * @param seed
     */
    public void setRand(int seed) {
      rand = new Random(seed);
    }
    /**
     * Decides which attack the zombie should use based on probability. Has a 1/40 chance to convert an allied soldier.
     * @param e - given moving entity
     */
    @Override
    public void attack(MovingEntity e) {
      if (rand.nextInt(40) == 0 && !(e instanceof Character)) {
        this.setAttack(new ConvertAttack());
      } else {
        this.setAttack(new BasicAttack());
      }
      getAttack().attack(this, e);
    }

    
    /** 
     * @return ImageView
     */
    @Override
    public ImageView getImage() {
        Image zombieImage = new Image((new File("src/images/zombie.png")).toURI().toString());
        ImageView view = new ImageView(zombieImage);
        return view;
    }
    
  
}
