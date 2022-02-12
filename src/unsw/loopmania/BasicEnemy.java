package unsw.loopmania;

import java.util.Random;

import javafx.scene.image.ImageView;

/**
 * a basic form of enemy in the world
 */
public class BasicEnemy extends MovingEntity {

    private int XPdrop;
    private int goldDrop;
    private boolean converted = false;
    private int battleRadius;
    private int supportRadius;
    private boolean inBattleRadius;
    private boolean inSupportRadius;
    private ImageView image;
    private Random rand = new Random(321);

    /**
     * Makes a BasicEnemy which the character can fight to gain items, gold and xp. Each
     * enemy has its own unique attacks and behaviours.
     * @param position
     */
    public BasicEnemy(PathPosition position) {
        super(position);
        this.XPdrop = 0;
        this.goldDrop = 0;
    }

    /**
     * Makes a BasicEnemy which the character can fight to gain items, gold and xp. Each
     * enemy has its own unique attacks and behaviours.
     * @param position
     * @param health
     * @param damage
     * @param XPdrop
     * @param goldDrop
     */
    public BasicEnemy(PathPosition position, int health, int damage, int XPdrop, int goldDrop)    {
        super(position);
        this.setHealth(health);
        this.setDamage(damage);
        this.XPdrop = XPdrop;
        this.goldDrop = goldDrop;
        this.setAttack(new BasicAttack());
    }

    public int getXPdrop() {
        return XPdrop;
    }


    public void setXPdrop(int xPdrop) {
        this.XPdrop = xPdrop;
    }

    public int getGoldDrop() {
        return goldDrop;
    }

    public void setGoldDrop(int goldDrop) {
        this.goldDrop = goldDrop;

    }   

    public boolean isConverted() {
        return converted;
    }

    public void setConverted(boolean converted) {
        this.converted = converted;
    }


    public int getBattleRadius() {
        return battleRadius;
    }

    public void setBattleRadius(int battleRadius) {
        this.battleRadius = battleRadius;
    }

    public int getSupportRadius() {
        return supportRadius;
    }

    public void setSupportRadius(int supportRadius) {
        this.supportRadius = supportRadius;
    }

    public boolean isInBattleRadius() {
        return inBattleRadius;
    }

    public void setInBattleRadius(boolean inBattleRadius) {
        this.inBattleRadius = inBattleRadius;
    }

    public boolean isInSupportRadius() {
        return inSupportRadius;
    }

    public void setInSupportRadius(boolean inSupportRadius) {
        this.inSupportRadius = inSupportRadius;
    }

    /**
     * Updates whether the character is within the battle radius and support radius of this enemy.
     * @param pos
     */
    public void updateInRadius(PathPosition pos) {
        if (pos.distance(this.getPosition()) <= (battleRadius * battleRadius)) {
            inBattleRadius = true;
        } else {
            setInBattleRadius(false);
        }
        if (pos.distance(this.getPosition()) <= (supportRadius * supportRadius)) {
            inSupportRadius = true;
        } else {
            setInSupportRadius(false);
        }
    }

    public ImageView getImage() {
        return image;
    }

    /**
     * Move the enemy.  
     */
    public void move(){
        // All enemies have a 1/3 chance of moving up, down or staying still on the spot.
        int directionChoice = rand.nextInt(3);
        if (directionChoice == 0){
            moveUpPath();
        }
        else if (directionChoice == 1){
            moveDownPath();
        }
    }
    
}
