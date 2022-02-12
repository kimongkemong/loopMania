package unsw.loopmania.Modes;

import java.util.Arrays;
import java.util.List;


import unsw.loopmania.Item;

/** Berserker Mode restricts the amount of protective gear that can be purchased */
public class BerserkerMode extends GameMode{

    private String restricedItem[] = {"Armour", "Helmet", "Shield"};
    private List<String> list = Arrays.asList(restricedItem);

    /**
     *  the Human Player cannot purchase more than 1 piece of protective gear 
     * (protective gear includes armour, helmets, and shields) each time it shops at the Hero's Castle.
     */
    @Override
    public boolean ModeCriteria(List<String> updateItems, Item selectedItem) {
        if(list.contains(selectedItem.getItemName())){
            for(String item : updateItems){
                if(list.contains(item)){
                    return false;
                }
            }
        }
        return true;
    }
    
}
