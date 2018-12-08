package gui;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import dao.RadiopharmaceuticalDao;
import dao.RoomDao;
import dao.SupplierDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Radiopharmaceutical;
import model.Room;
import model.Supplier;

public class Controller implements Initializable {

	private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
	private ObservableList<Radiopharmaceutical> radioList = FXCollections.observableArrayList();
	
	public DatePicker ankomstdatum = new DatePicker();
	public DatePicker kalibreringsdatum = new DatePicker();
	
	public ComboBox<Supplier> combobox_suppliers = new ComboBox<>();
	public ComboBox<Radiopharmaceutical> combobox_radio = new ComboBox<>();
	public ComboBox<Room> combobox_room = new ComboBox<>();
	
	public Label label_rad_substance = new Label();
	public Label label_halftime = new Label();
	
	public TextField text_kalibreringsaktivitet = new TextField();
	public TextField text_kalibreringstid = new TextField();
	public TextField text_batchnr = new TextField();
	public TextField text_kommentar = new TextField();
	public ListView<String> listView = new ListView<String>();
	
	public CheckBox check_kontamineringskontroll = new CheckBox();
	public Button button = new Button();
	

	
	public void addSuppliersToComboBox() {
		supplierList.addAll(new SupplierDao().getAll());
		combobox_suppliers.getItems().addAll(supplierList);
	}
	
	
	public void addRooms() {
		combobox_room.getItems().addAll(FXCollections.observableArrayList(new RoomDao().getAll()));
	}
	
	public void ContaminationCheck(){
	}
	
	public void disableElements() {
		
	}

	public String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addSuppliersToComboBox();
		addRooms();
		ankomstdatum.setValue(LocalDate.now());
		combobox_radio.setDisable(true);
		
		combobox_suppliers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
			combobox_radio.getItems().clear();
			combobox_radio.getItems().addAll(FXCollections.observableArrayList(new RadiopharmaceuticalDao().getRadiopharmaceuticalsBySupplierName(newValue.toString())));
			combobox_radio.setDisable(false);
			combobox_radio.getSelectionModel().selectFirst();
		});
		
		combobox_radio.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
			System.out.println(newValue.toString());
			System.out.println(newValue.getSubstance());
			label_rad_substance.setText(newValue.getSubstance().getName());
			label_halftime.setText(newValue.getSubstance().getHalfLife()+"");
		});
		text_kalibreringstid.focusedProperty().addListener((observable, oldText, newText)->{
			if(!newText) {
				if(!text_kalibreringstid.getText().matches("^(0[0-9]|1[0-9]|2[0-3]):?[0-5][0-9]$")) {
					text_kalibreringstid.setText("");
					text_kalibreringstid.setPromptText("Felaktig tid");
				}
			}
		});
		check_kontamineringskontroll.selectedProperty().addListener((obs, oldValue, newValue)->{
			if(newValue)
				text_kommentar.setDisable(true);
			else {
				text_kommentar.setDisable(false);
			}
		});
		
		
	}
	

}
