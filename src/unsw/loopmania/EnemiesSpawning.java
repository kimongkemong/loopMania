package unsw.loopmania;

import java.util.List;

import org.javatuples.Pair;

public interface EnemiesSpawning {
    public Pair<Integer, Integer> spawnRadius();
    public List<BasicEnemy> spawnEnemies();
}
