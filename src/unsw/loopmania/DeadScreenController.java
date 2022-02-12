package unsw.loopmania;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeadScreenController {
    
    @FXML
    private Button restartButton;
    
    @FXML
    private Button quitButton;

    private MenuSwitcher menuSwitcher;

    public void setMenuSwitcher(MenuSwitcher menuSwitcher){
        this.menuSwitcher = menuSwitcher;
    }

    @FXML
    void quitGame(ActionEvent event) {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void restartGame(ActionEvent event) {
        menuSwitcher.switchMenu();
    }

}
