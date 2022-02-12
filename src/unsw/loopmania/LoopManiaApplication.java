 package unsw.loopmania;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import unsw.loopmania.Modes.ConfusingMode;

/**
 * the main application
 * run main method from this class
 */
public class LoopManiaApplication extends Application {
    /**
     * the controller for the game. Stored as a field so can terminate it when click exit button
     */
    private LoopManiaWorldController mainController;
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // set title on top of window bar
        primaryStage.setTitle("Loop Mania");

        // prevent human player resizing game window (since otherwise would see white space)
        // alternatively, you could allow rescaling of the game (you'd have to program resizing of the JavaFX nodes)
        primaryStage.setResizable(false);

        // load the main game
        LoopManiaWorldControllerLoader loopManiaLoader = new LoopManiaWorldControllerLoader("world_with_twists_and_turns.json");
        mainController = loopManiaLoader.loadController();
        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("LoopManiaView.fxml"));
        gameLoader.setController(mainController);
        Parent gameRoot = gameLoader.load();

        // load the main menu
        MainMenuController mainMenuController = new MainMenuController();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("MainMenuView.fxml"));
        menuLoader.setController(mainMenuController);
        Parent mainMenuRoot = menuLoader.load();

        ShopMenuController shopMenuController = new ShopMenuController(mainController.getWorld());
        FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("ShopMenuView.fxml"));
        shopLoader.setController(shopMenuController);
        Parent shopRoot = shopLoader.load();

        //Dead Screen
        DeadScreenController deadScreenController = new DeadScreenController();
        FXMLLoader deadLoader = new FXMLLoader(getClass().getResource("DeadScreenView.fxml"));
        deadLoader.setController(deadScreenController);
        Parent deadScreenRoot = deadLoader.load();

        //How To play Screen
        HowToMenuController howToMenuController= new HowToMenuController();
        FXMLLoader howToLoader = new FXMLLoader(getClass().getResource("HowtoPlayMenuView.fxml"));
        howToLoader.setController(howToMenuController);
        Parent howToRoot = howToLoader.load();

        //Victory Screen
        WinScreenController winScreenController = new WinScreenController();
        FXMLLoader winLoader = new FXMLLoader(getClass().getResource("WinScreenView.fxml"));
        winLoader.setController(winScreenController);
        Parent winScreenRoot = winLoader.load();

        // create new scene with the main menu (so we start with the main menu)
        Scene scene = new Scene(mainMenuRoot);
        
        // set functions which are activated when button click to switch menu is pressed
        // e.g. from main menu to start the game, or from the game to return to main menu
        mainController.setMainMenuSwitcher(() -> {
            switchToRoot(scene, mainMenuRoot, primaryStage);
            if (mainController.getWorld().getGameMode() instanceof ConfusingMode) {
                mainController.getWorld().getCharacter().setConfused(true);
            }
        });
        mainMenuController.setHowToSwitcher(() -> {switchToRoot(scene, howToRoot, primaryStage);});
        mainMenuController.setGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.getWorld().setGameMode(mainMenuController.getGameMode());
            // shopMenuController.setGameModes(mainMenuController.getGameMode());
            mainController.startTimer();
        });

        //shop
        mainController.setShopSwitcher(() -> {
            switchToRoot(scene, shopRoot, primaryStage);
            shopMenuController.CreateShop(mainMenuController.getGameMode());
        });
        shopMenuController.setToGameSwitcher(() -> {
            switchToRoot(scene, gameRoot, primaryStage);
            mainController.startTimer();
        });
        
        //Dead Screen and Restart Game
        mainController.setDeadScreenSwitcher(() -> {switchToRoot(scene, deadScreenRoot, primaryStage);});
        deadScreenController.setMenuSwitcher(() -> {
            mediaPlayer.stop();
            primaryStage.close();
            try {
                start(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Win Screen and go back to Main Screen
        mainController.setWinScreenSwitcher(() -> {switchToRoot(scene, winScreenRoot, primaryStage);});
        winScreenController.setMenuSwitcher(() -> {
            mediaPlayer.stop();
            primaryStage.close();
            try {
                start(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //howToPlay menu setSwitcher
        howToMenuController.setMenuSwitcher(() -> {switchToRoot(scene, mainMenuRoot, primaryStage);});
        
        // deploy the main onto the stage
        playMusic();
        gameRoot.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop(){
        // wrap up activities when exit program
        mainController.terminate();
    }

    /**
     * switch to a different Root
     */
    private void switchToRoot(Scene scene, Parent root, Stage stage) {
        scene.setRoot(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    /**
     * Music Game
     */
    private void playMusic() {
        Media m = new Media((new File("src/images/gameMusic.mp3")).toURI().toString());
        mediaPlayer = new MediaPlayer(m);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }


    public static void main(String[] args) {
        launch(args);
        
    }
}