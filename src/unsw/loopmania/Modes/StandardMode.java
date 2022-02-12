package unsw.loopmania.Modes;

import java.util.List;

import unsw.loopmania.Item;

/** Standard mode */
public class StandardMode extends GameMode{

    /**
     * Standard mode has no distinguishing effects.
     */
    @Override
    public boolean ModeCriteria(List<String> updateItems, Item selectedItem) {
        return true;
    }
    
}
