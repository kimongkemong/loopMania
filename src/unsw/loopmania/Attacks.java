package unsw.loopmania;

public interface Attacks {
    /**
     * Interface for attacks done by entity1 on entity2.
     * @param e1
     * @param e2
     */
    void attack(MovingEntity e1, MovingEntity e2);
}
