package unsw.loopmania.Modes;

import java.util.List;

import unsw.loopmania.HealthPotion;
import unsw.loopmania.Item;

/** Survival mode restricts the amount of potions a player can buy each shop event. */
public class SurvivalMode extends GameMode{

    /**
     * the Human Player can only purchase 1 health potion each time the Character shops at the Hero's Castle.
     */
    @Override
    public boolean ModeCriteria(List<String> updateItems, Item selectedItem) {
        if (selectedItem instanceof HealthPotion && updateItems.contains(selectedItem.getItemName())) {
            return false;
        }
        return true;
    }
}
