package unsw.loopmania.Modes;

import java.util.List;

import unsw.loopmania.Item;

/** Confusing mode will modify the behaviour of rareItems by chaining random behaviours of other items */
public class ConfusingMode extends GameMode{

    /**
     * rare items look the same as the original item, but have both their original properties/behaviour, 
     * and the added properties/behaviour of another random rare item. 
     * 
     * Implementation Character.java
     */
    @Override
    public boolean ModeCriteria(List<String> updateItems, Item selectedItem) {
        return true;
    }
}