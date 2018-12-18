package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.sun.istack.internal.logging.Logger;

import controller.MathController;
import dao.CalibrationDao;
import dao.RadiopharmaceuticalDao;
import dao.RegRadioDao;
import dao.RoomDao;
import dao.SupplierDao;
import dao.UserDao;
import dataholder.DataHolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Calibration;
import model.Radiopharmaceutical;
import model.RegRadio;
import model.Room;
import model.Supplier;
import model.User;

public class SetActivityController implements Initializable {

	public TableView<RegRadio> tableView = new TableView<>();

	public Button saveButton = new Button();
	public Button button = new Button();
	public Button logOutButton = new Button();

	private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
	private ObservableList<Radiopharmaceutical> radioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> regRadioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> editRegRadioList = FXCollections.observableArrayList();

	public DatePicker arrivalDatePicker = new DatePicker();
	public DatePicker calibrationDatePicker = new DatePicker();

	public ComboBox<Supplier> comboBoxSuppliers = new ComboBox<>();
	public ComboBox<Radiopharmaceutical> comboBoxRadios = new ComboBox<>();
	public ComboBox<Room> comboBoxRooms = new ComboBox<>();
	
	public ChoiceBox<String> choiceBoxActivity = new ChoiceBox<>();

	public Label label_rad_substance = new Label();
	public Label label_halftime = new Label();
	public Label signatur = new Label();

	public TextField startActivityTextField = new TextField();
	public TextField calibrationTextField = new TextField();
	public TextField calibrationTimeTextField = new TextField();
	public TextField batchNrTextField = new TextField();
	public TextField commentaryTextField = new TextField();
	public TextField setActivityText = new TextField();
	public Label calibrationText = new Label();
	public Label regRadioName = new Label();

	
	public ListView<String> listView = new ListView<String>();

	public CheckBox checkBoxContamination = new CheckBox();

	private User user;

	public Button editButton = new Button();
	public Button closeButton = new Button();
	public Button saveEditButton = new Button();
	public RegRadio chosenRegRadio;

	private ActionEvent event;

	private RegRadio regRadio;

	private String regRadioNameText;

	private Double calculatedActivity;

	public void addSuppliers() {
		supplierList.addAll(new SupplierDao().getAll());
		comboBoxSuppliers.getItems().addAll(supplierList);
	}

	public void addRooms() {
		comboBoxRooms.getItems().addAll(FXCollections.observableArrayList(new RoomDao().getAll()));
	}

	public void addProducts() {
		comboBoxRadios.setDisable(false);
		radioList.clear();
		radioList.addAll(new RadiopharmaceuticalDao()
				.getRadiopharmaceuticalsBySupplierName(comboBoxSuppliers.getValue().toString()));
		comboBoxRadios.getItems().clear();
		comboBoxRadios.getItems().addAll(radioList);
		comboBoxRadios.getSelectionModel().selectFirst();
	}

	public void addUser() { 
		user = new UserDao().getCurrent(1);
	}
	
	public void addRegRadio(RegRadio chosenRegRadio) {
		this.chosenRegRadio = chosenRegRadio;
	}
	public RegRadio getRegRadio() {
		return chosenRegRadio;
		
	}

	public String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DecimalFormat df = new DecimalFormat("#.00");
		chosenRegRadio = DataHolder.getSavedRadio();
		regRadioName.setText(chosenRegRadio.getRadiopharmaceutical().getRadiopharmaceuticalName());
		calibrationText.setText(getCurrentDate());
		choiceBoxActivity.getItems().addAll("Manuell", "Automatisk");
		choiceBoxActivity.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				if(newValue.intValue()==0) {
				setActivityText.editableProperty();
				saveButton.setDisable(false);
				}
				if(newValue.intValue()==1) {
					System.out.println("Automatisk");
					
					}
			}else {
				System.out.print(newValue);
			}
		});

		saveButton.setOnAction((event) -> {
			MathController mathController = new MathController();
			calculatedActivity = mathController.execute(chosenRegRadio);
			chosenRegRadio.setCalibrationActivity(calculatedActivity);
			chosenRegRadio.setUser(new UserDao().getCurrent(1));
			new RegRadioDao().update(chosenRegRadio, new String[]{"calibration_activity", "users_iduser"});
			System.out.println(calculatedActivity);
			setActivityText.setText("Aktivitet: "+df.format(chosenRegRadio.getCalibrationActivity())+ " "
					+ "Datum: "+chosenRegRadio.getCalibrationDate());
		});

	}
	public String getRegRadioName() {
		return regRadioNameText = chosenRegRadio.getRadiopharmaceutical().getRadiopharmaceuticalName();
	}
	
	public double getActivity() {
		return Double.parseDouble(calibrationTextField.getText().replace(",", "."));
	}

	public LocalDateTime getCalibrationDate(){
		LocalDate date = calibrationDatePicker.getValue();
		LocalTime time = LocalTime.parse(getTime(), DateTimeFormatter.ofPattern("HHmm"));
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		return dateTime;
	}

	public Date getArrivalDate() {
		return java.sql.Date.valueOf(arrivalDatePicker.getValue());
	}

	public String getContaminationControl() {
		return checkBoxContamination.isSelected() ? "OK" : commentaryTextField.getText();
	}

	public String getTime() {
		String time = calibrationTimeTextField.getText();
		return time.replace(":", "");
	}


	public void closeEditGui(ActionEvent event) throws Exception {

		System.out.println("close");
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();

		// do what you have to do
		stage.close();
		System.out.println("end close");
	}

	public void setNewInfo() {
	
	}
	/*public void setMbQActivity(RegRadio chosenRegRadio) {
		this.chosenRegRadio = chosenRegRadio;
		MathController mathController = new MathController();
		calculatedActivity = mathController.execute(chosenRegRadio);
		chosenRegRadio.setCalibrationActivity(calculatedActivity);
		chosenRegRadio.setUser(new UserDao().getCurrent(1));
		new RegRadioDao().update(chosenRegRadio, new String[]{"calibration_activity", "users_iduser"});
		System.out.println(calculatedActivity);
	}*/

	public double getMbQActivity() {
		System.out.println(calculatedActivity);
		return calculatedActivity;
	}
}


