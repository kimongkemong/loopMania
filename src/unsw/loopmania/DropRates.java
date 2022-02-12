package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The abstract class Droprates is used for determining the probabilities of an item dropping 
 * DropRates can be initialised with a list of random Strings for additional items 
 */
public class DropRates {
    
    private Random rand = new Random(1);

    // Store all possible generic items and rareItems
    List<String> generalItems = List.of("Sword","Stake", "Staff", "Armour", "Helmet","Shield", "Gold", "HealthPotion");
    List<String> rareItems = List.of("TheOneRing", "Anduril","TreeStump");

    /**
     * String List of enabled rareItems"
     */
     List<String> rareItemsEnabled = new ArrayList<String>();

     /**
      * String list of disabled items
      */
     List<String> itemsDisabled = new ArrayList<String>();

     /**
      * String List of droppable items
      */
     List<String> dropList = new ArrayList<String>();
   

    public DropRates()  {
        for (String S : generalItems)   {
            dropList.add(S);
        }
    }

    public DropRates(List<String> rareItemsEnabled)  {
        for (String S : generalItems)   {
            dropList.add(S);
        }
        for (String S : rareItemsEnabled)   {
            dropList.add(S);
        }
    }

    /** Assumes that if a rare item is provided, it won't be in the items disabled list */
    public DropRates(List<String> rareItemsEnabled, List<String> itemsDisabled)  {
        for (String S : rareItemsEnabled)   {
            dropList.add(S);
        }
        for (String S: generalItems)   {
            if (itemsDisabled.contains(S)) continue;
            else dropList.add(S);
        }
    }


    public List<String> getDropList() {
        return dropList;
    }


   /**
    * Returns a String for which item to spawn after defeating an enemy
    * @return
    */
    public String itemtoDrop()    {

        Integer sample = rand.nextInt(100);

        if (sample <= 10 && dropList.contains("Sword")) {
            return "Sword";
        }
        else if (sample <= 20 && dropList.contains("Stake"))    {
            return "Stake";
        }
        else if (sample <= 30 && dropList.contains("Staff"))  {
            return "Staff";
        }
        else if (sample <= 35 && dropList.contains("Armour")){
            return "Armour";
        }
        else if (sample <= 45 && dropList.contains("Shield")){
            return "Shield";
        }
        else if (sample <= 55 && dropList.contains("Helmet")){
            return "Helmet";
        }
        else if (sample <= 75 && dropList.contains("Gold")){
            return "Gold";
        }
        else if (sample <= 98 && dropList.contains("HealthPotion"))   {
            return "HealthPotion";
        }
        // 2% chance of a rare drop
        else {
            if (rareItemsEnabled.size() > 0)   {
                int interval = rareItemsEnabled.size();
                int idx = rand.nextInt(interval); 
                return rareItemsEnabled.get(idx);
            }
            else return "HealthPotion";
        }
    }

  
       
}


/**
 *  int sample = rand.nextInt(100);

        // Return a random item from the rareItems list 
        //TODO fixup this function as currently leading to threading issues as "out of bounds"
        if (sample == 0 && rareItems.size() > 0)   {
            System.out.println(rareItems.size());
            int rnd = new Random().nextInt((rareItems.size()));
            return rareItems.get(rnd);    
        }
        if (sample <= 10) {
            return "Sword";
        }
        else if (sample <= 20)    {
            return "Stake";
        }
        else if (sample <= 30)  {
            return "Staff";
        }
        else if (sample <= 40){
            return "Armour";
        }
        else if (sample <= 50){
            return "Shield";
        }
        else if (sample <= 60){
            return "Helmet";
        }
        else if (sample <= 80){
            return "Gold";
        }
        else    {
            return "HealthPotion";
        }
    }
 */
