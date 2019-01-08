package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AboutWindowController implements Initializable{
	public Button closeButton = new Button();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		closeButton.setVisible(true);
	}
	public void clickedAbortButton() {
			Stage stage = (Stage) closeButton.getScene().getWindow();
			stage.close();
	}
}

