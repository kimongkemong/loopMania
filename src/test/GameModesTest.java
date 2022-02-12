package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Shield;
import unsw.loopmania.Modes.*;
import unsw.loopmania.Shops.Shop;
import unsw.loopmania.Armour;
import unsw.loopmania.Character;
import unsw.loopmania.HealthPotion;
import unsw.loopmania.Helmet;

public class GameModesTest {

    /**
     * This Test checks to ensure all items are available and standard game mode can be loaded.
     */
    @Test
    public void StandardModeTest(){
        
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);        
        world.setCharacter(character);
        character.setGold(100);

        GameMode standardMode = new StandardMode();
        world.setGameMode(standardMode);

        Shop shop = new Shop(world.getGameMode());

        HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        HealthPotion healthPotion2 = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Helmet helmet = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Shield shield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        shop.addItemToBasket(world.getUpdateItems(), healthPotion, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), healthPotion2, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), helmet, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), armour, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), shield, world.getCharacter(), world.getUnequippedInventory());

        assertEquals(5, world.getUpdateItems().size());
        assertTrue(world.getUpdateItems().contains(helmet.getItemName()));
        assertTrue(world.getUpdateItems().contains(armour.getItemName()));
        assertTrue(world.getUpdateItems().contains(shield.getItemName()));
        assertTrue(world.getUpdateItems().contains(healthPotion.getItemName()));
        assertTrue(world.getUpdateItems().contains(healthPotion2.getItemName()));
    }

    /**
     *  This test checks whether only 1 potion can be purchase each round 
     *  from the Shop. 
     */
    @Test
    public void SurvivalModeTest(){

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);        
        world.setCharacter(character);
        character.setGold(100);

        GameMode survival = new SurvivalMode();
        world.setGameMode(survival);

        Shop shop = new Shop(world.getGameMode());

        HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        HealthPotion healthPotion2 = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1)); 
        Helmet helmet = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Shield shield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        shop.addItemToBasket(world.getUpdateItems(), healthPotion, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), healthPotion2, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), helmet, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), armour, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), shield, world.getCharacter(), world.getUnequippedInventory());

        assertEquals(4, world.getUpdateItems().size());
        assertTrue(world.getUpdateItems().contains(helmet.getItemName()));
        assertTrue(world.getUpdateItems().contains(armour.getItemName()));
        assertTrue(world.getUpdateItems().contains(shield.getItemName()));
        assertTrue(world.getUpdateItems().contains(healthPotion.getItemName()));

        int count = 0; 
        for (String item: world.getUpdateItems()) {
            if (item.equals(healthPotion.getItemName())) {
                count++;
            }
        }
        assertEquals(1, count);
    }

    /**
     * Atop of checking if only 1 potion can be purchased each round, his test checks to ensure that the human player cannot 
     * buy more than 1 protective item 
     * from the Shop each round 
     */
    @Test
    public void BerserkerModeTest(){

        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);        
        world.setCharacter(character);
        character.setGold(100);

        GameMode berserker = new BerserkerMode();
        world.setGameMode(berserker);

        Shop shop = new Shop(world.getGameMode());

        HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        HealthPotion healthPotion2 = new HealthPotion(new SimpleIntegerProperty(0), new SimpleIntegerProperty(1)); 
        Helmet helmet = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Shield shield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        shop.addItemToBasket(world.getUpdateItems(), healthPotion, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), healthPotion2, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), helmet, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), armour, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), shield, world.getCharacter(), world.getUnequippedInventory());

        assertEquals(3, world.getUpdateItems().size());
        assertFalse(world.getUpdateItems().contains(armour.getItemName()));
        assertFalse(world.getUpdateItems().contains(shield.getItemName()));
        assertTrue(world.getUpdateItems().contains(helmet.getItemName()));
        assertTrue(world.getUpdateItems().contains(healthPotion.getItemName()));

        int count = 0; 
        for (String item: world.getUpdateItems()) {
            if (item.equals(healthPotion.getItemName())) {
                count++;
            }
        }
        assertEquals(2, count);
    }
}