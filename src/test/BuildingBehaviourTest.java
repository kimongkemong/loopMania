package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.*;
import unsw.loopmania.Enemies.*;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Building;
import unsw.loopmania.Character;

public class BuildingBehaviourTest {

    @Test
    public void BarrackBuildingBehaviourTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        world.setCharacter(character);
        Building Building = new Barracks(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Barracks barracks = (Barracks) Building;
        barracks.spawnAllied(world.getAllies(),character, world.getAllySlot());
        assertEquals(1, world.getAllies().size());

    }
    
    @Test
    public void CampfireBuildingBehaviourTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        int maxPath = 6;
        for (int i = 0; i <= maxPath; i++) {
            orderedPath.add(new Pair<>(i,1));
        }
        PathPosition pos = new PathPosition(3, orderedPath);
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Character character = new Character(pos);
        world.setCharacter(character);
        Integer initialDamage = character.getDamage();
        Building Building = new Campfire(new SimpleIntegerProperty(2), new SimpleIntegerProperty(2));
        Campfire campfire= (Campfire) Building;
        campfire.campfireHeal(character);
        assertEquals(initialDamage * 2,character.getDamage());

        character.moveDownPath();
        campfire.campfireHeal(character);
        assertEquals(initialDamage,character.getDamage());

    }

    @Test
    public void TowerBuildingBehaviourTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Building building = new Tower(new SimpleIntegerProperty(1), new SimpleIntegerProperty(2));
        Tower tower = (Tower) building;
        BasicEnemy zombie = new Zombie(pos);
        BasicEnemy vampire = new Vampire(pos);
        world.getEnemies().add(zombie);
        world.getEnemies().add(vampire);
        Integer zInitialDamage = zombie.getHealth();
        Integer vInitialDamage = vampire.getHealth();
        tower.towerDamage(world.getEnemies());
        assertEquals(zInitialDamage - tower.getDamage(), zombie.getHealth());
        assertEquals(vInitialDamage - tower.getDamage(), vampire.getHealth());

    }
    @Test
    public void TrapBuildingBehaviourTest() {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        int maxPath = 6;
        for (int i = 0; i <= maxPath; i++) {
            orderedPath.add(new Pair<>(i,1));
        }
        PathPosition pos1 = new PathPosition(1, orderedPath);
        PathPosition pos2 = new PathPosition(5, orderedPath);

        BasicEnemy slug = new Slug(pos2);
        BasicEnemy vampire = new Vampire(pos1);

        Building building = new Trap(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Building building2 = new Trap(new SimpleIntegerProperty(5), new SimpleIntegerProperty(1));
        Trap trap = (Trap) building;
        Trap trap2 = (Trap) building2;  

        slug.reduceHealthBy(10);      
        Integer sInitialDamage = slug.getHealth();
        Integer vInitialDamage = vampire.getHealth();
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        world.getEnemies().add(slug);
        world.getEnemies().add(vampire);
        trap.trapDamage(world.getEnemies());
        trap2.trapDamage(world.getEnemies());

        assertEquals(sInitialDamage - trap2.getDamage(), slug.getHealth());
        assertFalse(world.getEnemies().contains(slug));
        assertEquals(vInitialDamage - trap.getDamage(), vampire.getHealth());
        
    }
    @Test
    public void VampireBuildingBehaviourTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);

        Building building = new VampireCastleBuilding(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));
        VampireCastleBuilding vampireCastle = (VampireCastleBuilding) building;

        vampireCastle.spawnVampire(5, world.getEnemySpawnPosition(vampireCastle.getX(), vampireCastle.getY()), world.getEnemies(), world.getupdateEnemy());
        int countVampire = 0;
        for (BasicEnemy v: world.getEnemies()) {
            if (v instanceof Vampire) {
                countVampire++;
            }
        }
        assertTrue(countVampire> 0);
    }
    
    @Test
    public void VillageBuildingBehaviourTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);        
        Character character = new Character(pos);
        world.setCharacter(character);

        Integer initialHealth = 50;
        world.getCharacter().setHealth(initialHealth);
        assertEquals(initialHealth, world.getCharacter().getHealth());
        Integer maxHealth = 100;
        Building building = new Village(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        Village village= (Village) building;
        village.villageHeal(character);
        assertEquals(maxHealth, world.getCharacter().getHealth());
    }
    @Test
    public void ZombieBuildingBehaviourTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);

        Building building = new ZombiePit(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1));
        ZombiePit zombiePit= (ZombiePit) building;

        zombiePit.spawnZombie(1, world.getEnemySpawnPosition(zombiePit.getX(), zombiePit.getY()), world.getEnemies(), world.getupdateEnemy());
        int count = 0;
        for (BasicEnemy v: world.getEnemies()) {
            if (v instanceof Zombie) {
                count++;
            }
        }
        assertTrue(count > 0);
    }

    @Test
    public void CastleHeroTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(0,0));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);

        Building building = new CastleHero(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        CastleHero castleHero = (CastleHero) building;

        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        world.setCharacter(character);
        assertFalse(castleHero.openShop(1, world.getCharacter()));
        assertTrue(castleHero.openShop(2, world.getCharacter()));
        assertFalse(castleHero.openShop(4, world.getCharacter()));
        assertTrue(castleHero.openShop(5, world.getCharacter()));
        assertTrue(castleHero.openShop(9, world.getCharacter()));

    }
}
