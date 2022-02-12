package test;

import java.util.List;
import java.util.Random;

import org.junit.Test;

import unsw.loopmania.DropRates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;

public class dropRatesTest {

    
    /** 
     * This test simulates 1000 drops and ensures that each item is seen atleast once 
     */
    @Test
    public void testDropratesV2()   {
        // Assumes seeded 21 
        DropRates V2 = new DropRates();
        List<String> set = new ArrayList<String>();

        for (int i = 0; i < 1000; i++)   {
            String drop = V2.itemtoDrop();
            if (!set.contains(drop))    {
                set.add(drop);
            }
        }
        assertEquals(set.size(), 8);
    }
    
    /**
     * This test ensures that the dropList has the correctly specified rare items
     */
    @Test 
    public void testDropRatesInputs() {
        List<String> rareItems = List.of("TheOneRing","Anduril");
        DropRates V2 = new DropRates(rareItems);
        assertEquals(V2.getDropList().size(), 10);
    }

    /**
     * This test checks to ensure Banned Items aren't dropping 
     */
    @Test
    public void testBannedItems()   {
        List<String> rareItems = List.of("TheOneRing","Anduril");
        List<String> bannedList = List.of("Sword", "Shield");
        DropRates V2 = new DropRates(rareItems, bannedList);
        assertEquals(V2.getDropList().size(), 8);
    }


    
}
