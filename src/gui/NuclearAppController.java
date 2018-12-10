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
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Calibration;
import model.Radiopharmaceutical;
import model.RegRadio;
import model.Room;
import model.Substance;
import model.Supplier;
import model.User;

public class NuclearAppController implements Initializable {

	private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> regRadioList = FXCollections.observableArrayList();
	
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
	public TableView<RegRadio> tableview = new TableView<>();
	
	public CheckBox check_kontamineringskontroll = new CheckBox();
	public Button saveButton = new Button();
	
	@FXML
	TableColumn<RegRadio, Date> columnAnkomstdatum;
	@FXML
	TableColumn<RegRadio, Double> columnActivity;
	@FXML
	TableColumn<RegRadio, Supplier> columnSupplier;
	@FXML
	TableColumn<RegRadio, Radiopharmaceutical> columnRadiopharmaceutical;
	@FXML
	TableColumn<RegRadio, Date> columnCalibrationdate;
	@FXML
	TableColumn<RegRadio, String> columnTime;
	@FXML
	TableColumn<RegRadio, String> columnBatchNumber;
	@FXML
	TableColumn<RegRadio, String> columnContaminationControl;
	@FXML
	TableColumn<RegRadio, String> columnContaminationControlComment;
	@FXML
	TableColumn<RegRadio, Room> columnRoom;
	
	public void addSuppliersToComboBox() {
		supplierList.addAll(new SupplierDao().getAll());
		combobox_suppliers.getItems().addAll(supplierList);
	}
	
	
	public void addRooms() {
		combobox_room.getItems().addAll(FXCollections.observableArrayList(new RoomDao().getAll()));
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
			if(newValue != null) {
			label_rad_substance.setText(newValue.getSubstance().getName());
			label_halftime.setText(newValue.getSubstance().getHalfLife()+"");
			}
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
		
		saveButton.setOnAction((event)->{
			RegRadio rr = new RegRadio(getActivity(), getCalibrationDate(), getArrivalDate(), text_batchnr.getText(), 
					getContaminationControl(), combobox_radio.getValue(), combobox_room.getValue(), new User("CK"), 
					new Calibration(getCalibrationDate(), 66.6), combobox_suppliers.getValue(), getTime(), getContaminationControlComment());
			
			columnAnkomstdatum.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
			columnSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
			columnRadiopharmaceutical.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
			columnActivity.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
			columnCalibrationdate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
			columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
			columnBatchNumber.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
			columnContaminationControl.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
			columnContaminationControlComment.setCellValueFactory(new PropertyValueFactory<>("contaminationControlComment"));
			columnRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
			
			regRadioList.add(0,rr);
			
			tableview.setItems(regRadioList);
		});
		
	
		
	}
	
	
	
	
	public double getActivity() {
		return Double.parseDouble(text_kalibreringsaktivitet.getText());
	}
	public Date getCalibrationDate() {
		return java.sql.Date.valueOf(kalibreringsdatum.getValue());
	}
	public Date getArrivalDate() {
		return java.sql.Date.valueOf(ankomstdatum.getValue());
	}
	public String getContaminationControl() {
		return check_kontamineringskontroll.isSelected() ? "OK":"Ej OK";
	}
	public String getContaminationControlComment() {
		String comment = text_kommentar.getText();
		return comment.isEmpty()?"":comment;
	}
	public String getTime() {
		String time = text_kalibreringstid.getText();
			return time.replace(":", "");
		
	}
	

}
