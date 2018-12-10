package gui;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import dao.CalibrationDao;
import dao.RadiopharmaceuticalDao;
import dao.RegRadioDao;
import dao.RoomDao;
import dao.SupplierDao;
import dao.UserDao;
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
import model.Radiopharmaceutical;
import model.RegRadio;
import model.Room;
import model.Supplier;

public class NuclearAppController implements Initializable {

	private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
	private ObservableList<Radiopharmaceutical> radioList = FXCollections.observableArrayList();
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
	public ListView<String> listView = new ListView<String>();

	public CheckBox check_kontamineringskontroll = new CheckBox();
	public Button button = new Button();
	public TableView radioView = new TableView<RegRadio>();
	public TableColumn startActivityCol = new TableColumn();
	public TableColumn roomCol = new TableColumn();
	public TableColumn substanceCol2 = new TableColumn();
	public TableColumn startDateCol = new TableColumn();
	public TableColumn calibrationCol = new TableColumn();
	public TableColumn arrivalDateCol = new TableColumn();
	public TableColumn batchNumberCol = new TableColumn();
	private RegRadio regP;
	private Date startdate;
	private Date enddate;
	private Date arrivalDate;
	public TableColumn userCol = new TableColumn();;
	public TableColumn radioPharmaceuticalCol = new TableColumn();
	public TableColumn endDateCol = new TableColumn();;
	public TableColumn contaminationControllCol = new TableColumn();
	public TableColumn supplierCol = new TableColumn();



	@FXML
	TableColumn<RegRadio, Radiopharmaceutical> radiopharmaceuticalCol;
	


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
		//combobox_radio.getSelectionModel().selectFirst();
		setSubstanceInfo();
	}

	public void setSubstanceInfo() {
		label_rad_substance.setText(combobox_radio.getValue().getSubstance().getName());
		label_halftime.setText(combobox_radio.getValue().getSubstance().getHalfLife()+"");
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
		addSuppliers();
		ankomstdatum.setValue(LocalDate.now());
		combobox_radio.setDisable(true);

		String pattern = "yyyy-MM-dd HH:mm";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			Date startdate = simpleDateFormat.parse("2018-10-30 20:26");
			Date enddate = simpleDateFormat.parse("2019-10-30 20:26");
			Date arrivalDate;

			arrivalDate = simpleDateFormat.parse("2019-11-02 10:26");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		startActivityCol.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
		roomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
		radiopharmaceuticalCol.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
		startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
		calibrationCol.setCellValueFactory(new PropertyValueFactory<>("calibration"));
		arrivalDateCol.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
		batchNumberCol.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
		supplierCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		

		contaminationControllCol.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
		userCol.setCellValueFactory(new PropertyValueFactory<>("user"));

		regRadioList.clear();
		regRadioList.addAll(new RegRadioDao().getAll());
		radioView.setItems(regRadioList);

	}
}
