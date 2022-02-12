package unsw.loopmania;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.javatuples.Pair;

import javafx.beans.property.SimpleIntegerProperty;
import unsw.loopmania.ActionCards.RemovalistCard;
import unsw.loopmania.Buildings.*;
import unsw.loopmania.Enemies.*;
import unsw.loopmania.Modes.GameMode;


/**
 * A backend world.
 *
 * A world can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 */
public class LoopManiaWorld {

    public static final int unequippedInventoryWidth = 4;
    public static final int unequippedInventoryHeight = 4;

    public static final int allySlotWidth = 2;
    public static final int allySlotHeight = 4;
    public final int IGNOREDURABILITY = -1;

    /**
     * width of the world in GridPane cells
     */
    private int width;

    /**
     * height of the world in GridPane cells
     */
    private int height;

    /**
     * generic entitites - i.e. those which don't have dedicated fields
     */
    private List<Entity> nonSpecifiedEntities;

    private Character character;

    private List<BasicEnemy> enemies;

    private List<Card> cardEntities;

    private List<Item> unequippedInventoryItems;

    private List<Building> buildingEntities;
    
    private List<String> updateItems;
    // Count the cycles traversed
    private Integer cycleCounter;  
    // Count the current step in this cycle 
    private Integer stepsInCycle;

    private GameMode gameMode;
    private List<Ally> allies;
    private List<BasicEnemy> updateEnemy;

    private int numBossesKilled = 0;
    private Goal goals = null;
    private Boolean isShopOpen;
    private Boolean wild_Blue;
    private Random rand = new Random(521);
    private boolean win_game;
    private DropRates dRates;
    private boolean elanSpawn = false;
    private boolean doggieSpawn = false;

    private static final Integer MAX_HEALTH = 100;
    
    /**
     * list of x,y coordinate pairs in the order by which moving entities traverse them
     */
    private List<Pair<Integer, Integer>> orderedPath;

    /**
     * create the world (constructor)
     * 
     * @param width width of world in number of cells
     * @param height height of world in number of cells
     * @param orderedPath ordered list of x, y coordinate pairs representing position of path cells in world
     */
    public LoopManiaWorld(int width, int height, List<Pair<Integer, Integer>> orderedPath) {
        this.width = width;
        this.height = height;
        
        nonSpecifiedEntities = new ArrayList<>();
        character = new Character(new PathPosition(0, orderedPath));
        enemies = new ArrayList<>();
        updateItems = new ArrayList<>();
        cardEntities = new ArrayList<>();
        cycleCounter = 0; 
        stepsInCycle = 0;
        unequippedInventoryItems = new ArrayList<>();
        this.orderedPath = orderedPath;
        buildingEntities = new ArrayList<>();
        buildingEntities.add(new CastleHero(new SimpleIntegerProperty(0), new SimpleIntegerProperty(0)));
        this.allies = new ArrayList<Ally>();
        updateEnemy = new ArrayList<BasicEnemy>();
        this.dRates = new DropRates();
        this.isShopOpen = false;
        this.wild_Blue = false;
        this.win_game = false;
    }

    public Boolean getIsShopOpen() {
        return isShopOpen;
    }

    public Goal getGoals() {
        return goals;
    }

    public void setGoals(Goal goal) {
        goals = goal;
    }

    public void setIsShopOpen(Boolean isShopOpen) {
        this.isShopOpen = isShopOpen;
    }

    public List<Item> getUnequippedInventory()    {
        return unequippedInventoryItems;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<BasicEnemy> getEnemies() {
        return enemies;
    }

    public void addEnemies(BasicEnemy enemy) {
        enemies.add(enemy);
    }

    public List<Ally> getAllies() {
        return this.allies;
    }

    public Integer getCycleCounter() {
        return cycleCounter;
    }

    public void setCycleCounter(Integer cycleCounter) {
        this.cycleCounter = cycleCounter;
    }

    public Integer getStepsInCycle() {
        return stepsInCycle;
    }

    public List<Card> getCardEntities() {
        return cardEntities;
    }

    public List<BasicEnemy> getupdateEnemy() {
        return updateEnemy;
    }

    public List<Item> getUnequippedItems() {
        return unequippedInventoryItems;
    }

    public List<String> getUpdateItems(){
        return updateItems;
    }

    public boolean isWin_game() {
        return win_game;
    }

    public void setWin_game(boolean win_game) {
        this.win_game = win_game;
    }

    /**
     * set the character. This is necessary because it is loaded as a special entity out of the file
     * @param character the character
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public List<Building> getBuildings(){
        return buildingEntities;
    }

    public void addBuilding(Building building) {
        buildingEntities.add(building);
    }
    
    /**
     * add a generic entity (without it's own dedicated method for adding to the world)
     * @param entity
     */
    public void addEntity(Entity entity) {
        // for adding non-specific entities (ones without another dedicated list)
        nonSpecifiedEntities.add(entity);
    }

    /**
     * kill an enemy
     * @param enemy enemy to be killed
     */
    private void killEnemy(BasicEnemy enemy){
        enemy.destroy();
        enemies.remove(enemy);
    }

    /**
     * run the expected battles in the world, based on current world state
     * @return list of enemies which have been killed
     */
    public List<BasicEnemy> runBattles() {
        // finds all the enemies within distance of the fight and adds them
        List<BasicEnemy> fightingEnemies = new ArrayList<BasicEnemy>();
        List<BasicEnemy> defeatedEnemies = new ArrayList<BasicEnemy>();
        Boolean fightOccurs = false;
        for (BasicEnemy e: enemies){
            if (e.isInSupportRadius()) {
                if (e.isInBattleRadius()) {
                    fightOccurs = true;
                }
                fightingEnemies.add(e);
            }
        }

        // if enemies were in the range, a fight will occur and so any nearby towers will be included as well
        if (fightOccurs) {
            List<Building> fightingBuildings = new ArrayList<Building>();
            for (Building building : buildingEntities) {
                if (building instanceof Tower && building.buildingRadius(character)) {
                    fightingBuildings.add(building);
                }
            }
            defeatedEnemies.addAll(fightingEnemies);
            Battle currBattle = new Battle(fightingEnemies, allies, character, fightingBuildings);
            int alliesLeft = currBattle.beginBattle();
            // destroy the enemies that were defeated so they are destroyed on the frontend too
            for (BasicEnemy enemy : defeatedEnemies) {
                killEnemy(enemy);
            }
            // remove the allies which were defeated
            while (allies.size() != alliesLeft && allies.size() != 0) {
                Ally ally = allies.get(0);
                ally.destroy();
                allies.remove(0);
            }
        }
        return defeatedEnemies;
    }

    /**
     * spawn a card in the world and return the card entity
     * @return a card to be spawned in the controller as a JavaFX node
     */
    public Card spawnCard(){
        // if adding more cards than have, remove the first card...
        if (cardEntities.size() >= getWidth()){
            character.setGold(character.getGold() + 10);
            character.addExp(100);
            removeCard(0);
        }
        return Card.loadCard(cardEntities);
    }

    /**
     * remove card at a particular index of cards (position in gridpane of unplayed cards)
     * @param index the index of the card, from 0 to length-1
     */
    private void removeCard(int index){
        Card c = cardEntities.get(index);
        int x = c.getX();
        c.destroy();
        cardEntities.remove(index);
        shiftCardsDownFromXCoordinate(x);
    }

   
    /** FRONTEND LOADER FUNCTION 
     * This function takes in a string @param item and loads the item into the world
     * dependencies: loadItem(), onLoadItem()
     */
    public Item addItem(String item, Integer durability)   {
        Pair<Integer, Integer> firstAvailableSlot = getFirstAvailableSlotForItem();
        if (firstAvailableSlot == null){
            removeItemByPositionInUnequippedInventoryItems(0);
            firstAvailableSlot = getFirstAvailableSlotForItem();
        }
        if (item.equals("Sword"))    {
            Sword sword = new Sword(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            if (durability != -1)  sword.setDurability(durability);
            unequippedInventoryItems.add(sword);
            return sword;
        }
        else if (item.equals("Stake"))   {
            Stake stake= new Stake(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            if (durability != -1)  stake.setDurability(durability);
            unequippedInventoryItems.add(stake);
            return stake;
        }
        else if (item.equals("Staff"))   {
            Staff staff= new Staff(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            if (durability != -1)  staff.setDurability(durability);
            unequippedInventoryItems.add(staff);
            return staff;
        }
        else if (item.equals("Shield"))   {
            Shield shield= new Shield(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            if (durability != -1)  shield.setDurability(durability);
            unequippedInventoryItems.add(shield);
            return shield;
        }
        else if (item.equals("Armour"))   {
            Armour armour= new Armour(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            if (durability != -1)  armour.setDurability(durability);
            unequippedInventoryItems.add(armour);
            return armour;
        }
        else if (item.equals("Helmet"))   {
            Helmet helmet = new Helmet(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            if (durability != -1)  helmet.setDurability(durability);
            unequippedInventoryItems.add(helmet);
            return helmet;
        }  
        // Adds 20 gold for now
        else if (item.equals("Gold"))   { 
            character.setGold(character.getGold() + 20);
        }
        else if (item.equals("HealthPotion"))   {
            HealthPotion healthPotion = new HealthPotion(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            unequippedInventoryItems.add(healthPotion);
            return healthPotion;
        }
        else if (item.equals("TheOneRing"))   {
            TheOneRing theOneRing = new TheOneRing(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            unequippedInventoryItems.add(theOneRing);
            return theOneRing;
        }
        else if (item.equals("Anduril"))   {
            Anduril anduril = new Anduril(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            if (durability != -1) anduril.setDurability(durability);
            unequippedInventoryItems.add(anduril);
            return anduril;
        }
        else if (item.equals("TreeStump"))   {
            TreeStump treeStump = new TreeStump(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            unequippedInventoryItems.add(treeStump);
            if (durability != -1)  treeStump.setDurability(durability);
            return treeStump;
        }
        else if (item.equals("DoggieCoin"))   {
            DoggieCoin doggieCoin = new DoggieCoin(new SimpleIntegerProperty(firstAvailableSlot.getValue0()), new SimpleIntegerProperty(firstAvailableSlot.getValue1()));
            unequippedInventoryItems.add(doggieCoin);
            return doggieCoin;
        }
        return null;
    }

    /**
     * Frontend function 
     * The healper function allowsthe characters health to be set to maximum 
     * @param potion
     */
    public void consumePotion(int X, int Y) {
        character.setHealth(MAX_HEALTH);
        //removeUnequippedInventoryItemByCoordinates(X, Y);
        // No need to remove potion as all potions restore to 100 health
        System.out.println("Character Health set to " + character.getHealth() + " Inventory size " + getUnequippedInventory().size());
    }
   

    

    /**
     * remove an item by x,y coordinates
     * @param x x coordinate from 0 to width-1
     * @param y y coordinate from 0 to height-1
     */
    public void removeUnequippedInventoryItemByCoordinates(int x, int y){
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
    }


    /**
     * FRONTEND Helper functions -> called my LoopManiaWorldController
     * Equips an item by given x,y coordinates
     * @param x
     * @param y
     */
    public Item equipInventoryItemByCoordinates(int x, int y)    {
        Entity item = getUnequippedInventoryItemEntityByCoordinates(x, y);
        removeUnequippedInventoryItem(item);
        Item unequipped = null;

        if (item instanceof Weapon)  {
            unequipped = character.equipWeapon((Weapon)item);
        }
        else if (item instanceof Shield)    {
            unequipped = character.equipShield((Shield)item);
        }
        else if (item instanceof Armour)    {
            unequipped = character.equipArmour((Armour)item);
        }
        else if (item instanceof Helmet)    {
            unequipped = character.equipHelmet((Helmet)item);
        }
        if (unequipped != null) {
            return addItem(unequipped.getItemName(), unequipped.getDurability());
        }
        return unequipped;
    }

    
    /**
     * run moves which occur with every tick without needing to spawn anything immediately
     */
    public void runTickMoves(){
        if (goals != null) {
            if (goals.gameWon(character.getExp(), character.getGold(), numBossesKilled, cycleCounter)) {
                setWin_game(true);
            }
        }
        moveBasicEnemies();
        updateItems = new ArrayList<>();
        updateEnemy = new ArrayList<>();
        for (BasicEnemy e : enemies) {
            e.updateInRadius(character.getPosition());
        }
        character.moveDownPath();
        slugSpawn();
        doggieSpawn();
        elanSpawn();
        Iterator<Building> building = buildingEntities.iterator();
        while (building.hasNext()) {
            Building b = building.next();
            if(b instanceof Barracks) {
                Barracks barracks = (Barracks) b;
                barracks.spawnAllied(allies, character, getAllySlot());
            }

            if(b instanceof Campfire) {
                Campfire campfire = (Campfire) b;
                campfire.campfireHeal((Character) character);
            }

            if(b instanceof Trap) {
                Trap trap = (Trap) b;
                boolean success = trap.trapDamage(enemies);
                if (success) {
                    b.destroy();
                    building.remove();
                }
            }

            if(b instanceof VampireCastleBuilding) {
                VampireCastleBuilding vamp = (VampireCastleBuilding) b;
                vamp.spawnVampire(cycleCounter,getEnemySpawnPosition(b.getX(), b.getY()), enemies, updateEnemy);
            }

            if(b instanceof Village){
                Village village = (Village) b;
                village.villageHeal((Character)character);
            }

            if(b instanceof ZombiePit) {
                ZombiePit zomb = (ZombiePit) b;
                zomb.spawnZombie(cycleCounter,getEnemySpawnPosition(b.getX(), b.getY()), enemies, updateEnemy);
            }

            if (b instanceof BlueWildEntity) {
                BlueWildEntity blue_Entity = (BlueWildEntity) b;
                if(blue_Entity.buildingRadius(character)) {
                    blue_Entity.doAction();                    
                    setWild_Blue(true);
                    building.remove();                

                }
            }

            if(b instanceof RedWildEntity) {
                RedWildEntity red_Entity = (RedWildEntity) b;
                if(red_Entity.buildingRadius(character)){
                    red_Entity.doAction(unequippedInventoryItems);
                    building.remove();
                }
            }

            if(b instanceof CastleHero) {
                CastleHero castleHero = (CastleHero) b;
                if (castleHero.openShop(cycleCounter, character) || castleHero.openShop(character, getWild_Blue())) {
                    //open shop here
                    setIsShopOpen(true);
                }
            }

            if(b instanceof Removalist) {
                b.destroy();
                building.remove();
            }
        }
        updateCycleCounter();
    }

    public Boolean getWild_Blue() {
        return wild_Blue;
    }

    public void setWild_Blue(Boolean wild_Blue) {
        this.wild_Blue = wild_Blue;
    }

    /**
     * get a randomly generated position which could be used to spawn a slug
     * @return null if random choice is that wont be spawning an enemy or it isn't possible, or random coordinate pair if should go ahead
     */
    private Pair<Integer, Integer> possiblySlugSpawnPosition(){
        // has a chance spawning a basic enemy on a tile the character isn't on or immediately before or after (currently space required = 2)
        int choice = rand.nextInt(4);
        if (choice == 0){
            List<Pair<Integer, Integer>> orderedPathSpawnCandidates = new ArrayList<>();
            int indexPosition = orderedPath.indexOf(new Pair<Integer, Integer>(character.getX(), character.getY()));
            // inclusive start and exclusive end of range of positions not allowed
            int startNotAllowed = (indexPosition - 2 + orderedPath.size())%orderedPath.size();
            int endNotAllowed = (indexPosition + 3)%orderedPath.size();

            for (int i=endNotAllowed; i!=startNotAllowed; i=(i+1)%orderedPath.size()){
                orderedPathSpawnCandidates.add(orderedPath.get(i));
            }

            // choose random choice
            Pair<Integer, Integer> spawnPosition = orderedPathSpawnCandidates.get(rand.nextInt(orderedPathSpawnCandidates.size()));

            return spawnPosition;
        }
        return null;
    }

    /**
     * spawns slugs if the conditions warrant it, adds to world.
     */
    public void slugSpawn(){
        int slugCount = 0;
        for (BasicEnemy e: enemies) {
            if (e instanceof Slug) {
                slugCount += 1;
            }
        }
        if (slugCount < 3) {
            Pair<Integer, Integer> pos = possiblySlugSpawnPosition();
            if (pos != null){
                int indexInPath = orderedPath.indexOf(pos);
                Slug slug = new Slug(new PathPosition(indexInPath, orderedPath));
                enemies.add(slug);
                updateEnemy.add(slug);
            }
        }
    }

    /**
     * spawns doggie if the conditions warrant it, adds to world.
     */
    public void doggieSpawn(){
        if (cycleCounter == 20 && !doggieSpawn) {
            Pair<Integer, Integer> pos = possiblySlugSpawnPosition();
            if (pos != null){
                int indexInPath = orderedPath.indexOf(pos);
                Doggie doggie = new Doggie(new PathPosition(indexInPath, orderedPath));
                enemies.add(doggie);
                updateEnemy.add(doggie);
                doggieSpawn = true;
            }
        }
    }
    
    /**
     * spawns elanmuske if the conditions warrant it, adds to world.
     */
    public void elanSpawn(){
        if (cycleCounter == 40 && character.getExp() > 10000 && !elanSpawn) {
            Pair<Integer, Integer> pos = possiblySlugSpawnPosition();
            if (pos != null){
                int indexInPath = orderedPath.indexOf(pos);
                ElanMuske elanMuske = new ElanMuske(new PathPosition(indexInPath, orderedPath));
                enemies.add(elanMuske);
                updateEnemy.add(elanMuske);
                elanSpawn = true;
            }
        }
    }


    /**
     * Update Global Cycle when a cycle is completed 
     */
    public void updateCycleCounter()    {
        if (stepsInCycle.equals((orderedPath.size())))  {
            cycleCounter += 1;
            stepsInCycle = 0;
        }
        else { stepsInCycle += 1;}
    }

    /**
     * remove an item from the unequipped inventory
     * @param item item to be removed
     */
    private void removeUnequippedInventoryItem(Entity item){
        item.destroy();
        unequippedInventoryItems.remove(item);
    }

    /**
     * return an unequipped inventory item by x and y coordinates
     * assumes that no 2 unequipped inventory items share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return unequipped inventory item at the input position
     */
    private Entity getUnequippedInventoryItemEntityByCoordinates(int x, int y){
        for (Entity e: unequippedInventoryItems){
            if ((e.getX() == x) && (e.getY() == y)){
                return e;
            }
        }
        return null;
    }

    /**
     * remove item at a particular index in the unequipped inventory items list (this is ordered based on age in the starter code)
     * @param index index from 0 to length-1
     */
    private void removeItemByPositionInUnequippedInventoryItems(int index){
        Entity item = unequippedInventoryItems.get(index);
        item.destroy();
        unequippedInventoryItems.remove(index);
    }

    /**
     * get the first pair of x,y coordinates which don't have any items in it in the unequipped inventory
     * @return x,y coordinate pair
     */
    public Pair<Integer, Integer> getFirstAvailableSlotForItem(){
        // first available slot for an item...
        // IMPORTANT - have to check by y then x, since trying to find first available slot defined by looking row by row
        for (int y=0; y<unequippedInventoryHeight; y++){
            for (int x=0; x<unequippedInventoryWidth; x++){
                if (getUnequippedInventoryItemEntityByCoordinates(x, y) == null){
                    return new Pair<Integer, Integer>(x, y);
                }
            }
        }
        return null;
    }

    /**
     * get the first pair of x,y coordinates which don't have any Ally Soldier in it in the slot
     * @return x,y coordinate pair
     */
    public Pair<Integer, Integer> getAllySlot(){
        for (int x=0; x<allySlotWidth; x++){
            for (int y=0; y<allySlotHeight; y++){
                if(checkAllySlot(x, y) == null){
                    return new Pair<Integer, Integer>(x,y);
                }
            }
        }
        return null;
    }

    /**
     * return an empty allied soldier slot by x and y coordinates
     * assumes that no 2 allied soldier share x and y coordinates
     * @param x x index from 0 to width-1
     * @param y y index from 0 to height-1
     * @return ally slot at the input position
     */
    private Ally checkAllySlot(int x, int y){
        for(Ally ally : allies) {
            if ((ally.getX() == x) && (ally.getY() == y)){
                return ally;
            }
        }
        return null;
    }
    /**
     * shift card coordinates down starting from x coordinate
     * @param x x coordinate which can range from 0 to width-1
     */
    private void shiftCardsDownFromXCoordinate(int x){
        for (Card c: cardEntities){
            if (c.getX() >= x){
                c.x().set(c.getX()-1);
            }
        }
    }

    /**
     * move all enemies
     */
    private void moveBasicEnemies() {
        for (BasicEnemy e: enemies){
            e.move();
        }
    }

    /** 
     * getting possible spawn enmies near the building 
     */
    public PathPosition getEnemySpawnPosition(int x, int y) {
        int maxDist = 1000000000;
        int currPos = 0;
        int closestPos = 0;
        for (Pair<Integer,Integer> curr : orderedPath) {
            if (((curr.getValue0() - x) * (curr.getValue0() - x)) + ((curr.getValue1() - y) * (curr.getValue1() - y)) < maxDist) {
                maxDist = ((curr.getValue0() - x) * (curr.getValue0() - x)) + ((curr.getValue1() - y) * (curr.getValue1() - y));
                closestPos = currPos;
            }
            currPos += 1;
        }
        return new PathPosition(closestPos, orderedPath);
    }


    /**
     * remove a card by its x, y coordinates
     * @param cardNodeX x index from 0 to width-1 of card to be removed
     * @param cardNodeY y index from 0 to height-1 of card to be removed
     * @param buildingNodeX x index from 0 to width-1 of building to be added
     * @param buildingNodeY y index from 0 to height-1 of building to be added
     */
    public Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        // start by getting card
        Card card = getSpesificCard(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
        Pair<Integer, Integer> position = new Pair<Integer,Integer>(buildingNodeX, buildingNodeY);

        if (card instanceof RemovalistCard) {
            if (!isPositionBuildingValid(position)) {
                Building newBuilding = card.createBuildingfromCard(buildingNodeX, buildingNodeY);
                Removalist removalist = (Removalist) newBuilding;
                removalist.doAction(buildingEntities);
                buildingEntities.add(newBuilding);
                card.destroy();
                cardEntities.remove(card);
                shiftCardsDownFromXCoordinate(cardNodeX);
                return newBuilding;
            } 
            return null;
        }

        if (card.isCorrectPlacement(buildingNodeX, buildingNodeY, orderedPath) && isPositionBuildingValid(position)) {        
            Building newBuilding = card.createBuildingfromCard(buildingNodeX, buildingNodeY);
            buildingEntities.add(newBuilding);        
            card.destroy();
            cardEntities.remove(card);
            shiftCardsDownFromXCoordinate(cardNodeX);
            return newBuilding;
        }
        return null;
    }  

    /**
     * getting what card is in current position
     * @param cardNodeX
     * @param cardNodeY
     * @param buildingNodeX
     * @param buildingNodeY
     * @return
     */
    public Card getSpesificCard(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        Card card = null;
        for (Card c: cardEntities){
            if ((c.getX() == cardNodeX) && (c.getY() == cardNodeY)){
                card = c;
                break;
            }
        }
        return card;
    }

    /**
     * checking if the position is valid for placement
     * @param position
     * @return
     */
    private boolean isPositionBuildingValid(Pair<Integer, Integer> position) {
        for (Building building: buildingEntities) {
            Pair<Integer, Integer> buildingPos = new Pair<Integer,Integer>(building.getX(), building.getY());
            if (buildingPos.equals(position)) {
                return false;
            }
        }
        return true;
    }

    public void setGameMode(GameMode mode) {
        this.gameMode = mode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

}
