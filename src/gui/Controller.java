package gui;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import dao.RadiopharmaceuticalDao;
import dao.SupplierDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import model.Radiopharmaceutical;
import model.Supplier;

public class Controller implements Initializable {

	public DatePicker ankomstdatum = new DatePicker();
	public ComboBox<Supplier> combobox_suppliers = new ComboBox<>();
	private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
	public ComboBox<Radiopharmaceutical> combobox_radio = new ComboBox<>();
	private ObservableList<Radiopharmaceutical> radioList = FXCollections.observableArrayList();
	public ListView<String> listView = new ListView<String>();
	public Button button = new Button();
	
	
	
	
	public void addSuppliers() {
		supplierList.addAll(new SupplierDao().getAll());
		combobox_suppliers.getItems().addAll(supplierList);
	}
	
	public void addProducts() {
		combobox_radio.setDisable(false);
		radioList.clear();
		radioList.addAll(new RadiopharmaceuticalDao().getRadiopharmaceuticalsBySupplierName(combobox_suppliers.getValue().toString()));
		combobox_radio.getItems().clear();
		combobox_radio.getItems().addAll(radioList);
		combobox_radio.getSelectionModel().selectFirst();

	}
	
	
	public void disableElements() {
		
	}

	public String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addSuppliers();
		ankomstdatum.setPromptText(getCurrentDate());
		combobox_radio.setDisable(true);
	
		
		
	}
	

}
