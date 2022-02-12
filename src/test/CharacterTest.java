package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import unsw.loopmania.TreeStump;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.Stake;
import unsw.loopmania.Staff;
import unsw.loopmania.Shield;
import unsw.loopmania.Character;
import unsw.loopmania.Item;
import unsw.loopmania.Battle;

public class CharacterTest {
    
    @Test
    public void equipTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 
        assertEquals(0, inventory.size());

        // test equipping helmet
        Helmet testHelmet = new Helmet(null, null);
        inventory.add(testHelmet);
        assertEquals(1, inventory.size());
        assertEquals(null, character.getHelmet());
        character.equipHelmet(testHelmet, inventory);
        assertEquals(testHelmet, character.getHelmet());
        assertEquals(0, inventory.size());

        // test equipping armour
        Armour testArmour = new Armour(null, null);
        inventory.add(testArmour);
        assertEquals(1, inventory.size());
        assertEquals(null, character.getArmour());
        character.equipArmour(testArmour, inventory);
        assertEquals(testArmour, character.getArmour());
        assertEquals(0, inventory.size());

        // test equpping sword
        Sword testSword = new Sword(null, null);
        inventory.add(testSword);
        assertEquals(1, inventory.size());
        assertEquals(null, character.getWeapon());
        character.equipWeapon(testSword, inventory);
        assertEquals(testSword, character.getWeapon());
        assertEquals(0, inventory.size());

        // test equipping staff while a sword is already equipped
        Staff testStaff = new Staff(null, null);
        inventory.add(testStaff);
        assertEquals(1, inventory.size());
        character.equipWeapon(testStaff, inventory);
        assertEquals(testStaff, character.getWeapon());
        assertEquals(1, inventory.size());

        // test equipping a stake while a staff is already equipped
        Stake testStake = new Stake(null, null);
        inventory.add(testStake);
        assertEquals(2, inventory.size());
        character.equipWeapon(testStake, inventory);
        assertEquals(testStake, character.getWeapon());
        assertEquals(2, inventory.size());

        // test equipping a shield
        Shield testShield = new Shield(null, null);
        inventory.add(testShield);
        assertEquals(3, inventory.size());
        assertEquals(null, character.getShield());
        character.equipShield(testShield, inventory);
        assertEquals(testShield, character.getShield());
        assertEquals(2, inventory.size());
    }
    
    @Test
    public void unequipTest(){
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 
        assertEquals(0, inventory.size());

        // equip and unequip helmet
        Helmet testHelmet = new Helmet(null, null);
        inventory.add(testHelmet);
        character.equipHelmet(testHelmet, inventory);
        character.unequipHelmet(inventory);
        assertEquals(null, character.getHelmet());
        assertEquals(1, inventory.size());

        // equip and unequip armour
        Armour testArmour = new Armour(null, null);
        inventory.add(testArmour);
        character.equipArmour(testArmour, inventory);
        character.unequipArmour(inventory);
        assertEquals(null, character.getArmour());
        assertEquals(2, inventory.size());

        // equip and unequip sword
        Sword testSword = new Sword(null, null);
        inventory.add(testSword);
        character.equipWeapon(testSword, inventory);
        character.unequipWeapon(inventory);
        assertEquals(null, character.getWeapon());
        assertEquals(3, inventory.size());

        // equip and unequip staff
        Staff testStaff = new Staff(null, null);
        inventory.add(testStaff);
        character.equipWeapon(testStaff, inventory);
        character.unequipWeapon(inventory);
        assertEquals(null, character.getWeapon());
        assertEquals(4, inventory.size());

        // equip and unequip stake
        Stake testStake = new Stake(null, null);
        inventory.add(testStake);
        character.equipWeapon(testStake, inventory);
        character.unequipWeapon(inventory);
        assertEquals(null, character.getWeapon());
        assertEquals(5, inventory.size());

        // equip and unequip shield
        Shield testShield = new Shield(null, null);
        inventory.add(testShield);
        character.equipShield(testShield, inventory);
        character.unequipShield(inventory);
        assertEquals(null, character.getShield());
        assertEquals(6, inventory.size());
    }

    @Test
    public void doubleEquipTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 
        assertEquals(0, inventory.size());

        // test equipping helmet
        Helmet testHelmet = new Helmet(null, null);
        Helmet testHelmet2 = new Helmet(null, null);
        inventory.add(testHelmet);
        inventory.add(testHelmet2);
        character.equipHelmet(testHelmet, inventory);
        character.equipHelmet(testHelmet2, inventory);
        assertEquals(testHelmet2, character.getHelmet());

        // test equipping armour
        Armour testArmour = new Armour(null, null);
        Armour testArmour2 = new Armour(null, null);
        inventory.add(testArmour);
        inventory.add(testArmour2);
        character.equipArmour(testArmour, inventory);
        character.equipArmour(testArmour2, inventory);
        assertEquals(testArmour2, character.getArmour());

        // test equpping sword
        Sword testSword = new Sword(null, null);
        Sword testSword2 = new Sword(null, null);
        inventory.add(testSword);
        inventory.add(testSword2);
        character.equipWeapon(testSword, inventory);
        character.equipWeapon(testSword2, inventory);
        assertEquals(testSword2, character.getWeapon());

        // test equipping a shield
        Shield testShield = new Shield(null, null);
        Shield testShield2 = new Shield(null, null);
        inventory.add(testShield);
        inventory.add(testShield2);
        character.equipShield(testShield, inventory);
        character.equipShield(testShield2, inventory);
        assertEquals(testShield2, character.getShield());
    }

    @Test
    public void setHealthTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 

        Helmet testHelmet = new Helmet(null, null);
        inventory.add(testHelmet);
        character.equipHelmet(testHelmet, inventory);
         Armour testArmour = new Armour(null, null);
        inventory.add(testArmour);
        character.equipArmour(testArmour, inventory);
        Sword testSword = new Sword(null, null);
        inventory.add(testSword);
        character.equipWeapon(testSword, inventory);
        Shield testShield = new Shield(null, null);
        inventory.add(testShield);
        character.equipShield(testShield, inventory);
        
        // check if set health works
        character.setHealth(50);
        assertEquals(character.getHealth(), 50);

        character.setHealth(100);
        Zombie zomb = new Zombie(pos);
        // seting the characters health from 100 to 50, but since he has
        // items on the damage is reduced (note this is a different set
        // health function than the previous one)
        character.setHealth(50, zomb);
        assertEquals(character.getHealth(), 79);

        // shield blocks damage this time due to probability (due to the seed)
        character.setHealth(50, zomb);
        assertEquals(character.getHealth(), 79);

    }

    @Test
    public void durabilityTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 

        Helmet testHelmet = new Helmet(null, null);
        inventory.add(testHelmet);
        character.equipHelmet(testHelmet, inventory);
         Armour testArmour = new Armour(null, null);
        inventory.add(testArmour);
        character.equipArmour(testArmour, inventory);
        Sword testSword = new Sword(null, null);
        inventory.add(testSword);
        character.equipWeapon(testSword, inventory);
        Shield testShield = new Shield(null, null);
        inventory.add(testShield);
        character.equipShield(testShield, inventory);
        // equipment will still be there after 2 battles
        for (int i = 0; i < 2; i++) {
            Slug slug = new Slug(pos);
            ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
            enemies.add(slug);
            Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
            currBattle.beginBattle();
            assertEquals(testHelmet, character.getHelmet());
            assertEquals(testArmour, character.getArmour());
            assertEquals(testShield, character.getShield());
            assertEquals(testSword, character.getWeapon());
        }
        // equipment will break on 3rd battle due to durability
        Slug slug = new Slug(pos);
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(slug);
        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        assertEquals(null, character.getHelmet());
        assertEquals(null, character.getArmour());
        assertEquals(null, character.getShield());
        assertEquals(null, character.getWeapon());

    }

    @Test
    public void durabilityTest2() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 

        Staff testStaff = new Staff(null, null);
        inventory.add(testStaff);
        character.equipWeapon(testStaff, inventory);

        // equipment will still be there after 2 battles
        for (int i = 0; i < 2; i++) {
            Slug slug = new Slug(pos);
            ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
            enemies.add(slug);
            Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
            currBattle.beginBattle();
            assertEquals(testStaff, character.getWeapon());
        }
        // equipment will break on 3rd battle due to durability
        Slug slug = new Slug(pos);
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(slug);
        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        assertEquals(null, character.getWeapon());

    }

    @Test
    public void durabilityTest3() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 

        Staff testStake = new Staff(null, null);
        inventory.add(testStake);
        character.equipWeapon(testStake, inventory);

        // equipment will still be there after 2 battles
        for (int i = 0; i < 2; i++) {
            Slug slug = new Slug(pos);
            ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
            enemies.add(slug);
            Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
            currBattle.beginBattle();
            assertEquals(testStake, character.getWeapon());
        }
        // equipment will break on 3rd battle due to durability
        Slug slug = new Slug(pos);
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(slug);
        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        assertEquals(null, character.getWeapon());

    }

    @Test
    public void durabilityRareItemTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 

        Anduril anduril = new Anduril(null, null);
        inventory.add(anduril);
        character.equipWeapon(anduril, inventory);
        TreeStump treeStump = new TreeStump(null, null);
        inventory.add(treeStump);
        character.equipShield(treeStump, inventory);


        // equipment will still be there after 2 battles
        for (int i = 0; i < 9; i++) {
            Slug slug = new Slug(pos);
            ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
            enemies.add(slug);
            Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
            currBattle.beginBattle();
            assertEquals(anduril, character.getWeapon());
            assertEquals(treeStump, character.getShield());
        }
        // equipment will break on 3rd battle due to durability
        Slug slug = new Slug(pos);
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(slug);
        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        assertEquals(null, character.getWeapon());
        assertEquals(null, character.getShield());

    }

    




}

