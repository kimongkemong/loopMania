package unsw.loopmania;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;

import unsw.loopmania.Modes.GameMode;
import unsw.loopmania.Shops.*;

/**
 * controller for the main menu.
 */
public class ShopMenuController{
    @FXML
    private GridPane shopWindow;

    @FXML
    private GridPane Sword;

    @FXML
    private Button SwordButton;

    @FXML
    private Label SwordPrice;

    @FXML
    private GridPane Armour;

    @FXML
    private Button ArmourButton;

    @FXML
    private Label ArmourPrice;

    @FXML
    private GridPane Helmet;

    @FXML
    private Button HelmetButton;

    @FXML
    private Label HelmetPrice;

    @FXML
    private GridPane Shield;

    @FXML
    private Label ShieldPrice;

    @FXML
    private Button ShieldButton;

    @FXML
    private GridPane Stake;

    @FXML
    private Button StakeButton;

    @FXML
    private Label StakePrice;

    @FXML
    private GridPane Staff;

    @FXML
    private Button StaffButton;

    @FXML
    private Label StaffPrice;

    @FXML
    private Button ShopButton;

    @FXML
    private GridPane HealthPotion;

    @FXML
    private Button HealthPotionButton;

    @FXML
    private Label HealthPrice;

    @FXML
    private GridPane Gold;

    @FXML
    private Label GoldAmountShop;

    @FXML
    private Button QuitButton;

    @FXML
    private Label shield_amt;

    @FXML
    private Label helmet_amt1;

    @FXML
    private Label potion_amt;

    @FXML
    private Label sword_amt;

    @FXML
    private Label stake_amt;

    @FXML
    private Label staff_amt;

    @FXML
    private Label armour_amt;

    @FXML
    private Label gameModeLabelShop;

    @FXML
    private Button DoggieCoinButton;

    @FXML
    private Label dogePrice;

    @FXML
    private ListView<String> shoppingCart;

    @FXML
    private ListView<String> soldCart;

    private Shop shop;
    private GameMode gameMode;
    private LoopManiaWorld world;

    private Sword sword;
    private Stake stake;
    private Staff staff;
    private Shield shield;
    private Helmet helmet;
    private HealthPotion healthPotion;
    private Armour armour;
    private DoggieCoin doggieCoin;

    private BackgroundImage myBI= new BackgroundImage(new Image((new File("src/images/listBackground.png")).toURI().toString()),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);

    private BackgroundImage myBI2= new BackgroundImage(new Image((new File("src/images/listBackground.png")).toURI().toString()),
          BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
    
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher backToGameSwitcher;

    public ShopMenuController(LoopManiaWorld world) {
        this.world = world;
        // shop = new BuyShop(gameMode);

        //DUMMY ITEM
        sword = new Sword(null, null);
        stake = new Stake(null, null);
        staff = new Staff(null, null);
        armour = new Armour(null, null);
        shield = new Shield(null, null);
        helmet = new Helmet(null, null);
        healthPotion = new HealthPotion(null, null);
        doggieCoin = new DoggieCoin(null, null);
    }
    
    @FXML
    public void initialize() {
        GoldAmountShop.textProperty().bindBidirectional(world.getCharacter().getGoldProperty());
        shoppingCart.setBackground(new Background(myBI));
        soldCart.setBackground(new Background(myBI2));
        changeBuyPrice();
    }
    
    public void setToGameSwitcher(MenuSwitcher toShopSwitcher){
        this.backToGameSwitcher = toShopSwitcher;
    }
    
    @FXML
    void ShieldPressed(ActionEvent event) {
        String itemToBasket = shop.addItemToBasket(world.getUpdateItems(), shield, world.getCharacter(), world.getUnequippedInventory());
        addItemToCartList(itemToBasket);
        CharacterGoldUpdate();
    }
    
    @FXML
    void armourPressed(ActionEvent event) {
        String itemToBasket = shop.addItemToBasket(world.getUpdateItems(), armour, world.getCharacter(), world.getUnequippedInventory());
        addItemToCartList(itemToBasket);
        CharacterGoldUpdate();
    }
    
    @FXML
    void healthPotionPressed(ActionEvent event) {
        String itemToBasket = shop.addItemToBasket(world.getUpdateItems(), healthPotion, world.getCharacter(), world.getUnequippedInventory());
        addItemToCartList(itemToBasket);
        CharacterGoldUpdate();
    }

    @FXML
    void helmetPress(ActionEvent event) {
        String itemToBasket = shop.addItemToBasket(world.getUpdateItems(), helmet, world.getCharacter(), world.getUnequippedInventory());
        addItemToCartList(itemToBasket);
        CharacterGoldUpdate();
    }
    
    @FXML
    void staffPressed(ActionEvent event) {
        String itemToBasket = shop.addItemToBasket(world.getUpdateItems(), staff, world.getCharacter(), world.getUnequippedInventory());
        addItemToCartList(itemToBasket);
        CharacterGoldUpdate();
    }
    
    @FXML
    void stakePressed(ActionEvent event) {
        String itemToBasket = shop.addItemToBasket(world.getUpdateItems(), stake, world.getCharacter(), world.getUnequippedInventory());
        addItemToCartList(itemToBasket);
        CharacterGoldUpdate();
    }
    
    @FXML
    void switchToGame(ActionEvent event) {  //QuitButton
        backToGameSwitcher.switchMenu();
    }
    
    @FXML
    void swordPressed(ActionEvent event) {
        String itemToBasket = shop.addItemToBasket(world.getUpdateItems(), sword, world.getCharacter(), world.getUnequippedInventory());
        addItemToCartList(itemToBasket);
        CharacterGoldUpdate();
    }
    
    
    @FXML
    void doggiePressed(ActionEvent event) {
        String itemToBasket = shop.addItemToBasket(world.getUpdateItems(), doggieCoin, world.getCharacter(), world.getUnequippedInventory());
        addItemToCartList(itemToBasket);
        CharacterGoldUpdate();
    }
    
    @FXML
    void shopButtonPressed(ActionEvent event) {
        if(ShopButton.getText().equals("Buy")){
            // shop = new BuyShop(gameMode);
            shop.setCurrentState(new BuyShop(world.getGameMode()));
            changeBuyPrice();
            ShopButton.setText("Sell");
        } else {
            // shop = new SellShop(gameMode);
            shop.setCurrentState(new SellShop(world.getGameMode()));
            changeSellPrice();
            ShopButton.setText("Buy");
        }
    }

    public void changeBuyPrice(){
        SwordPrice.setText(Integer.toString(sword.getCost()) + " COINS");
        HelmetPrice.setText(Integer.toString(helmet.getCost()) + " COINS");
        ShieldPrice.setText(Integer.toString(shield.getCost()) + " COINS");
        StakePrice.setText(Integer.toString(stake.getCost()) + " COINS");
        StaffPrice.setText(Integer.toString(staff.getCost()) + " COINS");
        HealthPrice.setText(Integer.toString(healthPotion.getCost()) + " COINS");
        ArmourPrice.setText(Integer.toString(armour.getCost()) + " COINS");
        dogePrice.setText("NOT FOR SALE");

    }
    
    public void changeSellPrice(){
        SwordPrice.setText(Integer.toString(sword.getSellPrice()) + " COINS");
        HelmetPrice.setText(Integer.toString(helmet.getSellPrice()) + " COINS");
        ShieldPrice.setText(Integer.toString(shield.getSellPrice()) + " COINS");
        StakePrice.setText(Integer.toString(stake.getSellPrice()) + " COINS");
        StaffPrice.setText(Integer.toString(staff.getSellPrice()) + " COINS");
        HealthPrice.setText("PRICELESS");
        ArmourPrice.setText(Integer.toString(armour.getSellPrice()) + " COINS");        
        dogePrice.setText(Integer.toString(doggieCoin.getSellPrice()) + " COINS");

    }
    
    public void CharacterGoldUpdate(){
        GoldAmountShop.setText(Integer.toString(world.getCharacter().getGold()));
    }
    
    public void setGameModes(GameMode mode) {
        this.gameMode = mode;
    }
    
    public void CreateShop(GameMode mode) {
        //by default is BuyShop
        shop = new Shop(world.getGameMode());       
        System.out.println(world.getGameMode().toString());
        gameModeLabelShop.setText(world.getGameMode().getClass().getSimpleName());
        for (Item i: world.getUnequippedInventory()) {
            soldCart.getItems().add(i.getItemName());
        }
    }

    private void addItemToCartList(String itemToAdd) {
        if (itemToAdd == null) return;
        if (shop.getCurrentState() instanceof BuyShop) {
            shoppingCart.getItems().add(itemToAdd);
        } else {
            soldCart.getItems().remove(itemToAdd);
        }
    }
}