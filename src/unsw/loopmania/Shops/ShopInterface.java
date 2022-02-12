package unsw.loopmania.Shops;

import unsw.loopmania.Item;

import java.util.List;

import unsw.loopmania.Character;
/** Interface for shopping */
public interface ShopInterface {
    public boolean doShopping(Item itemSelected, Character character, List<Item> inventoryItems);
    public String addItemToBasket(List<String> updateItem, Item selectedItem,
     Character character, List<Item> inventoryItems);
}
