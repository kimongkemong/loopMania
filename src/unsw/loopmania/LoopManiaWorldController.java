package unsw.loopmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.codefx.libfx.listener.handle.ListenerHandle;
import org.codefx.libfx.listener.handle.ListenerHandles;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.util.Duration;
import unsw.loopmania.Enemies.Doggie;
import unsw.loopmania.Enemies.ElanMuske;
import unsw.loopmania.Enemies.Slug;
import unsw.loopmania.Enemies.Zombie;

import java.util.HashMap; 
import javafx.scene.control.ProgressBar;

import java.util.EnumMap;

import java.io.File;
import java.io.IOException;


/**
 * the draggable types.
 * If you add more draggable types, add an enum value here.
 * This is so we can see what type is being dragged.
 */
enum DRAGGABLE_TYPE{
    CARD,
    ITEM
}

/**
 * A JavaFX controller for the world.
 * 
 * All event handlers and the timeline in JavaFX run on the JavaFX application thread:
 *     https://examples.javacodegeeks.com/desktop-java/javafx/javafx-concurrency-example/
 *     Note in https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Application.html under heading "Threading", it specifies animation timelines are run in the application thread.
 * This means that the starter code does not need locks (mutexes) for resources shared between the timeline KeyFrame, and all of the  event handlers (including between different event handlers).
 * This will make the game easier for you to implement. However, if you add time-consuming processes to this, the game may lag or become choppy.
 * 
 * If you need to implement time-consuming processes, we recommend:
 *     using Task https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Task.html by itself or within a Service https://openjfx.io/javadoc/11/javafx.graphics/javafx/concurrent/Service.html
 * 
 *     Tasks ensure that any changes to public properties, change notifications for errors or cancellation, event handlers, and states occur on the JavaFX Application thread,
 *         so is a better alternative to using a basic Java Thread: https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm
 *     The Service class is used for executing/reusing tasks. You can run tasks without Service, however, if you don't need to reuse it.
 *
 * If you implement time-consuming processes in a Task or thread, you may need to implement locks on resources shared with the application thread (i.e. Timeline KeyFrame and drag Event handlers).
 * You can check whether code is running on the JavaFX application thread by running the helper method printThreadingNotes in this class.
 * 
 * NOTE: http://tutorials.jenkov.com/javafx/concurrency.html and https://www.developer.com/design/multithreading-in-javafx/#:~:text=JavaFX%20has%20a%20unique%20set,in%20the%20JavaFX%20Application%20Thread.
 * 
 * If you need to delay some code but it is not long-running, consider using Platform.runLater https://openjfx.io/javadoc/11/javafx.graphics/javafx/application/Platform.html#runLater(java.lang.Runnable)
 *     This is run on the JavaFX application thread when it has enough time.
 */
public class LoopManiaWorldController {

    /**
     * squares gridpane includes path images, enemies, character, empty grass, buildings
     */
    @FXML
    private GridPane squares;

    /**
     * cards gridpane includes cards and the ground underneath the cards
     */
    @FXML
    private GridPane cards;

    /**
     * anchorPaneRoot is the "background". It is useful since anchorPaneRoot stretches over the entire game world,
     * so we can detect dragging of cards/items over this and accordingly update DragIcon coordinates
     */
    @FXML
    private AnchorPane anchorPaneRoot;

    /**
     * equippedItems gridpane is for equipped items (e.g. swords, shield, axe)
     */
    @FXML
    private GridPane equippedItems;

    @FXML 
    private GridPane equippedHelmet; 

    @FXML
    private GridPane equippedShield;

    @FXML 
    private GridPane equippedArmour;

    @FXML 
    private GridPane equippedWeapon;

    @FXML
    private GridPane the_one_ring_slot;

    @FXML
    private GridPane unequippedInventory;

    @FXML
    private Label charGold;

    @FXML
    private Label healthChar;

    @FXML
    private Label expChar;

    @FXML
    private Label worldCycle;

    @FXML
    private ProgressBar healthBar;
    
    @FXML
    private Label GameModeLabel;

    @FXML
    private GridPane soldierGridPane;

    // all image views including tiles, character, enemies, cards... even though cards in separate gridpane...
    private List<ImageView> entityImages;

    /**
     * when we drag a card/item, the picture for whatever we're dragging is set here and we actually drag this node
     */
    private DragIcon draggedEntity;

    private boolean isPaused;
    private LoopManiaWorld world;

    /**
     *  Droprates object has a method to determine, which iterm to drop. 
     */
    DropRates d = new DropRates();

    /**
     * runs the periodic game logic - second-by-second moving of character through maze, as well as enemies, and running of battles
     */
    private Timeline timeline;

    /**
     * 
     */
    private Image swordImage;
    private Image stakeImage;
    private Image staffImage;
    private Image armourImage;
    private Image shieldImage;
    private Image helmetImage;
    private Image soldierImage;
    private Image TheOneRingImage;
    private Image healthPotionImage;
    private Image theOneRingImage;
    private Image treeStumpImage;
    private Image andurilImage;
    private Image doggieImage;


    /**
     * the image currently being dragged, if there is one, otherwise null.
     * Holding the ImageView being dragged allows us to spawn it again in the drop location if appropriate.
     */
    private ImageView currentlyDraggedImage;
    
    /**
     * null if nothing being dragged, or the type of item being dragged
     */
    private DRAGGABLE_TYPE currentlyDraggedType;

    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped over its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged over the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragOver;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dropped in the background
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> anchorPaneRootSetOnDragDropped;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged into the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragEntered;
    /**
     * mapping from draggable type enum CARD/TYPE to the event handler triggered when the draggable type is dragged outside of the boundaries of its appropriate gridpane
     */
    private EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>> gridPaneNodeSetOnDragExited;

    private HashMap<GridPane, ImageView> mapEquippedImages = new HashMap<GridPane, ImageView>();
    /**
     * object handling switching to the main menu
     */
    private MenuSwitcher MenuSwitcher;
    private MenuSwitcher toShopSwitcher;
    private MenuSwitcher deadScreen;
    private MenuSwitcher winScreenSwitcher;
    /**
     * @param world world object loaded from file
     * @param initialEntities the initial JavaFX nodes (ImageViews) which should be loaded into the GUI
     */
    public LoopManiaWorldController(LoopManiaWorld world, List<ImageView> initialEntities) {
        this.world = world;
        entityImages = new ArrayList<>(initialEntities);
        swordImage = new Image((new File("src/images/basic_sword.png")).toURI().toString());
        stakeImage = new Image((new File("src/images/stake.png")).toURI().toString());;
        staffImage = new Image((new File("src/images/staff.png")).toURI().toString());;
        armourImage = new Image((new File("src/images/armour.png")).toURI().toString());;
        shieldImage = new Image((new File("src/images/shield.png")).toURI().toString());;
        helmetImage = new Image((new File("src/images/helmet.png")).toURI().toString());;
        soldierImage = new Image((new File("src/images/deep_elf_master_archer.png")).toURI().toString());;
        andurilImage = new Image((new File("src/images/anduril.png")).toURI().toString());;
        doggieImage = new Image((new File("src/images/doggiecoin.png")).toURI().toString());;
        treeStumpImage = new Image((new File("src/images/tree_stump.png")).toURI().toString());;
        healthPotionImage = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());;
        theOneRingImage = new Image((new File("src/images/the_one_ring.png")).toURI().toString());;
        
        currentlyDraggedImage = null;
        currentlyDraggedType = null;

        // initialize them all...
        gridPaneSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragOver = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        anchorPaneRootSetOnDragDropped = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragEntered = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
        gridPaneNodeSetOnDragExited = new EnumMap<DRAGGABLE_TYPE, EventHandler<DragEvent>>(DRAGGABLE_TYPE.class);
    }

    @FXML
    public void initialize() {
        healthChar.setText(Integer.toString(world.getCharacter().getHealth()));
        charGold.setText(Integer.toString(world.getCharacter().getGold()));
        expChar.setText(Integer.toString(world.getCharacter().getExp()));
        worldCycle.setText(Integer.toString(world.getCycleCounter()));

        Image pathTilesImage = new Image((new File("src/images/32x32GrassAndDirtPath.png")).toURI().toString());
        Image inventorySlotImage = new Image((new File("src/images/empty_slot.png")).toURI().toString());
        Rectangle2D imagePart = new Rectangle2D(0, 0, 32, 32);

        // Add the ground first so it is below all other entities (inculding all the twists and turns)
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                ImageView groundView = new ImageView(pathTilesImage);
                groundView.setViewport(imagePart);
                squares.add(groundView, x, y);
            }
        }

        // load entities loaded from the file in the loader into the squares gridpane
        for (ImageView entity : entityImages){
            squares.getChildren().add(entity);
        }
        
        // add the ground underneath the cards
        for (int x=0; x<world.getWidth(); x++){
            ImageView groundView = new ImageView(pathTilesImage);
            groundView.setViewport(imagePart);
            cards.add(groundView, x, 0);
        }

        // add the empty slot images for the unequipped inventory
        for (int x=0; x<LoopManiaWorld.unequippedInventoryWidth; x++){
            for (int y=0; y<LoopManiaWorld.unequippedInventoryHeight; y++){
                ImageView emptySlotView = new ImageView(inventorySlotImage);
                unequippedInventory.add(emptySlotView, x, y);
            }
        }

        healthBar.setStyle("-fx-accent: red;");

        // create the draggable icon
        draggedEntity = new DragIcon();
        draggedEntity.setVisible(false);
        draggedEntity.setOpacity(0.7);
        anchorPaneRoot.getChildren().add(draggedEntity);
    }

    public LoopManiaWorld getWorld() {
        return world;
    }
    /**
     * create and run the timer
     */
    public void startTimer(){
        System.out.println("starting timer");
        isPaused = false;
        GameModeLabel.setText(world.getGameMode().getClass().getSimpleName());
        // trigger adding code to process main game logic to queue. JavaFX will target framerate of 0.3 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), event -> {
            healthChar.setText(Integer.toString(world.getCharacter().getHealth())+"/100");
            double health = (double) world.getCharacter().getHealth()/100;
            healthBar.setProgress(health);
            charGold.setText(Integer.toString(world.getCharacter().getGold()));
            expChar.setText(Integer.toString(world.getCharacter().getExp())+"/100000");
            worldCycle.setText(Integer.toString(world.getCycleCounter())+"/50");
            if(world.isWin_game()){
                switchToWin();
            }
            if(world.getCharacter().getHealth() <= 0) {
                switchToDead();
            }
            for (String item : world.getUpdateItems()) {
                loadItem(item);
            }
            world.runTickMoves();
            if (world.getIsShopOpen()) {
                switchToShop();
                world.setIsShopOpen(false);
                world.setWild_Blue(false);
            }
            List<BasicEnemy> defeatedEnemies = world.runBattles();
            for (BasicEnemy e: defeatedEnemies){
                reactToEnemyDefeat(e);   
                // Load doggieCoin if Doggie is defeated
                reactToDoggieCoin(e);
                if (e instanceof Slug) System.out.println("FK MEEEEEEE!!!!!!!!!!!!");
            }
            List<BasicEnemy> newEnemies =  world.getupdateEnemy();
            for (BasicEnemy newEnemy: newEnemies){
                onLoad(newEnemy);
            }            
            onLoad(world.getAllies());
            removeBrokenItems();
            printThreadingNotes("HANDLED TIMER");
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * pause the execution of the game loop
     * the human player can still drag and drop items during the game pause
     */
    public void pause(){
        isPaused = true;
        System.out.println("pausing");
        timeline.stop();
    }

    public void terminate(){
        pause();
    }

    /**
     * pair the entity an view so that the view copies the movements of the entity.
     * add view to list of entity images
     * @param entity backend entity to be paired with view
     * @param view frontend imageview to be paired with backend entity
     */
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entityImages.add(view);
    }

    /**
     * load a vampire card from the world, and pair it with an image in the GUI
     */
    private void loadCard() {
        Card Card = world.spawnCard();
        onLoad(Card);
    }

    /**
     * Load an item from the world 
     * @param enemy
     */
    private void loadItem(String item) {
        if (item.equals("Sword"))    {
            Sword sword = (Sword)world.addItem(item, world.IGNOREDURABILITY);
            onLoad(sword);
        }
        else if (item.equals("Stake"))   {
            Stake stake = (Stake)world.addItem(item, world.IGNOREDURABILITY);
            onLoad(stake);
        }
        else if (item.equals("Staff"))   {
            Staff staff= (Staff)world.addItem(item, world.IGNOREDURABILITY);
            onLoad(staff);
        }
        else if (item.equals("Shield"))   {
            Shield shield = (Shield)world.addItem(item, world.IGNOREDURABILITY);
            onLoad(shield);
        }
        else if (item.equals("Armour"))   {
            Armour armour= (Armour)world.addItem(item, world.IGNOREDURABILITY);
            onLoad(armour);
        }
        else if (item.equals("Helmet"))   {
            Helmet helmet= (Helmet)world.addItem(item, world.IGNOREDURABILITY);
            onLoad(helmet);
        }
        else if (item.equals("Gold"))   { 
            world.addItem(item, world.IGNOREDURABILITY);
            //onLoad(gold);
        }
        else if (item.equals("HealthPotion"))   {
            HealthPotion healthPotion = (HealthPotion)world.addItem(item, world.IGNOREDURABILITY);
            onLoad(healthPotion);
        }
        else if (item.equals("TheOneRing"))   {
            TheOneRing theOneRing = (TheOneRing)world.addItem(item, world.IGNOREDURABILITY);
            onLoad(theOneRing);
        }
        else if (item.equals("Anduril"))    {
            Anduril anduril = (Anduril)world.addItem(item, world.IGNOREDURABILITY);
            onLoad(anduril);
        }
        else if (item.equals("TreeStump"))  {
            TreeStump treeStump = (TreeStump)world.addItem(item, world.IGNOREDURABILITY);
            onLoad(treeStump);
        }
        else if (item.equals("DoggieCoin"))    {
            DoggieCoin doggieCoin = (DoggieCoin)world.addItem(item, world.IGNOREDURABILITY);
            onLoad(doggieCoin);
        }


        else return; // If object is null return
    }


    /**
     * run GUI events after an enemy is defeated, such as spawning items/experience/gold
     * @param enemy defeated enemy for which we should react to the death of
     */
    private void reactToEnemyDefeat(BasicEnemy enemy){
        // react to character defeating an enemy
        // in starter code, spawning extra card/weapon...
        
        
        loadItem(d.itemtoDrop());
        loadCard();
    }


    /** 
     * This function will fluctuate the price of DoggieCoin 
     * else if Elon is deat then price drops
     */
    private void reactToDoggieCoin(BasicEnemy e)    {

        if (e instanceof Doggie) loadItem("DoggieCoin");
        // If ElonMuske is defeated reduce the price
        if (e instanceof ElanMuske) {
            for (Item i : world.getUnequippedInventory())   {
                if (i instanceof DoggieCoin)    {
                    ((DoggieCoin)i).decreasePrice(new Random().nextInt(100));
                    
                }
            }
        }
        else {
            for (Item i : world.getUnequippedInventory())   {
                if (i instanceof DoggieCoin)    {
                    ((DoggieCoin)i).increasePrice(new Random().nextInt(100));
                    
                }
            }

        }
    }


    /**
     * load cards into the GUI.
     * Particularly, we must connect to the drag detection event handler,
     * and load the image into the cards GridPane.
     * @param Card
     */
    private void onLoad(Card card) {
        ImageView view = card.getImage();

        // FROM https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
        // note target setOnDragOver and setOnDragEntered defined in initialize method
        addDragEventHandlers(view, DRAGGABLE_TYPE.CARD, cards, squares);

        addEntity(card, view);
        cards.getChildren().add(view);
    }

    

     /**
     * The following loaders take in an item and spawn in the ImageView of the item into the world
     *  @param Item item, 
     */
    private void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedWeapon);
        addEntity(sword, view);
        unequippedInventory.getChildren().add(view);
    }
    private void onLoad(Stake stake) {
        ImageView view = new ImageView(stakeImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedWeapon);
        addEntity(stake, view);
        unequippedInventory.getChildren().add(view);
    }
    private void onLoad(Staff staff) {
        ImageView view = new ImageView(staffImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedWeapon);
        addEntity(staff, view);
        unequippedInventory.getChildren().add(view);
    }
    private void onLoad(Shield shield) {
        ImageView view = new ImageView(shieldImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedShield);
        addEntity(shield, view);
        unequippedInventory.getChildren().add(view);
    }
    private void onLoad(Armour armour) {
        ImageView view = new ImageView(armourImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedArmour);
        addEntity(armour, view);
        unequippedInventory.getChildren().add(view);
    }
    private void onLoad(Helmet helmet) {
        ImageView view = new ImageView(helmetImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedHelmet);
        addEntity(helmet, view);
        unequippedInventory.getChildren().add(view);    
    }
    private void onLoad(HealthPotion healthPotion) {
        ImageView view = new ImageView(healthPotionImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, unequippedInventory);
        addEntity(healthPotion, view);
        unequippedInventory.getChildren().add(view);
        view.setOnMousePressed(event -> clickPotion(event, view));
    }
    private void onLoad(TheOneRing theOneRing)  {
        ImageView view = new ImageView(theOneRingImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, the_one_ring_slot);
        addEntity(theOneRing, view);
        unequippedInventory.getChildren().add(view);    
    }
    private void onLoad(Anduril anduril)  {
        ImageView view = new ImageView(andurilImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedWeapon);
        addEntity(anduril, view);
        unequippedInventory.getChildren().add(view);    
    }
    private void onLoad(TreeStump treeStump)  {
        ImageView view = new ImageView(treeStumpImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, equippedShield);
        addEntity(treeStump, view);
        unequippedInventory.getChildren().add(view);    
    }
    private void onLoad(DoggieCoin doggieCoin)  {
        ImageView view = new ImageView(doggieImage);
        addDragEventHandlers(view, DRAGGABLE_TYPE.ITEM, unequippedInventory, unequippedInventory);
        addEntity(doggieCoin, view);
        unequippedInventory.getChildren().add(view);    
    }


    /** This functions removes broken items 
     *  from the equipped Gridpane
     */
    public void removeBrokenItems()  {
          
         if (world.getCharacter().getArmour() == null)  {
            try {
                world.getCharacter().remove_broken_armour();
                ImageView previouslyEquippedItem =  mapEquippedImages.get(equippedArmour);
                equippedArmour.getChildren().remove(previouslyEquippedItem);
                mapEquippedImages.remove(equippedArmour);
            } catch (Exception e)   {
                System.out.print("No Armour Currently equipped");

            }
        }

        if (world.getCharacter().getHelmet() == null)  {
            try {
                world.getCharacter().remove_broken_helmet();
                ImageView previouslyEquippedItem =  mapEquippedImages.get(equippedHelmet);
                equippedHelmet.getChildren().remove(previouslyEquippedItem);
                mapEquippedImages.remove(equippedHelmet);
            } catch (Exception e)   {
                System.out.print("No Helmet Currently equipped");
            }
        }
        if (world.getCharacter().getShield() == null)   {
            try {
                world.getCharacter().remove_broken_shield();
                ImageView previouslyEquippedItem = mapEquippedImages.get(equippedShield);
                equippedShield.getChildren().remove(previouslyEquippedItem);
                mapEquippedImages.remove(equippedShield);
            } catch (Exception e) {
                System.out.print("No Shield Currently equipped");
            }
        }
        if (world.getCharacter().getWeapon() == null)   {
                try {
                    ImageView previouslyEquippedItem =  mapEquippedImages.get(equippedWeapon);
                    equippedWeapon.getChildren().remove(previouslyEquippedItem);
                    mapEquippedImages.remove(equippedWeapon);
                } catch (Exception e) {
                    System.out.print("No Weapon Currently equipped");
                }         
        }    
        if (world.getCharacter().getRing() == null) {
            try {
                ImageView previouslyEquippedItem = mapEquippedImages.get(the_one_ring_slot);
                the_one_ring_slot.getChildren().remove(previouslyEquippedItem);
                mapEquippedImages.remove(the_one_ring_slot);
            } catch (Exception e)   {
                System.out.println("No Ring currently equipped");
            }
        }  



    }



    /** 
     * Add a click event for potion consumption 
     */
    public void clickPotion(MouseEvent event,ImageView view)    {
        // Get Index of potion in LoopMania World unequipped Inventory
        int potionIndex = unequippedInventory.getChildren().indexOf(view);
        Node potionNode = unequippedInventory.getChildren().get(potionIndex);
        int nodeX = GridPane.getColumnIndex(potionNode);
        int nodeY = GridPane.getRowIndex(potionNode);
        unequippedInventory.getChildren().remove(view);
        getWorld().consumePotion(nodeX, nodeY);
        removeItemByCoordinates(nodeX, nodeY);
    }

    /**
     * load an enemy into the GUI
     * @param enemy
     */
    private void onLoad(BasicEnemy enemy) {
        ImageView view = enemy.getImage();
        addEntity(enemy, view);
        squares.getChildren().add(view);
    }

    /**
     * load a building into the GUI
     * @param building
     */
    private void onLoad(Building building){
        if (building != null) {
            ImageView view = building.getImage();
            addEntity(building, view);
            squares.getChildren().add(view);
        }
    }

    /**
     * load a alliedSoldier into the GUI
     * @param List<Ally>
     */
    private void onLoad(List<Ally> allies) {
        for (Ally ally: allies) {
            ImageView view = new ImageView(soldierImage);
            addEntity(ally, view);
            soldierGridPane.getChildren().add(view);
        }
    }

    /**
     * add drag event handlers for dropping into gridpanes, dragging over the background, dropping over the background.
     * These are not attached to invidual items such as swords/cards.
     * @param draggableType the type being dragged - card or item
     * @param sourceGridPane the gridpane being dragged from
     * @param targetGridPane the gridpane the human player should be dragging to (but we of course cannot guarantee they will do so)
     */
    private void buildNonEntityDragHandlers(DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        // for example, in the specification, villages can only be dropped on path, whilst vampire castles cannot go on the path

        gridPaneSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /*
                 *you might want to design the application so dropping at an invalid location drops at the most recent valid location hovered over,
                 * or simply allow the card/item to return to its slot (the latter is easier, as you won't have to store the last valid drop location!)
                 */
                if (currentlyDraggedType == draggableType) {
                    // problem = event is drop completed is false when should be true...
                    // https://bugs.openjdk.java.net/browse/JDK-8117019
                    // putting drop completed at start not making complete on VLAB...

                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != targetGridPane && db.hasImage()){

                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        int x = cIndex == null ? 0 : cIndex;
                        int y = rIndex == null ? 0 : rIndex;
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        ImageView image = new ImageView(db.getImage());
                        
                        int nodeX = GridPane.getColumnIndex(currentlyDraggedImage);
                        int nodeY = GridPane.getRowIndex(currentlyDraggedImage);
                        switch (draggableType){
                            case CARD:
                            removeDraggableDragEventHandlers(draggableType, targetGridPane);
                                Building newBuilding = convertCardToBuildingByCoordinates(nodeX, nodeY, x, y);
                                // newBuilding = removalist
                                //new building = null 
                                if (newBuilding == null) {
                                    onLoad(world.getSpesificCard(nodeX, nodeY, x, y));
                                } else {
                                    onLoad(newBuilding);
                                } 
                                break;
                            case ITEM:
                                 removeDraggableDragEventHandlers(draggableType, targetGridPane);

                                //removeItemByCoordinates(nodeX, nodeY); // Default add to inventory

                                // This takes an item and returns the item to load into inventory 
                                Item toLoad = equipItemByCoordinates(nodeX, nodeY);

                                if (toLoad instanceof Sword) onLoad((Sword)toLoad);
                                if (toLoad instanceof Staff) onLoad((Staff)toLoad);
                                if (toLoad instanceof Shield) onLoad((Shield)toLoad);
                                if (toLoad instanceof TreeStump) onLoad((TreeStump)toLoad);
                                if (toLoad instanceof Anduril) onLoad((Anduril)toLoad);
                                if (toLoad instanceof Armour) onLoad((Armour)toLoad);
                                if (toLoad instanceof Helmet) onLoad((Helmet)toLoad);
                                if (toLoad instanceof Stake) onLoad((Stake)toLoad);
                                
                                // This will try to remove any previously equipped items, note since they are 
                                // mapped from gridplane to ImageView, there exists a 1:1 mapping. 
                                try {
                                    ImageView previouslyEquippedItem =  mapEquippedImages.get(targetGridPane);
                                    targetGridPane.getChildren().remove(previouslyEquippedItem);
                                    mapEquippedImages.remove(targetGridPane);
                                    

                                } catch (Exception e) {
                                    System.out.print("No item previously in inventory");
                                   
                                }            

                                // Add the Gridpane and item to HashMap then add to the gridpane
                                // Note we need this to be able to remove the correct image later.
                                mapEquippedImages.put(targetGridPane, image);
                                targetGridPane.add(image, x, y, 1, 1);



                                System.out.print(targetGridPane.getChildren());
                                break;
                            default:
                                break;
                        }
                        
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                        printThreadingNotes("DRAG DROPPED ON GRIDPANE HANDLED");
                    }
                }
                event.setDropCompleted(true);
                // consuming prevents the propagation of the event to the anchorPaneRoot (as a sub-node of anchorPaneRoot, GridPane is prioritized)
                // https://openjfx.io/javadoc/11/javafx.base/javafx/event/Event.html#consume()
                // to understand this in full detail, ask your tutor or read https://docs.oracle.com/javase/8/javafx/events-tutorial/processing.htm
                event.consume();
            }
        });

        // this doesn't fire when we drag over GridPane because in the event handler for dragging over GridPanes, we consume the event
        anchorPaneRootSetOnDragOver.put(draggableType, new EventHandler<DragEvent>(){
            // https://github.com/joelgraff/java_fx_node_link_demo/blob/master/Draggable_Node/DraggableNodeDemo/src/application/RootLayout.java#L110
            @Override
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    if(event.getGestureSource() != anchorPaneRoot && event.getDragboard().hasImage()){
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                }
                if (currentlyDraggedType != null){
                    draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                }
                event.consume();
            }
        });

        // this doesn't fire when we drop over GridPane because in the event handler for dropping over GridPanes, we consume the event
        anchorPaneRootSetOnDragDropped.put(draggableType, new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (currentlyDraggedType == draggableType){
                    //Data dropped
                    //If there is an image on the dragboard, read it and use it
                    Dragboard db = event.getDragboard();
                    Node node = event.getPickResult().getIntersectedNode();
                    if(node != anchorPaneRoot && db.hasImage()){
                        //Places at 0,0 - will need to take coordinates once that is implemented
                        currentlyDraggedImage.setVisible(true);
                        draggedEntity.setVisible(false);
                        draggedEntity.setMouseTransparent(false);
                        // remove drag event handlers before setting currently dragged image to null
                        removeDraggableDragEventHandlers(draggableType, targetGridPane);
                        
                        currentlyDraggedImage = null;
                        currentlyDraggedType = null;
                    }
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    /**
     * remove the card from the world, and spawn and return a building instead where the card was dropped
     * @param cardNodeX the x coordinate of the card which was dragged, from 0 to width-1
     * @param cardNodeY the y coordinate of the card which was dragged (in starter code this is 0 as only 1 row of cards)
     * @param buildingNodeX the x coordinate of the drop location for the card, where the building will spawn, from 0 to width-1
     * @param buildingNodeY the y coordinate of the drop location for the card, where the building will spawn, from 0 to height-1
     * @return building entity returned from the world
     */
    private Building convertCardToBuildingByCoordinates(int cardNodeX, int cardNodeY, int buildingNodeX, int buildingNodeY) {
        return world.convertCardToBuildingByCoordinates(cardNodeX, cardNodeY, buildingNodeX, buildingNodeY);
    }

    /**
     * remove an item from the unequipped inventory by its x and y coordinates in the unequipped inventory gridpane
     * @param nodeX x coordinate from 0 to unequippedInventoryWidth-1
     * @param nodeY y coordinate from 0 to unequippedInventoryHeight-1
     */
    private void removeItemByCoordinates(int nodeX, int nodeY) {
        world.removeUnequippedInventoryItemByCoordinates(nodeX, nodeY);
    }


    /**
     * Equip an item from the unequipped Inventory by it's x and y coordinates in the unequipped inventory gridpane
     * @param nodeX
     * @param nodeY
     */
    private Item equipItemByCoordinates(int nodeX, int nodeY)    {
        return (world.equipInventoryItemByCoordinates(nodeX, nodeY));
    }


    /**
     * add drag event handlers to an ImageView
     * @param view the view to attach drag event handlers to
     * @param draggableType the type of item being dragged - card or item
     * @param sourceGridPane the relevant gridpane from which the entity would be dragged
     * @param targetGridPane the relevant gridpane to which the entity would be dragged to
     */
    private void addDragEventHandlers(ImageView view, DRAGGABLE_TYPE draggableType, GridPane sourceGridPane, GridPane targetGridPane){
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                currentlyDraggedImage = view; // set image currently being dragged, so squares setOnDragEntered can detect it...
                currentlyDraggedType = draggableType;
                //Drag was detected, start drap-and-drop gesture
                //Allow any transfer node
                Dragboard db = view.startDragAndDrop(TransferMode.MOVE);
    
                //Put ImageView on dragboard
                ClipboardContent cbContent = new ClipboardContent();
                cbContent.putImage(view.getImage());
                db.setContent(cbContent);
                view.setVisible(false);

                buildNonEntityDragHandlers(draggableType, sourceGridPane, targetGridPane);

                draggedEntity.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                switch (draggableType){
                    case CARD:
                        draggedEntity.setImage(view.getImage());
                        break;
                    case ITEM:
                        draggedEntity.setImage(view.getImage());
                        break;
                    default:
                        break;
                }
                
                draggedEntity.setVisible(true);
                draggedEntity.setMouseTransparent(true);
                draggedEntity.toFront();

                // IMPORTANT!!!
                // to be able to remove event handlers, need to use addEventHandler
                // https://stackoverflow.com/a/67283792
                targetGridPane.addEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
                anchorPaneRoot.addEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

                for (Node n: targetGridPane.getChildren()){
                    // events for entering and exiting are attached to squares children because that impacts opacity change
                    // these do not affect visibility of original image...
                    // https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
                    gridPaneNodeSetOnDragEntered.put(draggableType, new EventHandler<DragEvent>() {
                        // TODO = be more selective about whether highlighting changes - if it cannot be dropped in the location, the location shouldn't be highlighted!
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                            //The drag-and-drop gesture entered the target
                            //show the user that it is an actual gesture target
                                if(event.getGestureSource() != n && event.getDragboard().hasImage()){
                                    n.setOpacity(1);
                                }
                            }
                            event.consume();
                        }
                    });
                    gridPaneNodeSetOnDragExited.put(draggableType, new EventHandler<DragEvent>() {
                        public void handle(DragEvent event) {
                            if (currentlyDraggedType == draggableType){
                                n.setOpacity(1);
                            }
                
                            event.consume();
                        }
                    });
                    n.addEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
                    n.addEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
                }
                event.consume();
            }
            
        });
    }

    /**
     * remove drag event handlers so that we don't process redundant events
     * this is particularly important for slower machines such as over VLAB.
     * @param draggableType either cards, or items in unequipped inventory
     * @param targetGridPane the gridpane to remove the drag event handlers from
     */
    private void removeDraggableDragEventHandlers(DRAGGABLE_TYPE draggableType, GridPane targetGridPane){
        // remove event handlers from nodes in children squares, from anchorPaneRoot, and squares
        targetGridPane.removeEventHandler(DragEvent.DRAG_DROPPED, gridPaneSetOnDragDropped.get(draggableType));

        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_OVER, anchorPaneRootSetOnDragOver.get(draggableType));
        anchorPaneRoot.removeEventHandler(DragEvent.DRAG_DROPPED, anchorPaneRootSetOnDragDropped.get(draggableType));

        for (Node n: targetGridPane.getChildren()){
            n.removeEventHandler(DragEvent.DRAG_ENTERED, gridPaneNodeSetOnDragEntered.get(draggableType));
            n.removeEventHandler(DragEvent.DRAG_EXITED, gridPaneNodeSetOnDragExited.get(draggableType));
        }
    }

    /**
     * handle the pressing of keyboard keys.
     * Specifically, we should pause when pressing SPACE
     * @param event some keyboard key press
     */
    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case SPACE:
            if (isPaused){
                startTimer();
            }
            else{
                pause();
            }
            break;
        default:
            break;
        }
    }

    public void setDeadScreenSwitcher(MenuSwitcher deadScreen){
        this.deadScreen = deadScreen;
    }
    
    public void setShopSwitcher(MenuSwitcher toShopSwitcher){
        this.toShopSwitcher = toShopSwitcher;
    }

    public void setMainMenuSwitcher(MenuSwitcher mainMenuSwitcher){
        this.MenuSwitcher = mainMenuSwitcher;
    }

    public void setWinScreenSwitcher(MenuSwitcher winScreenSwitcher){
        this.winScreenSwitcher = winScreenSwitcher;
    }
    /**
     * this method is triggered when click button to go to main menu in FXML
     * @throws IOException
     */
    @FXML
    private void switchToMainMenu() throws IOException {
        pause();
        MenuSwitcher.switchMenu();
    }

    /**
     * triggered when the shop is open
     */
    public void switchToShop(){
        pause();
        toShopSwitcher.switchMenu();
    }

    /**
     * triggered when the character is dead
     */
    public void switchToDead(){
        pause();
        deadScreen.switchMenu();
    }

    /**
     * triggered when the goal is achieve
     */
    public void switchToWin(){
        pause();
        winScreenSwitcher.switchMenu();
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the world.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * 
     * note that this is put in the controller rather than the loader because we need to track positions of spawned entities such as enemy
     * or items which might need to be removed should be tracked here
     * 
     * NOTE teardown functions setup here also remove nodes from their GridPane. So it is vital this is handled in this Controller class
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        // TODO = tweak this slightly to remove items from the equipped inventory?
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());

        ChangeListener<Number> xListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        };
        ChangeListener<Number> yListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        };

        // if need to remove items from the equipped inventory, add code to remove from equipped inventory gridpane in the .onDetach part
        ListenerHandle handleX = ListenerHandles.createFor(entity.x(), node)
                                               .onAttach((o, l) -> o.addListener(xListener))
                                               .onDetach((o, l) -> {
                                                    o.removeListener(xListener);
                                                    entityImages.remove(node);
                                                    squares.getChildren().remove(node);
                                                    cards.getChildren().remove(node);
                                                    soldierGridPane.getChildren().remove(node);
                                                    equippedHelmet.getChildren().remove(node);
                                                    equippedArmour.getChildren().remove(node);
                                                    equippedShield.getChildren().remove(node);
                                                    equippedWeapon.getChildren().remove(node);
                                                    the_one_ring_slot.getChildren().remove(node);
                                                    unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        ListenerHandle handleY = ListenerHandles.createFor(entity.y(), node)
                                               .onAttach((o, l) -> o.addListener(yListener))
                                               .onDetach((o, l) -> {
                                                   o.removeListener(yListener);
                                                   entityImages.remove(node);
                                                   squares.getChildren().remove(node);
                                                   cards.getChildren().remove(node);
                                                   soldierGridPane.getChildren().remove(node);
                                                   equippedHelmet.getChildren().remove(node);
                                                   equippedArmour.getChildren().remove(node);
                                                   equippedShield.getChildren().remove(node);
                                                   equippedWeapon.getChildren().remove(node);
                                                   the_one_ring_slot.getChildren().remove(node);
                                                   unequippedInventory.getChildren().remove(node);
                                                })
                                               .buildAttached();
        handleX.attach();
        handleY.attach();

        // this means that if we change boolean property in an entity tracked from here, position will stop being tracked
        // this wont work on character/path entities loaded from loader classes
        entity.shouldExist().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> obervable, Boolean oldValue, Boolean newValue) {
                handleX.detach();
                handleY.detach();
            }
        });
    }

    /**
     * we added this method to help with debugging so you could check your code is running on the application thread.
     * By running everything on the application thread, you will not need to worry about implementing locks, which is outside the scope of the course.
     * Always writing code running on the application thread will make the project easier, as long as you are not running time-consuming tasks.
     * We recommend only running code on the application thread, by using Timelines when you want to run multiple processes at once.
     * EventHandlers will run on the application thread.
     */
    private void printThreadingNotes(String currentMethodLabel){
        System.out.println("\n###########################################");
        System.out.println("current method = "+currentMethodLabel);
        System.out.println("In application thread? = "+Platform.isFxApplicationThread());
        System.out.println("Current system time = "+java.time.LocalDateTime.now().toString().replace('T', ' '));
    }
}
