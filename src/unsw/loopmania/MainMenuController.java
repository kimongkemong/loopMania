package unsw.loopmania;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import unsw.loopmania.Modes.BerserkerMode;
import unsw.loopmania.Modes.ConfusingMode;
import unsw.loopmania.Modes.GameMode;
import unsw.loopmania.Modes.StandardMode;
import unsw.loopmania.Modes.SurvivalMode;

/**
 * controller for the main menu.
 */
public class MainMenuController implements Initializable{
    /**
     * facilitates switching to main game
     */
    private MenuSwitcher gameSwitcher;
    private MenuSwitcher howToSwitcher;

    private String[] gamemodes = {"Standard Mode", "Survival Mode", "Berserker Mode", "CONFUSING Mode"};
    
    @FXML
    private ChoiceBox<String> ChooseGameMode;
    
    @FXML
    private Button howToButton;

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }

    public void setHowToSwitcher(MenuSwitcher howToSwitcher){
        this.howToSwitcher = howToSwitcher;
    }

    @FXML
    void howToPressed(ActionEvent event) {
        howToSwitcher.switchMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //obj.getClass().getSimpleName();
        ChooseGameMode.getItems().addAll(gamemodes);
    }

    public GameMode getGameMode() {
        String mode = ChooseGameMode.getValue();
        if (mode == null) {
            return new StandardMode();
        }
        if (mode.equals("Survival Mode")) {
            return new SurvivalMode();
        } else if (mode.equals("Berserker Mode")) {
            return new BerserkerMode();
        } else if(mode.equals("CONFUSING Mode")) {
            return new ConfusingMode();
        }
        return new StandardMode();
    }
}
