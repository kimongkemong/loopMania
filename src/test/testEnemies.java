package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import unsw.loopmania.BasicEnemy;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Vampire;
import unsw.loopmania.Enemies.Zombie;

import org.javatuples.Pair;


public class testEnemies {


    /**
     * This simple test spawn in an enemy and check that its support and battle radiues
     * are behaving as expected.
     */
    @Test
    public void testBasicEnemies()  {
         // Initialise a Path and positions for Enemy entities
         List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer,Integer>>();
         for (int i = 0; i < 10; i++)    {
             int j = (10);
             orderedPath.add(new Pair<>(i,j));
         }
        PathPosition basicPos = new PathPosition(0, orderedPath);
        BasicEnemy basicEnemy = new BasicEnemy(basicPos,100, 10, 20, 30);
        basicEnemy.move();
        basicEnemy.updateInRadius(basicPos);
        assertEquals(basicEnemy.getBattleRadius(), 0);
        assertEquals(basicEnemy.getSupportRadius(), 0);
    }

    /**
     * This test checks whether enemies can have their health reduced using the reduceHealth function
     */
    @Test
    public void reduceHealth()  {

        // Initialise a Path and positions for Enemy entities
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer,Integer>>();
        for (int i = 0; i < 10; i++)    {
            int j = (10);
            orderedPath.add(new Pair<>(i,j));
        }
        PathPosition zombiePos = new PathPosition(0, orderedPath);
        PathPosition vampPos = new PathPosition(1, orderedPath);
        PathPosition slugPos = new PathPosition(2, orderedPath);

        Slug Jabba = new Slug(slugPos);
        Zombie John = new Zombie(zombiePos);
        Vampire Dracula = new Vampire(vampPos);
        

        // Reduce all enemies health to 0 
        Jabba.reduceHealthBy(30);
        John.reduceHealthBy(50);
        Dracula.reduceHealthBy(70);        
        assertEquals(John.getHealth(), Jabba.getHealth());
        assertEquals(Jabba.getHealth(), Dracula.getHealth());

        Dracula.setHealth(100);
        Jabba.setHealth(100);
        John.setHealth(100);

        // test attack methods
        Dracula.attack(Jabba);
        Jabba.attack(John);
        John.attack(Dracula);
        
        assertNotEquals(Dracula.getHealth(), Jabba.getHealth());
    }
    
}

        



        


    