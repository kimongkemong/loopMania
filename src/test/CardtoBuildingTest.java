package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.Cards.*;
import unsw.loopmania.Card;

/**
 * Test for the placing the building in the correct position and a valid position
 */
public class CardtoBuildingTest {

    @Test
    public void BarrackPositionTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        int maxPathX = 6;
        int maxPathY = 4;

        for (int i = 0; i <= maxPathX; i++) {
            orderedPath.add(new Pair<>(i,1));
        }
        for (int j = 1; j <maxPathY; j++) {
            orderedPath.add(new Pair<>(maxPathX,j));
        }

        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Card card1 = new BarracksCard(new SimpleIntegerProperty(0),new SimpleIntegerProperty(world.getHeight()));
        Card card2 = new BarracksCard(new SimpleIntegerProperty(1),new SimpleIntegerProperty(world.getHeight()));

        assertTrue(card1.isCorrectPlacement(1, 1, orderedPath));
        assertFalse(card2.isCorrectPlacement(8, 7, orderedPath));

    }
    @Test
    public void CampfirePositionTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        int maxPathX = 6;
        int maxPathY = 4;

        for (int i = 0; i <= maxPathX; i++) {
            orderedPath.add(new Pair<>(i,1));
        }
        for (int j = 1; j <maxPathY; j++) {
            orderedPath.add(new Pair<>(maxPathX,j));
        }

        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Card card1 = new CampfireCard(new SimpleIntegerProperty(0),new SimpleIntegerProperty(world.getHeight()));
        Card card2 = new CampfireCard(new SimpleIntegerProperty(1),new SimpleIntegerProperty(world.getHeight()));
        Card card3 = new CampfireCard(new SimpleIntegerProperty(2),new SimpleIntegerProperty(world.getHeight()));

        assertFalse(card1.isCorrectPlacement(1, 1, orderedPath));
        assertTrue(card2.isCorrectPlacement(8, 7, orderedPath));
        assertTrue(card3.isCorrectPlacement(1, 2, orderedPath));

    }
    @Test
    public void TowerPositionTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        int maxPathX = 2;
        int maxPathY = 4;

        for (int i = 0; i <= maxPathX; i++) {
            orderedPath.add(new Pair<>(i,1));
        }
        for (int j = 0; j <maxPathY; j++) {
            orderedPath.add(new Pair<>(maxPathX,j));
        }

        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Card card1 = new TowerCard(new SimpleIntegerProperty(0),new SimpleIntegerProperty(world.getHeight()));
        Card card2 = new TowerCard(new SimpleIntegerProperty(1),new SimpleIntegerProperty(world.getHeight()));
        Card card3 = new TowerCard(new SimpleIntegerProperty(2),new SimpleIntegerProperty(world.getHeight()));

        assertFalse(card1.isCorrectPlacement(1, 1, orderedPath));
        assertFalse(card2.isCorrectPlacement(8, 7, orderedPath));
        assertTrue(card3.isCorrectPlacement(1, 2, orderedPath));

    }
    @Test
    public void TrapPositionTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        int maxPathX = 6;
        int maxPathY = 4;

        for (int i = 0; i <= maxPathX; i++) {
            orderedPath.add(new Pair<>(i,1));
        }
        for (int j = 0; j <maxPathY; j++) {
            orderedPath.add(new Pair<>(maxPathX,j));
        }

        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Card card1 = new TrapCard(new SimpleIntegerProperty(0),new SimpleIntegerProperty(world.getHeight()));
        Card card2 = new TrapCard(new SimpleIntegerProperty(1),new SimpleIntegerProperty(world.getHeight()));
        Card card3 = new TrapCard(new SimpleIntegerProperty(2),new SimpleIntegerProperty(world.getHeight()));

        //on path tiles
        assertTrue(card1.isCorrectPlacement(1, 1, orderedPath));        //on path tiles
        assertFalse(card2.isCorrectPlacement(8, 7, orderedPath));       //non-path tiles
        assertFalse(card3.isCorrectPlacement(1, 2, orderedPath));        //adjacent to path tiles (non-path)

    }
    @Test
    public void VampirePositionTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        int maxPathX = 6;
        int maxPathY = 4;

        for (int i = 0; i <= maxPathX; i++) {
            orderedPath.add(new Pair<>(i,1));
        }
        for (int j = 0; j <maxPathY; j++) {
            orderedPath.add(new Pair<>(maxPathX,j));
        }

        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Card card1 = new VampireCastleCard(new SimpleIntegerProperty(0),new SimpleIntegerProperty(world.getHeight()));
        Card card2 = new VampireCastleCard(new SimpleIntegerProperty(1),new SimpleIntegerProperty(world.getHeight()));
        Card card3 = new VampireCastleCard(new SimpleIntegerProperty(2),new SimpleIntegerProperty(world.getHeight()));

        //on path tiles
        assertFalse(card1.isCorrectPlacement(1, 1, orderedPath));        //on path tiles
        assertFalse(card2.isCorrectPlacement(8, 7, orderedPath));       //non-path tiles
        assertTrue(card3.isCorrectPlacement(1, 2, orderedPath));        //adjacent to path tiles (non-path)

    }
    @Test
    public void VillagePositionTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        int maxPathX = 6;
        int maxPathY = 4;

        for (int i = 0; i <= maxPathX; i++) {
            orderedPath.add(new Pair<>(i,1));
        }
        for (int j = 0; j <maxPathY; j++) {
            orderedPath.add(new Pair<>(maxPathX,j));
        }

        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Card card1 = new VillageCard(new SimpleIntegerProperty(0),new SimpleIntegerProperty(world.getHeight()));
        Card card2 = new VillageCard(new SimpleIntegerProperty(1),new SimpleIntegerProperty(world.getHeight()));
        Card card3 = new VillageCard(new SimpleIntegerProperty(2),new SimpleIntegerProperty(world.getHeight()));

        //on path tiles
        assertTrue(card1.isCorrectPlacement(1, 1, orderedPath));        //on path tiles
        assertFalse(card2.isCorrectPlacement(8, 7, orderedPath));       //non-path tiles
        assertFalse(card3.isCorrectPlacement(1, 2, orderedPath));        //adjacent to path tiles (non-path)

    }
    @Test
    public void zombiePitPositionTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        int maxPathX = 6;
        int maxPathY = 4;

        for (int i = 0; i <= maxPathX; i++) {
            orderedPath.add(new Pair<>(i,1));
        }
        for (int j = 0; j <maxPathY; j++) {
            orderedPath.add(new Pair<>(maxPathX,j));
        }

        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        Card card1 = new ZombiePitCard(new SimpleIntegerProperty(0),new SimpleIntegerProperty(world.getHeight()));
        Card card2 = new ZombiePitCard(new SimpleIntegerProperty(1),new SimpleIntegerProperty(world.getHeight()));
        Card card3 = new ZombiePitCard(new SimpleIntegerProperty(2),new SimpleIntegerProperty(world.getHeight()));

        //on path tiles
        assertFalse(card1.isCorrectPlacement(1, 1, orderedPath));        //on path tiles
        assertFalse(card2.isCorrectPlacement(8, 7, orderedPath));       //non-path tiles
        assertTrue(card3.isCorrectPlacement(1, 2, orderedPath));        //adjacent to path tiles (non-path)

    }
}
