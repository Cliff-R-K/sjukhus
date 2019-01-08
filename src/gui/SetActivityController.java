package gui;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import controller.MathController;
import dao.RegRadioDao;
import dao.UserDao;
import dataholder.DataHolder;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Radiopharmaceutical;
import model.RegRadio;
import model.Room;
import model.Supplier;

public class SetActivityController implements Initializable {

	public TableView<RegRadio> tableView = new TableView<>();

	public Button saveButton = new Button();
	public Button button = new Button();
	public Button logOutButton = new Button();
	public Button calibrationButton = new Button();
	public Button closeButton = new Button();

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
	public TextField calibrationInput = new TextField();
	public TextField calibrationTimeTextField = new TextField();
	public TextField batchNrTextField = new TextField();
	public TextField commentaryTextField = new TextField();
	public Label label_show_activity = new Label();
	public TextField text_activity = new TextField();
	public Label calibrationText = new Label();
	public Label regRadioInfo = new Label();

	public ListView<String> listView = new ListView<String>();

	public CheckBox checkBoxContamination = new CheckBox();
	public CheckBox check_auto_calibration = new CheckBox();

	public Button editButton = new Button();
	public Button saveEditButton = new Button();
	public RegRadio chosenRegRadio;

	private String regRadioInfoText;

	private Double calculatedActivity;
	private Double userInputActivity;

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
	public void handleCloseButtonAction(ActionEvent event) {
		Alert saveAlert = new Alert(AlertType.CONFIRMATION);
		saveAlert.setHeaderText(null);
		saveAlert.setTitle("Avbryt");
		saveAlert.setContentText("Vill du avbryta aktiviteten?");
	    Optional<ButtonType> result = saveAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Stage stage = (Stage) closeButton.getScene().getWindow();
		    stage.close();
		}
	}
	
	public void handleSaveButtonAction(ActionEvent event) {
		new RegRadioDao().update(chosenRegRadio, new String[] { "calibration_activity", "users_iduser" });
		Alert saveAlert = new Alert(AlertType.CONFIRMATION);
		saveAlert.setHeaderText(null);
		saveAlert.setTitle("Spara");
		saveAlert.setContentText("Vill du spara aktiviteten?");

		Optional<ButtonType> result = saveAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Stage stage = (Stage) saveButton.getScene().getWindow();
		    stage.close();
		}
		}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DecimalFormat df = new DecimalFormat("#.000");
		chosenRegRadio = DataHolder.getSavedRadio();
		regRadioInfo.setText(getRegRadioInfo());
		calibrationText.setText(getCurrentDate());
		saveButton.setDisable(true);
		calibrationButton.setDisable(true);
		calibrationTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			calibrationButton.setDisable(false);
		});		
		check_auto_calibration.selectedProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue) {
				calibrationTextField.setDisable(true);
				calibrationButton.setDisable(true);
				setMbQActivity(chosenRegRadio);
				label_show_activity.setText("Aktivitet: " + df.format(chosenRegRadio.getCalibrationActivity()) + " MBq, "
						+ "Datum: " + getCalibrationDate());
				saveButton.setDisable(false);			
			} else {
				calibrationTextField.setDisable(false);
			}
		});
		calibrationButton.setOnAction((event) -> {
			chosenRegRadio.setCalibrationActivity(getActivity());
			chosenRegRadio.setCalibrationsDate();
			label_show_activity.setText("Aktivitet: " + df.format(chosenRegRadio.getCalibrationActivity()) + " "
					+ "Datum: " + getCalibrationDate());
		});
	}

	public String getRegRadioInfo() {
		regRadioInfoText = chosenRegRadio.getRadiopharmaceutical().getSupplier().getSupplierName() +": "
				+chosenRegRadio.getRadiopharmaceutical().getRadiopharmaceuticalName()+": "
				+chosenRegRadio.getBatchNumber()+": "+chosenRegRadio.getRoom().getDescription()+ ": "
				+chosenRegRadio.getUser().getSignature();
		return regRadioInfoText;
	}

	public double getActivity() {
		try {
			userInputActivity = Double.parseDouble(calibrationTextField.getText().replace(",", "."));
			calibrationButton.setDisable(false);
			saveButton.setDisable(false);
		} catch (NumberFormatException e) {
			calibrationButton.setDisable(true);
			saveButton.setDisable(true);
			label_show_activity.setText("Ange ett korrekt aktivitetsvärde");
		}
		return userInputActivity;
	}

	public String getCalibrationDate() {
		LocalDate date = chosenRegRadio.getCalibrationDate().toLocalDate();
		String dateString = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return dateString;
	}

	public Date getArrivalDate() {
		return java.sql.Date.valueOf(arrivalDatePicker.getValue());
	}

	public String getCalibrationInput() {
		return check_auto_calibration.isSelected() ? "Här ska den uträknade aktiviteten stå"
				: commentaryTextField.getText();
	}

	public String getTime() {
		String time = calibrationTimeTextField.getText();
		return time.replace(":", "");
	}

	public void setMbQActivity(RegRadio chosenRegRadio) {
		this.chosenRegRadio = chosenRegRadio;
		MathController mathController = new MathController();
		calculatedActivity = mathController.execute(chosenRegRadio);
		chosenRegRadio.setCalibrationActivity(calculatedActivity);
		chosenRegRadio.setCalibrationsDate();
		chosenRegRadio.setUser(new UserDao().getCurrent(1));
	}

	public double getMbQActivity() {
		System.out.println(calculatedActivity);
		return calculatedActivity;
	}
}

