package unsw.loopmania.Shops;

import java.util.List;

import unsw.loopmania.Item;
import unsw.loopmania.Modes.GameMode;
import unsw.loopmania.Character;

/** Abstract class for shop */
public class Shop {
    /**
     * generic class for shop
     */
    private GameMode mode;
    private ShopInterface currentState;
    
    /** 
     * @return GameMode
     */
    public GameMode getMode() {
        return mode;
    }

    public Shop(GameMode mode){
        this.mode = mode;
        this.currentState = new BuyShop(mode);
    }

    /** 
     * @return ShopInterface
     */
    public ShopInterface getCurrentState() {
        return currentState;
    }

    
    /** 
     * @param currentState
     */
    public void setCurrentState(ShopInterface currentState) {
        this.currentState = currentState;
    }

    /** 
     * @param updateItem
     * @param selectedItem
     * @param character
     * @param inventoryItems
     * @return String
     */
    public String addItemToBasket(List<String> updateItem, Item selectedItem, Character character, List<Item> inventoryItems) {
        return currentState.addItemToBasket(updateItem, selectedItem, character, inventoryItems);
    }

}
