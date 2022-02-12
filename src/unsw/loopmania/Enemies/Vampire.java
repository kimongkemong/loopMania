package unsw.loopmania.Enemies;

import java.util.Random;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import unsw.loopmania.BasicAttack;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.CritAttack;
import unsw.loopmania.PathPosition;
import unsw.loopmania.MovingEntity;
import unsw.loopmania.Character;

/** The Vampire is the strongest of the BasicEnemies */
public class Vampire extends BasicEnemy{

    private static final Integer BASE_DAMAGE_VAMPIRE = 30;
    private static final Integer BASE_HEALTH_VAMPIRE = 70;
    private static final Integer BASE_XP_DROP_VAMPIRE = 500;
    private static final Integer BASE_GOLD_DROP_VAMPIRE = 10;

    private int totalCritRounds = 0;
    private int critRound = 0;
    private int critdmg = 0;
    private Random rand = new Random(123);

    /**
     * Vampires have high damage,
     * They have a higher battle radius than slugs, and an even higher support radius. A critical bite
     * from a vampire causes random additional damage with every vampire attack, f
     * or a random number of vampire attacks.
     * 
     * @param position
     */
    public Vampire(PathPosition position) {
      super(position, BASE_HEALTH_VAMPIRE, BASE_DAMAGE_VAMPIRE, BASE_XP_DROP_VAMPIRE, BASE_GOLD_DROP_VAMPIRE);
      this.setBattleRadius(2);
      this.setSupportRadius(2);
    }

    
    /** 
     * @return int
     */
    public int getTotalDamage() {
      if (critRound < totalCritRounds) {
        return critdmg + getDamage();
      } 
      return getDamage();
    }

    
    /** 
     * @return int
     */
    public int getTotalCritRounds() {
      return totalCritRounds;
    }

    
    /** 
     * @param totalCritRounds
     */
    public void setTotalCritRounds(int totalCritRounds) {
      this.totalCritRounds = totalCritRounds;
    }

    
    /** 
     * @return int
     */
    public int getCritRound() {
      return critRound;
    }

    
    /** 
     * @param critRound
     */
    public void setCritRound(int critRound) {
      this.critRound = critRound;
    }

    
    /** 
     * @return int
     */
    public int getCritdmg() {
      return critdmg;
    }

    
    /** 
     * @param critdmg
     */
    public void setCritdmg(int critdmg) {
      this.critdmg = critdmg;
    }

    
    /** 
     * Attack the given entity 
     * @param e
     */
    @Override
    public void attack(MovingEntity e) {
      if (totalCritRounds == 0 && rand.nextInt(5) == 0) {
        if (e instanceof Character) {
          Character character = (Character) e;
          if (character.getShield() != null && (rand.nextInt(5) <= 2)) {
            this.setAttack(new BasicAttack());
          } else {
            this.setAttack(new CritAttack());
          }
        } else {
          this.setAttack(new CritAttack());
        }
      } else if (critRound < totalCritRounds) {
        this.setAttack(new CritAttack());
      } else {
        this.setAttack(new BasicAttack());
      }
      getAttack().attack(this, e);
      critRound += 1;
      if (critRound >= totalCritRounds) {
        critdmg = 0;
        critRound = 0;
        totalCritRounds = 0;
      }
    }

    
    /** 
     * @return ImageView
     */
    @Override
    public ImageView getImage() {
        Image vampireImage = new Image((new File("src/images/vampire.png")).toURI().toString());
        ImageView view = new ImageView(vampireImage);
        return view;
    }

    
    
}
