package unsw.loopmania.Shops;

import unsw.loopmania.Item;
import unsw.loopmania.Modes.GameMode;

import java.util.List;

import unsw.loopmania.Character;
import unsw.loopmania.DoggieCoin;

/** The Buy Shop allows the character to purchase items */
public class BuyShop implements ShopInterface{
    
    private GameMode mode;

    public BuyShop(GameMode mode) {
        this.mode = mode;
    }
    
    /**
     * Check if the character can purchase the items
     */
    public boolean doShopping(Item itemSelected, Character character, List<Item> inventoryItems) {
        if (itemSelected.getCost() <= character.getGold()) {
            character.setGold(character.getGold() - itemSelected.getCost());
            return true;
        }
        return false;
    }

    /**
     * add item to shopping cart, also checks the requirement from gameModes
     */
    public String addItemToBasket(List<String> basket, Item selectedItem, Character character, List<Item> inventoryItems) {
        if (this.getMode().ModeCriteria(basket,selectedItem) && doShopping(selectedItem, character, inventoryItems) && !(selectedItem instanceof DoggieCoin)) {
            //check modes
            basket.add(selectedItem.getItemName());
            return selectedItem.getItemName();
        }
        return null;
    }

    
    /** 
     * @return GameMode
     */
    public GameMode getMode() {
        return mode;
    }
}
