package unsw.loopmania;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HowToMenuController {
    @FXML
    private Button BackButton;

    private MenuSwitcher menuSwitcher;

    
    public void setMenuSwitcher(MenuSwitcher menuSwitcher){
        this.menuSwitcher = menuSwitcher;
    }
    
    @FXML
    void backButtonPressed(ActionEvent event) {
        menuSwitcher.switchMenu();

    }
}
