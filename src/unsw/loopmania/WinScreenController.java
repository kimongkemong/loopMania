package unsw.loopmania;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WinScreenController {
    private MenuSwitcher menuSwitcher;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Button quitButton;

    public void setMenuSwitcher(MenuSwitcher menuSwitcher){
        this.menuSwitcher = menuSwitcher;
    }
    
    @FXML
    void mainMenu(ActionEvent event) {
        menuSwitcher.switchMenu();
    }

    @FXML
    void quitGame(ActionEvent event) {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }
}
