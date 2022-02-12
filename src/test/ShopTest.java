package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.Armour;
import unsw.loopmania.Character;
import unsw.loopmania.HealthPotion;
import unsw.loopmania.Helmet;
import unsw.loopmania.LoopManiaWorld;
import unsw.loopmania.PathPosition;
import unsw.loopmania.Shield;
import unsw.loopmania.Staff;
import unsw.loopmania.Stake;
import unsw.loopmania.Sword;
import unsw.loopmania.Modes.GameMode;
import unsw.loopmania.Modes.StandardMode;
import unsw.loopmania.Shops.*;

public class ShopTest {
    
    /** Check the Buy side functionality and ensures items can be purchased and addded to the Inventory.*/
    @Test
    public void BuyBasicTest(){
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

        assertEquals(world.getUnequippedInventory().size(), 0);
        shop.addItemToBasket(world.getUpdateItems(), healthPotion, world.getCharacter(), world.getUnequippedInventory());
        assertEquals(1, world.getUpdateItems().size());
        assertTrue(world.getUpdateItems().contains(healthPotion.getItemName()));

        Helmet helmet = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Shield shield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        shop.addItemToBasket(world.getUpdateItems(), helmet, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), armour, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), shield, world.getCharacter(), world.getUnequippedInventory());
        assertEquals(4, world.getUpdateItems().size());
        assertTrue(world.getUpdateItems().contains(helmet.getItemName()));
        assertTrue(world.getUpdateItems().contains(armour.getItemName()));
        assertTrue(world.getUpdateItems().contains(shield.getItemName()));

        character.setGold(0);
        Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        shop.addItemToBasket(world.getUpdateItems(), staff, world.getCharacter(), world.getUnequippedInventory());
        assertFalse(world.getUpdateItems().contains(staff.getItemName()));

    }

    /** Check to see whether Items that the character has can be sold in the Shop */
    @Test
    public void SellShopTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);        

        world.setCharacter(character);

        GameMode standardMode = new StandardMode();
        world.setGameMode(standardMode);

        Shop shop = new Shop(world.getGameMode());
        shop.setCurrentState(new SellShop(world.getGameMode()));

        Helmet helmet = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Shield shield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Stake stake  = new Stake(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        world.addItem(helmet.getItemName(), -1);
        world.addItem(armour.getItemName(), -1);
        world.addItem(shield.getItemName(), -1);
        world.addItem(stake.getItemName(), -1);
        world.addItem(staff.getItemName(), -1);

        assertEquals(5, world.getUnequippedInventory().size());
        shop.addItemToBasket(world.getUpdateItems(), helmet, world.getCharacter(), world.getUnequippedInventory());
        assertFalse(world.getUnequippedInventory().contains(helmet));
        assertEquals(4, world.getUnequippedInventory().size());


        shop.addItemToBasket(world.getUpdateItems(), helmet, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), armour, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), shield, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), staff, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), stake, world.getCharacter(), world.getUnequippedInventory());
        assertEquals(0, world.getUnequippedInventory().size());
        assertFalse(world.getUnequippedInventory().contains(armour));
        assertFalse(world.getUnequippedInventory().contains(shield));
        assertFalse(world.getUnequippedInventory().contains(stake));
        assertFalse(world.getUnequippedInventory().contains(staff));

    }

    /** Tests whether items can be purchased and sold at the shop as well as ensuring 
     * inventory and gold is correctly updated */
    @Test
    public void BuyAndSellTest(){
        List<Pair<Integer, Integer>> orderedPath = new ArrayList<Pair<Integer, Integer>>();
        orderedPath.add(new Pair<>(1,1));
        LoopManiaWorld world = new LoopManiaWorld(10, 10, orderedPath);
        PathPosition pos = new PathPosition(0, orderedPath);
        Character character = new Character(pos);        

        world.setCharacter(character);

        GameMode standardMode = new StandardMode();
        world.setGameMode(standardMode);
        character.setGold(100);

        Shop shop = new Shop(world.getGameMode());
        shop.setCurrentState(new SellShop(world.getGameMode()));

        Helmet helmet = new Helmet(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)); 
        Armour armour = new Armour(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Shield shield = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Shield shield2  = new Shield(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Staff staff = new Staff(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));
        Sword sword = new Sword(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0));

        //fill in inventory
        world.addItem(helmet.getItemName(), -1);
        world.addItem(armour.getItemName(), -1);
        world.addItem(shield.getItemName(), -1);
        world.addItem(sword.getItemName(), -1);


        //sell
        assertEquals(4, world.getUnequippedInventory().size());
        shop.addItemToBasket(world.getUpdateItems(), helmet, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), armour, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), shield, world.getCharacter(), world.getUnequippedInventory());
        assertFalse(world.getUnequippedInventory().contains(helmet));
        assertFalse(world.getUnequippedInventory().contains(armour));
        assertFalse(world.getUnequippedInventory().contains(shield));
        assertEquals(1, world.getUnequippedInventory().size());

        shop.setCurrentState(new BuyShop(world.getGameMode()));

        shop.addItemToBasket(world.getUpdateItems(), staff, world.getCharacter(), world.getUnequippedInventory());
        shop.addItemToBasket(world.getUpdateItems(), shield2, world.getCharacter(), world.getUnequippedInventory());
        assertEquals(2, world.getUpdateItems().size());
        assertTrue(world.getUpdateItems().contains(staff.getItemName()));
        assertTrue(world.getUpdateItems().contains(shield2.getItemName()));

        shop.setCurrentState(new SellShop(world.getGameMode()));
        shop.addItemToBasket(world.getUpdateItems(), sword, world.getCharacter(), world.getUnequippedInventory());
        assertFalse(world.getUnequippedInventory().contains(sword));
        assertEquals(0, world.getUnequippedInventory().size());




    }
}
