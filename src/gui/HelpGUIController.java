package gui;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelpGUIController {
	public Button closeButtonTab1 = new Button();
	public Button closeButtonTab2 = new Button();
	public Button closeButtonTab3 = new Button();
	ActionEvent event;

	public void clickedAbortButton(ActionEvent exit) {
		this.event = exit;
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
}
	
}
//Välj på vilket sätt läkemedlet kastas på i listan och tryck sedan på spara.
//Ditt val kommer inte gå att ångra efter att du tryckt på spara.