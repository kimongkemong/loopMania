package test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Ally;
import unsw.loopmania.Character;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Buildings.Barracks;
import unsw.loopmania.Buildings.Campfire;
import unsw.loopmania.Buildings.Tower;
import unsw.loopmania.Buildings.Trap;
import unsw.loopmania.Buildings.VampireCastleBuilding;
import unsw.loopmania.Buildings.Village;
import unsw.loopmania.Buildings.ZombiePit;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;


public class LoopManiaWorldTests {

    /**
     * This test Checks the Functionality of the Cycle counter 
     * Creates an arbitrary length path and ensures that a cycle is counted
     */
    @Test
    public void LoopManiaWorldTestCount()   {

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < 20; i++)    {
            orderedPath.add(new Pair<>(i,i));
        }
        LoopManiaWorld tester = new LoopManiaWorld(5, 20, orderedPath);
        for (int i = 0; i <= (orderedPath.size()); i++)   {
            tester.updateCycleCounter();
        }
         // Check First cycle
        assertEquals(tester.getCycleCounter(), 1);
        

        for (int i = 0; i <= (orderedPath.size()); i++)   {
            tester.updateCycleCounter();
        }

        assertEquals(tester.getCycleCounter(), 2);
        assertEquals(tester.getStepsInCycle(), 0);
    }
    /**
     * This series of tests simulates that Battles are working by spawning in Slugs 
     */
    @Test
    public void LoopManiaWorldGridRunTest()    {
        // Create a 4 by 4 grid 
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < 5; i++)    {
            orderedPath.add(new Pair<>(i,0));
        }
        for (int j = 1; j < 5; j++)    {
            orderedPath.add(new Pair<>(4,j));
        }
        for (int i = 3; i >= 0 ;i--)    {
            orderedPath.add(new Pair<>(i,4));
        }
        for (int j = 3; j > 0; j--)    {
            orderedPath.add(new Pair<>(0,j));
        }

        LoopManiaWorld World = new LoopManiaWorld(4, 4, orderedPath);
        PathPosition herosPos = new PathPosition(0, orderedPath);
        Character John = new Character(herosPos);
        World.setCharacter(John);
        World.slugSpawn();

        while (John.getHealth() == 100) {
            World.runTickMoves();
            World.runBattles();   
        }
        assertNotEquals(John.getHealth(), 100);
        // Add a sword and unequip it 
        World.spawnCard();
        World.addItem("Sword", -1);
        World.removeUnequippedInventoryItemByCoordinates(0, 0);

    }

    /**
     * This test checks the functionality of the main function in LoopManiaWorld
     */
    @Test
    public void LoopManiaWorldMain()    {
        // Setup 
        // Create a 4 by 4 grid 
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < 5; i++)    {
            orderedPath.add(new Pair<>(i,0));
        }
        for (int j = 1; j < 5; j++)    {
            orderedPath.add(new Pair<>(4,j));
        }
        for (int i = 3; i >= 0 ;i--)    {
            orderedPath.add(new Pair<>(i,4));
        }
        for (int j = 3; j > 0; j--)    {
            orderedPath.add(new Pair<>(0,j));
        }
    }  

    /** This test checks the funcitonality of running battles and checks whether there has been a change in 
     * the state of the key items
     */
    @Test
    public void runBattlesTest()   {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < 5; i++)    {
            orderedPath.add(new Pair<>(i,i));
            orderedPath.add(new Pair<>(i+1,i));
        }
        LoopManiaWorld tester = new LoopManiaWorld(20, 20, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        tester.setCharacter(character);
        Zombie zomb = new Zombie(pos);
        Zombie zomb2 = new Zombie(pos);
        tester.addEnemies(zomb);
        tester.addEnemies(zomb2);
        Tower tower = new Tower(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        tester.addBuilding(tower); 
        tester.runTickMoves();
        tester.runBattles();
        assertFalse(character.getHealth() == 0);
        assertEquals(character.getGold(), 6);
        assertEquals(character.getExp(), 400);
        assertEquals(tester.getEnemies().size(), 0);
    }

    /** Tests to check if a tower is in range to attack  */
    @Test
    public void towerNotInRangeTest()   {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < 5; i++)    {
            orderedPath.add(new Pair<>(i,i));
            orderedPath.add(new Pair<>(i+1,i));
        }
        LoopManiaWorld tester = new LoopManiaWorld(20, 20, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        tester.setCharacter(character);
        Zombie zomb = new Zombie(pos);
        Zombie zomb2 = new Zombie(pos);
        tester.addEnemies(zomb);
        tester.addEnemies(zomb2);
        Tower tower = new Tower(new SimpleIntegerProperty(6), new SimpleIntegerProperty(0));
        tester.addBuilding(tower); 
        tester.runTickMoves();
        tester.runBattles();
        assertEquals(character.getHealth(), 0);
        assertEquals(character.getGold(), 6);
        assertEquals(character.getExp(), 400);
        assertEquals(tester.getEnemies().size(), 0);
    }

    /** Simple test to check if slugs are spawning correctly */
    @Test
    public void slugsSpawnTest()   {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < 5; i++)    {
            orderedPath.add(new Pair<>(i,i));
            orderedPath.add(new Pair<>(i+1,i));
        }
        LoopManiaWorld tester = new LoopManiaWorld(20, 20, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        tester.setCharacter(character);
        tester.slugSpawn();
        tester.slugSpawn();
        tester.slugSpawn();
        tester.slugSpawn();
        tester.slugSpawn();
        assertEquals(tester.getEnemies().size(), 0);
        tester.slugSpawn();
        // slug will spawn by chance (based on given seed)
        assertEquals(tester.getEnemies().size(), 1);
        
    }

    /** Tests to see if character passing a Barrack will increase allied soldiers spawned. */
    @Test
    public void barracksTest()   {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < 5; i++)    {
            orderedPath.add(new Pair<>(i,i));
            orderedPath.add(new Pair<>(i+1,i));
        }
        LoopManiaWorld tester = new LoopManiaWorld(20, 20, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        Barracks barracks = new Barracks(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        tester.addBuilding(barracks);
        tester.setCharacter(character);
        tester.runTickMoves();
        assertEquals(barracks.getY(), character.getY());
        assertEquals(barracks.getX(), character.getX());
        assertEquals(tester.getAllies().size(), 1);
    }

    /**
     * Tests whether campfires impact character damage. 
     */
    @Test
    public void campfireTest()   {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < 5; i++)    {
            orderedPath.add(new Pair<>(i,0));
        }
        LoopManiaWorld tester = new LoopManiaWorld(20, 20, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        Campfire campfire = new Campfire(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        tester.addBuilding(campfire);
        tester.setCharacter(character);
        character.setDamage(10);
        tester.runTickMoves();
        assertEquals(campfire.getY(), character.getY());
        assertEquals(campfire.getX(), character.getX());
        assertEquals(character.getDamage(), 20);
        tester.runTickMoves();
        // move outside the range of the campfire will reduce dmg back to original
        tester.runTickMoves();
        assertEquals(character.getDamage(), 10);

    }

    /**
     * This test checks trap spawning and whether slugs are spawned.
     */
    @Test
    public void trapTest()   {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < 5; i++)    {
            orderedPath.add(new Pair<>(i,0));
        }
        LoopManiaWorld tester = new LoopManiaWorld(20, 20, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        Trap trap = new Trap(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        tester.addBuilding(trap);
        tester.setCharacter(character);
        Slug slug = new Slug(pos);
        slug.setHealth(20);
        tester.addEnemies(slug);
        tester.runTickMoves();
        assertEquals(trap.getY(), slug.getY());
        assertEquals(trap.getX(), slug.getX());
        assertEquals(tester.getEnemies().size(), 0);
    }

    /**
     * This test checks whether a vampire castle can be added and whether or not it spawns Vampires.
     */
    @Test
    public void vampireBuildingTest()   {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < 5; i++)    {
            orderedPath.add(new Pair<>(i,0));
        }
        LoopManiaWorld tester = new LoopManiaWorld(20, 20, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        VampireCastleBuilding vampBuilding = new VampireCastleBuilding(new SimpleIntegerProperty(4), new SimpleIntegerProperty(1));
        tester.addBuilding(vampBuilding);
        tester.setCharacter(character);

        // vampire spawns after 5 cycles
        tester.setCycleCounter(5);
        tester.runTickMoves();
        assertEquals(tester.getEnemies().size(), 1);
        assert (tester.getEnemies().get(0) instanceof Vampire);
    }

    /**
     *  This test checks whether a Zombie building is able to spawn new Zombies.
     */
    @Test
    public void zombieBuildingTest()   {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < 5; i++)    {
            orderedPath.add(new Pair<>(i,0));
        }
        LoopManiaWorld tester = new LoopManiaWorld(20, 20, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ZombiePit zombPit = new ZombiePit(new SimpleIntegerProperty(4), new SimpleIntegerProperty(1));
        tester.addBuilding(zombPit);
        tester.setCharacter(character);

        // zombie spawns every cycle
        tester.setCycleCounter(1);
        tester.runTickMoves();
        assertEquals(tester.getEnemies().size(), 1);
        assert (tester.getEnemies().get(0) instanceof Zombie);
    }

    /**
     * This test checks whether a Village can be built and whether the characters health is 
     * restored upon passsing.
     */
    @Test
    public void villageBuildingTest()   {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < 5; i++)    {
            orderedPath.add(new Pair<>(i,0));
        }
        LoopManiaWorld tester = new LoopManiaWorld(20, 20, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        Village village = new Village(new SimpleIntegerProperty(1), new SimpleIntegerProperty(0));
        tester.addBuilding(village);
        tester.setCharacter(character);
        character.setHealth(50);
        tester.runTickMoves();
        assertEquals(village.getY(), character.getY());
        assertEquals(village.getX(), character.getX());
        assertEquals(character.getHealth(), 100);
    }

    /**
     * This test checkes whether items can be added to a characters inventory successfully
     */
    @Test
    public void AddItemsTest()  {
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        for (int i = 0; i < 5; i++)    {
            orderedPath.add(new Pair<>(i,0));
        }
        LoopManiaWorld tester = new LoopManiaWorld(20, 20, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);


        for (int i = 0; i < 2; i++) {
            tester.addItem("Sword", -1);
            tester.addItem("Shield", -1);
            tester.addItem("Staff", -1);
            tester.addItem("Stake", -1);
            tester.addItem("Helmet", -1);
            tester.addItem("Armour", -1);
            tester.addItem("Gold", -1);
            tester.addItem("HealthPotion", -1);
        }
        

        assertEquals(tester.getUnequippedInventory().size(), 14);
        
        
    }

}