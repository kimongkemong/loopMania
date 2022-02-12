package unsw.loopmania.Shops;

import unsw.loopmania.Item;
import unsw.loopmania.Modes.GameMode;
import unsw.loopmania.HealthPotion;

import java.util.List;

import unsw.loopmania.Character;

public class SellShop implements ShopInterface{

    private GameMode mode;

    public SellShop(GameMode mode) {
        this.mode = mode;
    }

    /**
     * check if the item can be sold
     */
    @Override
    public boolean doShopping(Item itemSelected, Character character, List<Item> inventoryItems) {
        if (findingItem(itemSelected, inventoryItems) != null) {
            character.setGold(character.getGold() + itemSelected.getSellPrice());
            return true;
        }
        return false;
    }
    /**
     * spesify on which item to sell based on the inventory
     * @param itemSelected
     * @param inventoryItems
     * @return
     */
    private Item findingItem(Item itemSelected, List<Item> inventoryItems) {
        for (Item item : inventoryItems) {
            if (item.getItemName().equals(itemSelected.getItemName())) {
                itemSelected = item;
                return itemSelected;
            }
        }
        return null;
    }

    /**
     * sold Item from inventory if item exist in inventory
     */
    @Override
    public String addItemToBasket(List<String> updateItem, Item selectedItem, Character character, List<Item> inventoryItems) {
        if (doShopping(selectedItem, character, inventoryItems) && !(selectedItem instanceof HealthPotion)) {
            Item item = findingItem(selectedItem, inventoryItems);
            item.destroy();
            inventoryItems.remove(item);
            return item.getItemName();
        }
        return null;
    }
}
