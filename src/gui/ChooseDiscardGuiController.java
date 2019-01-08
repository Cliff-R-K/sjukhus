package gui;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


import dao.RegRadioDao;
import dao.RoomDao;
import dao.UserDao;
import dataholder.DataHolder;
import db.DbConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.RegRadio;
import model.Room;
import model.User;


public class ChooseDiscardGuiController implements Initializable {


	public Button saveButton = new Button();
	public Button abortButton = new Button();
	public ComboBox<Room> discardComboBox = new ComboBox<Room>();
	private RegRadio chosenRadio;
	private List<Room> roomList = new ArrayList<>();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chosenRadio = DataHolder.getSavedRadio();
		System.out.println(chosenRadio);
		roomList= new RoomDao().getTrash();
		discardComboBox.getItems().addAll(roomList);
	}
	public void clickedSaveButton() {
		System.out.println("Before: " +chosenRadio.getRoom().toString());
		
		
		
		Alert saveAlert = new Alert(AlertType.CONFIRMATION);
		saveAlert.setHeaderText(null);
		saveAlert.setTitle("Avbryt");
		saveAlert.setContentText("Vill du spara kassering?");
		Optional<ButtonType> result = saveAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			
			chosenRadio.setRoom(discardComboBox.getValue());
			new RegRadioDao().update(chosenRadio, new String[] { "rooms_idroom" });
			
			Stage stage = (Stage) abortButton.getScene().getWindow();
			stage.close();
		}
		
		
		
	}

	public void clickedAbortButton() {
		System.out.println("abort");
			Stage stage = (Stage) abortButton.getScene().getWindow();
			stage.close();
//		}
	}
}