package unsw.loopmania.Modes;

import java.util.List;

import unsw.loopmania.Item;

/** Interface for shoppingModes */
public interface ShoppingModes {
    public boolean ModeCriteria(List<String> updateItems, Item selectedItem);
}
