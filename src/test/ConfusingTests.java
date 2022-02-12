package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

//import javafx.util.Pair;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Helmet;
import unsw.loopmania.Ally;
import unsw.loopmania.Anduril;
import unsw.loopmania.Armour;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Battle;
import unsw.loopmania.Building;
import unsw.loopmania.Sword;
import unsw.loopmania.TheOneRing;
import unsw.loopmania.TreeStump;
import unsw.loopmania.Enemies.Doggie;
import unsw.loopmania.Enemies.ElanMuske;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.Stake;
import unsw.loopmania.Staff;
import unsw.loopmania.Shield;
import unsw.loopmania.Character;
import unsw.loopmania.Item;
import unsw.loopmania.Battle;

public class ConfusingTests {
    
    @Test
    public void confusedAndurilTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 
        assertEquals(0, inventory.size());

        // make character confused to simulate confusing mode
        character.setConfused(true);
        character.setRand(432131);
        
        // equip Anduril
        Anduril anduril = new Anduril(null, null);
        inventory.add(anduril);
        character.equipWeapon(anduril, inventory);
        // a confused shield is equipped due to the seed and confusing mode 
        assertNotEquals(character.getConfusedShield(), null);
        
        ElanMuske elan = new ElanMuske(pos);

        elan.attack(character);
        assertEquals(character.getHealth(), 70);
        // the confused shield will block the bosses ability since no shield is
        // equipped
        elan.attack(character);
        assertEquals(character.getHealth(), 70);
        elan.attack(character);
        assertEquals(character.getHealth(), 40);

        // anduril will cause 10 base damage + 15 * 3 extra damage since fighting a boss
        character.attack(elan);
        assertEquals(elan.getHealth(), 95);

    }

    @Test
    public void confusedAndurilTest2() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 
        assertEquals(0, inventory.size());

        // make character confused to simulate confusing mode
        character.setConfused(true);
        character.setRand(4325123);   

        // equip Anduril
        Anduril anduril = new Anduril(null, null);
        inventory.add(anduril);
        character.equipWeapon(anduril, inventory);
        // a confused ring is equipped due to seed and confusing mode
        assertNotEquals(character.getConfusedRing(), null);
        
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        Vampire vamp = new Vampire(pos);
        Vampire vamp2 = new Vampire(pos);
        enemies.add(vamp);
        enemies.add(vamp2);

        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        // character will only win if the confusedRing works and character
        // is revived otherwise character will lose
        assertEquals(currBattle.battleWon(), true);


    }

    @Test
    public void confusedTreeStumpTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 
        assertEquals(0, inventory.size());

        // make character confused to simulate confusing mode
        character.setConfused(true);
        character.setRand(4325123);
       
        // equip rareitem TreeStump
        TreeStump treeStump = new TreeStump(null, null);
        inventory.add(treeStump);
        character.equipShield(treeStump, inventory);
        // a confused ring is equipped due to seed and confusing mode
        assertNotEquals(character.getConfusedRing(), null);
        
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        Zombie zombie = new Zombie(pos);
        Zombie zombie2 = new Zombie(pos);
        Zombie zombie3 = new Zombie(pos);
        enemies.add(zombie);
        enemies.add(zombie2);
        enemies.add(zombie3);

        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        // character will only win if the confusedRing works and character
        // is revived otherwise character will lose
        assertEquals(currBattle.battleWon(), true);


    }
    
    @Test
    public void confusedTreeStumpTest2() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 
        assertEquals(0, inventory.size());
        
        // make character confused to simulate confusing mode
        character.setConfused(true);
        character.setRand(432131);

        // equip rareitem TreeStump
        TreeStump treeStump = new TreeStump(null, null);
        inventory.add(treeStump);
        character.equipShield(treeStump, inventory);
        // a confused Sword is equipped due to seed and confusing mode 
        assertNotEquals(character.getConfusedWeapon(), null);
        
        ElanMuske elan = new ElanMuske(pos);

        elan.attack(character);
        assertEquals(character.getHealth(), 70);
        // the confused shield will block the bosses ability since no shield is
        // equipped
        elan.attack(character);
        assertEquals(character.getHealth(), 70);
        elan.attack(character);
        assertEquals(character.getHealth(), 40);

        // anduril will cause 10 base damage + 15 * 3 extra damage since fighting a boss
        character.attack(elan);
        assertEquals(elan.getHealth(), 95);


    }

    @Test
    public void confusedTheOneRingTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 
        assertEquals(0, inventory.size());

        // make character confused to simulate confusing mode
        character.setConfused(true);
        character.setRand(432131);

        // equip rareitem TheOneRing
        TheOneRing theOneRing = new TheOneRing(null, null);
        inventory.add(theOneRing);
        character.equipRing(theOneRing);
        // a confused weapon is equipped due to seed and confusing mode
        assertNotEquals(character.getConfusedWeapon(), null);
        
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        Vampire vamp = new Vampire(pos);
        Vampire vamp2 = new Vampire(pos);
        enemies.add(vamp);
        enemies.add(vamp2);

        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        // character will only win if the confusedWeapon works and character
        // is dealing that extra weapon damage
        assertEquals(currBattle.battleWon(), true);


    }

    @Test
    public void confusedTheOneRingTest2() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 
        assertEquals(0, inventory.size());

        // make character confused to simualte confusing mode
        character.setConfused(true);
        character.setRand(4325123);
        
        // equip rareitem TheOneRing
        TheOneRing theOneRing = new TheOneRing(null, null);
        inventory.add(theOneRing);
        character.equipRing(theOneRing);
        // a confused shield is equipped due to seed and confusing mode
        assertNotEquals(character.getConfusedShield(), null);
        
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        Zombie zombie = new Zombie(pos);
        Zombie zombie2 = new Zombie(pos);
        Zombie zombie3 = new Zombie(pos);
        enemies.add(zombie);
        enemies.add(zombie2);
        enemies.add(zombie3);

        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        // character will only win if the confusedRing works and character
        // is revived otherwise character will lose
        assertEquals(currBattle.battleWon(), true);


    }
}

