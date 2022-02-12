package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
//import javafx.util.Pair;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Staff;
import unsw.loopmania.Armour;
import unsw.loopmania.BasicEnemy;
import unsw.loopmania.Sword;
import unsw.loopmania.TreeStump;
import unsw.loopmania.Character;
import unsw.loopmania.Item;
import unsw.loopmania.Ally;
import unsw.loopmania.Anduril;
import unsw.loopmania.Battle;
import unsw.loopmania.Enemies.Doggie;
import unsw.loopmania.Enemies.ElanMuske;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;
import unsw.loopmania.Buildings.Tower;
import unsw.loopmania.Building;

public class BattleTest {
    
    @Test
    public void basicFightTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);

        // initialise slug enemy
        Slug slug = new Slug(pos);
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(slug);

        // make a battle occur between slug and character
        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        assertEquals(true, currBattle.battleWon());
        assertEquals(85, character.getHealth());
        assertEquals(100, character.getExp());
        assertEquals(1, character.getGold());
    }

    @Test
    public void basicFightTest2() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);

        // initialise zombie enemy
        Zombie zombie = new Zombie(pos);
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(zombie);

        // make a battle occur between zombie and character
        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        assertEquals(true, currBattle.battleWon());
        assertEquals(200, character.getExp());
        assertEquals(3, character.getGold());
    }

    @Test
    public void basicFightTest3() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);

        // initialise the vampire enemy
        Vampire vampire = new Vampire(pos);
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(vampire);

        // make a battle occur between vampire and character (character will lose)
        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        assertEquals(false, currBattle.battleWon());
        assertEquals(0, character.getExp());
        assertEquals(0, character.getGold());
    }

    @Test
    public void itemsFightTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>();

        // equip armour and sword on character
        Armour armour = new Armour(null, null);
        Sword sword = new Sword(null, null); 
        inventory.add(armour);
        inventory.add(sword);
        character.equipArmour(armour, inventory);
        character.equipWeapon(sword, inventory);

        // initialise Vampire
        Vampire vampire = new Vampire(pos);
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(vampire);

        // Now the character wins the battle since he has armour and sword equipped
        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        assertEquals(true, currBattle.battleWon());
        assertEquals(500, character.getExp());
        assertEquals(10, character.getGold());
    }

    @Test
    public void multipleEnemiesFightTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);

        // spawn 2 zombies
        Zombie zombie1 = new Zombie(pos);
        Zombie zombie2 = new Zombie(pos);  
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(zombie1);
        enemies.add(zombie2);

        // character will lose a battle against 2 zombies
        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        assertEquals(false, currBattle.battleWon());
    }

    @Test
    public void towerFightTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        // initialise a tower which will be in the fight
        Tower tower = new Tower(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        ArrayList<Building> buildings = new ArrayList<Building>();
        buildings.add(tower);

        // add 2 zombies
        Zombie zombie1 = new Zombie(pos);
        Zombie zombie2 = new Zombie(pos);  
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(zombie1);
        enemies.add(zombie2);

        // now character wins against 2 zombies since he has a tower helping him
        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, buildings);
        currBattle.beginBattle();
        assertEquals(true, currBattle.battleWon());
        assertEquals(400, character.getExp());
        assertEquals(6, character.getGold());
    }

    @Test
    public void allyFightTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        Ally ally = new Ally(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));

        // initialise an ally
        ArrayList<Ally> allies = new ArrayList<Ally>();
        allies.add(ally);

        // initialise 2 slugs
        Slug slug1 = new Slug(pos);
        Slug slug2 = new Slug(pos);
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(slug1);
        enemies.add(slug2);

        // character wins against both slugs with the help of the ally and gains
        // the correct amount of gold and exp
        Battle currBattle = new Battle(enemies, allies, character, new ArrayList<Building>());
        currBattle.beginBattle();
        assertEquals(true, currBattle.battleWon());
        assertEquals(2, character.getGold());
        assertEquals(200, character.getExp());
        
    }

    @Test
    public void convertAllyFightTest() {
         // initialise character and inventory
         List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
         orderedPath.add(new Pair<>(1,1));
         PathPosition pos = new PathPosition(0, orderedPath);
         Character character = new Character(pos);
         Ally ally = new Ally(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
 
         // initialise ally
         ArrayList<Ally> allies = new ArrayList<Ally>();
         allies.add(ally);

         // initialise 2 zombies
         Zombie zombie = new Zombie(pos);
         Zombie zombie2 = new Zombie(pos);
         ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
         enemies.add(zombie);
         // set zombie Random seed to ensure he crits and converts ally
         zombie.setRand(2);
         enemies.add(zombie2);


         Battle currBattle = new Battle(enemies, allies, character, new ArrayList<Building>());
         currBattle.beginBattle();

         // the ally will be converted to a zombie (known due to seed) and so the character will lose even though he would
         // win if he vsed them solo
         assertEquals(false, currBattle.battleWon());
         assertEquals(enemies.size(), 1);
    }

    @Test
    public void convertEnemyFightTest() {
         // initialise character and inventory
         List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
         orderedPath.add(new Pair<>(1,1));
         PathPosition pos = new PathPosition(0, orderedPath);
         Character character = new Character(pos);
         ArrayList<Item> inventory = new ArrayList<Item>(); 

         // equip a staff on the character and set seed to ensure he will put
         // the enemy in a trance
         Staff staff = new Staff(null, null);
         inventory.add(staff);
         character.equipWeapon(staff, inventory);
         character.setRand(6453);

         // initialise zombie
         Zombie zombie = new Zombie(pos);
         ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
         enemies.add(zombie);

         Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
         currBattle.beginBattle();

         // the enemy is converted to an ally on the 3rdd attack (due to the seed used) and so the character
         // only loses 30 health compared to losing 50 when vsing a zombie
         assertEquals(true, currBattle.battleWon());
         assertEquals(70, character.getHealth());
    }

    @Test
    public void towerAndAllyFightTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        // initialise ally
        Ally ally = new Ally(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        ArrayList<Ally> allies = new ArrayList<Ally>();
        allies.add(ally);
        
        // initialise new tower
        Tower tower = new Tower(new SimpleIntegerProperty(1), new SimpleIntegerProperty(1));
        ArrayList<Building> buildings = new ArrayList<Building>();
        buildings.add(tower);

        // initialise vampire
        Vampire vamp = new Vampire(pos);  
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(vamp);

        // character wins against vampire with the aid of the ally and tower
        Battle currBattle = new Battle(enemies, allies, character, buildings);
        currBattle.beginBattle();
        assertEquals(true, currBattle.battleWon());
        assertEquals(500, character.getExp());
        assertEquals(10, character.getGold());
    }

    @Test
    public void tranceEndsTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 

        // equip staff on character and set seed to ensure trance occurs
        Staff staff = new Staff(null, null);
        inventory.add(staff);
        character.equipWeapon(staff, inventory);
        character.setRand(3213);

        // initialise slugs
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        for (int i = 0; i < 7; i++) {
            enemies.add(new Slug(pos));
        }

        // battle occurs and a slug is put under a trance, but the trance ends
        // and the slug goes back to being an enemy
        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        assertEquals(true, currBattle.battleWon());
        assertEquals(700, character.getExp());
        assertEquals(7, character.getGold());
    }

    @Test
    public void rareItemTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>(); 

        // equipping rare items
        Anduril anduril = new Anduril(null, null);
        inventory.add(anduril);
        character.equipWeapon(anduril, inventory);
        TreeStump treeStump = new TreeStump(null, null);
        inventory.add(treeStump);
        character.equipShield(treeStump, inventory);

        // initialise slug
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        Slug slug = new Slug(pos);

        assertEquals(30, slug.getHealth());
        slug.attack(character);
        character.attack(slug);
        assertEquals(character.getHealth(), 95);

        // anduril only deals 15 extra damage (plus 10 base character damage) since slug isnt a boss
        assertEquals(slug.getHealth(), 5);
        for (int i = 0; i < 10; i++) {
            slug.attack(character);
        }
        // assert that character has blocked 1 hit and so is only hit 9 more times
        // and so only loses 45 more health (10% chance to block)
        assertEquals(character.getHealth(), 50);
    }

    @Test
    public void doggieTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        // initialise boss enemy, doggie
        Doggie doggie = new Doggie(pos);

        assertEquals(100, doggie.getHealth());
        for (int i = 0; i < 10; i++) {
            doggie.attack(character);
            character.attack(doggie);
            character.setStunned(false);
        }
        // assert that the doggie will stun the target 3 times (25% chance)
        // and so the doggie will only be hit 7 times
        assertEquals(character.getHealth(), 0);
        assertEquals(doggie.getHealth(), 30);
    }

    @Test
    public void bossFightWithRareItemsTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);
        ArrayList<Item> inventory = new ArrayList<Item>();
        // add rare items 
        Anduril anduril = new Anduril(null, null);
        inventory.add(anduril);
        character.equipWeapon(anduril, inventory);
        TreeStump treeStump = new TreeStump(null, null);
        inventory.add(treeStump);
        character.equipShield(treeStump, inventory);

        // add elan as enemy
        ElanMuske elan = new ElanMuske(pos);
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(elan);
        assertEquals(150, elan.getHealth());

        // character wins against Elan when using rare items
        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        assertEquals(true, currBattle.battleWon());
        assertEquals(character.getHealth(), 40);
        assertEquals(1000, character.getExp());
        assertEquals(20, character.getGold());
    }

    @Test
    public void elanTest() {
        // initialise character and inventory
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);

        // intialise boss enemy, ElanMuske
        ElanMuske elan = new ElanMuske(pos);
        ArrayList<BasicEnemy> enemies = new ArrayList<BasicEnemy>();
        enemies.add(elan);
        assertEquals(150, elan.getHealth());

        // Character loses against Elan
        Battle currBattle = new Battle(enemies, new ArrayList<Ally>(), character, new ArrayList<Building>());
        currBattle.beginBattle();
        assertEquals(false, currBattle.battleWon());
        assertEquals(elan.getHealth(), 110);
    }

    
}

