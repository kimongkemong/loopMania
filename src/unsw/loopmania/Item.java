package unsw.loopmania;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * Item is an abstract class which is extended by various other classes
 */
public abstract class Item extends StaticEntity {
    private String name;
    private int cost;
    private int sellPrice;
    private int durability;
    
    /**
     * Initialise Item with x,y coords, name, cost and sellPrice
     * @param x
     * @param y
     * @param name
     * @param cost
     * @param sellPrice
     */
    public Item(SimpleIntegerProperty x, SimpleIntegerProperty y, String name, 
            int cost, int sellPrice) {
        super(x, y);
        this.name = name;
        this.cost = cost;
        this.sellPrice = sellPrice;
    }


    public int getCost() {
        return cost;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int price) {
        sellPrice = price;
    }

    public String getItemName() {
        return name;
    }


    public int getDurability() {
        return durability;
    }


    public void setDurability(int durability) {
        this.durability = durability;
    }

    
}
