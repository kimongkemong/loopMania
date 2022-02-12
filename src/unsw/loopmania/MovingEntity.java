package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * The moving entity
 */
public abstract class MovingEntity extends Entity {

    
    /**
     * object holding position in the path
     */
    private PathPosition position;
    protected Integer health;
    private Integer damage;
    private Attacks attack;
    private Boolean stunned = false;

    /**
     * Create a moving entity which moves up and down the path 
     * @param position represents the current position in the path
     * @param damage represents the damage allocated to the entity
     * @param health health represent the health allocated to the entity
     */
    public MovingEntity(PathPosition position)  {
        super();
        this.position = position;
        // this.health = health;
        // this.damage = damage;
    }

    /**
     * move clockwise through the path
     */
    public void moveDownPath() {
        position.moveDownPath();
    }

    /**
     * move anticlockwise through the path
     */
    public void moveUpPath() {
        position.moveUpPath();
    }

    public SimpleIntegerProperty x() {
        return position.getX();
    }

    public SimpleIntegerProperty y() {
        return position.getY();
    }

    public int getX() {
        return x().get();
    }

    public int getY() {
        return y().get();
    }

    public Integer getDamage()  {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public PathPosition getPosition() {
        return position;
    }

    public void attack(MovingEntity e) {
        attack.attack(this, e);
    }

    public Attacks getAttack() {
        return attack;
    }
    
    public void setAttack(Attacks attack) {
        this.attack = attack;
    }

    public Boolean isStunned() {
        return stunned;
    }

    public void setStunned(Boolean stunned) {
        this.stunned = stunned;
    }

    /**
     * Takes in an Integer @damageRecieved and reduces the entity by the given damage
     * @param damageRecieved 
     */
    public void reduceHealthBy(int damageRecieved)  {
        this.health -= damageRecieved;
    }

}

