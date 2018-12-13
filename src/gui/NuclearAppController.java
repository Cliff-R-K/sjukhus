package gui;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.sun.istack.internal.logging.Logger;


import dao.RadiopharmaceuticalDao;
import dao.RegRadioDao;
import dao.RoomDao;
import dao.SupplierDao;
import dao.UserDao;
import dataholder.DataHolder;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.ComboBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Radiopharmaceutical;
import model.RegRadio;
import model.Room;
import model.Supplier;
import model.User;

public class NuclearAppController implements Initializable {

	public TableView<RegRadio> tableview = new TableView<>();

	public Button saveButton = new Button();
	public Button button = new Button();
	public Button logOutButton = new Button();
	public Button logOutButton1 = new Button();
	public Button searchButton = new Button();
	public Button clearButton = new Button();
	public Button editButton = new Button();

	private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();
	private ObservableList<Radiopharmaceutical> radioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> regRadioList = FXCollections.observableArrayList();
	private ObservableList<RegRadio> searchRegRadioList = FXCollections.observableArrayList();
	
	private List<RegRadio> sortedList;

	public DatePicker ankomstdatum = new DatePicker();
	public DatePicker kalibreringsdatum = new DatePicker();
	public DatePicker startSortDate = new DatePicker();
	public DatePicker endSortDate = new DatePicker();
	
	public ComboBox<Supplier> combobox_suppliers = new ComboBox<>();
	public ComboBox<Radiopharmaceutical> combobox_radio = new ComboBox<>();
	public ComboBox<Room> combobox_room = new ComboBox<>();
	
	public ComboBox<User> combobox_user_tab_two = new ComboBox<>();
	public ComboBox<Radiopharmaceutical> combobox_radio_tab_two = new ComboBox<>();
	public ComboBox<Room> combobox_room_tab_two = new ComboBox<>();

	public Label label_rad_substance = new Label();
	public Label label_halftime = new Label();
	public Label signatur = new Label();

	public TextField text_kalibreringsaktivitet = new TextField();
	public TextField text_kalibreringstid = new TextField();
	public TextField text_batchnr = new TextField();
	public TextField text_kommentar = new TextField();
	public ListView<String> listView = new ListView<String>();

	public CheckBox check_kontamineringskontroll = new CheckBox();

	private User user;
	///////////////////////////////////////////////////////////
	public TableView radioView = new TableView<RegRadio>();
	public TableColumn startActivityCol = new TableColumn();
	public TableColumn roomCol = new TableColumn();
	public TableColumn substanceCol2 = new TableColumn();
	public TableColumn startDateCol = new TableColumn();
	public TableColumn calibrationCol = new TableColumn();
	public TableColumn arrivalDateCol = new TableColumn();
	public TableColumn batchNumberCol = new TableColumn();
	private TableColumn uniqueIdCol = new TableColumn();
	//private RegRadio regP;
	private User user;
	//private Date startdate;
	//private Date enddate;
	//private Date arrivalDate;
	//private Date startSortDate;
	//private Date endSortDate;

	public TableColumn userCol = new TableColumn();;
	public TableColumn radioPharmaceuticalCol = new TableColumn();
	public TableColumn endDateCol = new TableColumn();;
	public TableColumn contaminationControllCol = new TableColumn();
	public TableColumn supplierCol = new TableColumn();
	
	private RegRadio chosenRegRadio;
	private Radiopharmaceutical radio_tab_two = null;
	private Room room_tab_two = null;
	private User user_tab_two;

	private ActionEvent event;
	//////////////////////////////////////////////////////////

	// Test

	@FXML
	TableColumn<RegRadio, Radiopharmaceutical> radiopharmaceuticalCol;

	///// ***********

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
	TableColumn<RegRadio, Room> columnRoom;
	@FXML
	TableColumn<RegRadio, User> columnUser;


	private RegRadio radioToEdit;

	private java.sql.Date start;

	private java.sql.Date end;

	public TableColumn uniqueIdCol;



	public void addSuppliers() {
		supplierList.addAll(new SupplierDao().getAll());
		combobox_suppliers.getItems().addAll(supplierList);
	}

	public void addRooms() {
		combobox_room.getItems().addAll(FXCollections.observableArrayList(new RoomDao().getAll()));
	}

	public void addProducts() {
		combobox_radio.setDisable(false);
		radioList.clear();
		radioList.addAll(new RadiopharmaceuticalDao()
				.getRadiopharmaceuticalsBySupplierName(combobox_suppliers.getValue().toString()));
		combobox_radio.getItems().clear();
		combobox_radio.getItems().addAll(radioList);
		combobox_radio.getSelectionModel().selectFirst();
	}
	//FLIK 2
	public void addProductsTabTwo() {
		combobox_radio_tab_two.getItems().addAll(FXCollections.observableArrayList(new RadiopharmaceuticalDao().getAll()));
	}
	//FLIK 2
	public void addRoomsTabTwo() {
		combobox_room_tab_two.getItems().addAll(FXCollections.observableArrayList(new RoomDao().getAll()));
	}
	//FLIK 2
	public void addUsersTabTwo() {
		combobox_user_tab_two.getItems().addAll(FXCollections.observableArrayList(new UserDao().getAll()));
	}


	public void addUser() {
		user = new UserDao().getCurrent(1);
	}

	public void logOutButtonAction(ActionEvent logout) throws Exception {
		this.event = logout;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Logga ut");
		alert.setContentText("Vill du fortsätta?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			logOut();
		}
	}

	public void logOut() throws IOException {
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("LOGIN.fxml"));
		primaryStage.setTitle("Login");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	public void clearButton(ActionEvent search) throws Exception {
		this.event = search;
		combobox_user_tab_two.setValue(null);
		combobox_radio_tab_two.setValue(null);
		combobox_room_tab_two.setValue(null);
		endSortDate.setValue(LocalDate.now());
		startSortDate.setValue(LocalDate.of(1900, 01, 01));
		searchRegRadioList.clear();
		populateListFromDatabase();
		radioView.setItems(searchRegRadioList);
	}

	public String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addSuppliers();
		addRooms();
		addUser();

		//runTempStorage();

		addRoomsTabTwo();
		addUsersTabTwo();
		addProductsTabTwo();

		signatur.setText(user.getSignature());
		ankomstdatum.setValue(LocalDate.now());
		endSortDate.setValue(LocalDate.now());
		startSortDate.setValue(LocalDate.of(1900, 01, 01));
		combobox_radio.setDisable(true);
		
		setUpTableView();
		new Thread(() -> populateListFromDatabase()).start();

		////////////////////////////////////////////////

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
		uniqueIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));

		searchRegRadioList.clear();
		radioView.setItems(searchRegRadioList);

		///////////////////////////////////////////////////
		
		

		combobox_suppliers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			combobox_radio.getItems().clear();
			combobox_radio.getItems().addAll(FXCollections.observableArrayList(
					new RadiopharmaceuticalDao().getRadiopharmaceuticalsBySupplierName(newValue.toString())));
			combobox_radio.setDisable(false);
			combobox_radio.getSelectionModel().selectFirst();
		});

		combobox_radio.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				
				//label_rad_substance.setText(newValue.getSubstance().getName());
				label_halftime.setText(newValue.getSubstance().getHalfLife() + "");
			}
		});

		text_kalibreringstid.focusedProperty().addListener((observable, oldText, newText) -> {
			if (!newText) {
				if (!text_kalibreringstid.getText().matches("^(0[0-9]|1[0-9]|2[0-3]):?[0-5][0-9]$")) {
					text_kalibreringstid.setText("");
					text_kalibreringstid.setPromptText("Felaktig tid");
				}
			}
		});
		check_kontamineringskontroll.selectedProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue) {
				text_kommentar.setDisable(true);
				text_kommentar.clear();
			} else {
				text_kommentar.setDisable(false);
			}
		});
		saveButton.setOnAction((event) -> {

			saveProductButton();
			/*

			RegRadio rr = new RegRadio(getActivity(), getCalibrationDate(), getArrivalDate(), text_batchnr.getText(),
					getContaminationControl(), combobox_radio.getValue(), combobox_room.getValue(), user, null,
					combobox_suppliers.getValue());


			if(regRadioList.size() >= 10)
				regRadioList.remove(regRadioList.size()-1);
			regRadioList.add(0, rr);
			searchRegRadioList.add(0, rr);
			tableview.setItems(regRadioList);
			radioView.getItems().add(0, rr);
			new RegRadioDao().save(rr);
			*/
		});
		///FLIK 2
		combobox_radio_tab_two.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				radio_tab_two = newValue;
			} else {
				radio_tab_two = null;
			}
		});
		combobox_room_tab_two.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				room_tab_two = newValue;
			} else {
				radio_tab_two = null;
			}
		});
		combobox_user_tab_two.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				user_tab_two = newValue;
			} else {
				user_tab_two = null;
			}
		});
	}


	//public void runTempStorage() {

	private void saveProductButton() {

		new Thread() {
			@Override
			public void run() {
				RegRadio rr = new RegRadio(getActivity(), getCalibrationDate(), getArrivalDate(),
						text_batchnr.getText(), getContaminationControl(), combobox_radio.getValue(),
						combobox_room.getValue(), user, null, combobox_suppliers.getValue());

				columnAnkomstdatum.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
				columnSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
				columnRadiopharmaceutical.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
				columnActivity.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
				columnCalibrationdate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
				columnBatchNumber.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
				columnContaminationControl.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
				columnRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
				columnUser.setCellValueFactory(new PropertyValueFactory<>("user"));

				regRadioList.add(0, rr);
				searchRegRadioList.add(0, rr);
				tableview.setItems(regRadioList);
				new RegRadioDao().save(rr);
			}
		}.start();
	}

	public void populateListFromDatabase() {
		searchRegRadioList.addAll(new RegRadioDao().getAll());
		populateTabOneTablelist();
	}

	public void populateTabOneTablelist() {
		//Magiska siffror
		regRadioList = FXCollections.observableArrayList(searchRegRadioList.subList(0, 10)); 
		tableview.getItems().addAll(regRadioList);

	}

	public double getActivity() {
		return Double.parseDouble(text_kalibreringsaktivitet.getText().replace(",", "."));
	}

	public LocalDateTime getCalibrationDate() {
		LocalDate date = kalibreringsdatum.getValue();
		LocalTime time = LocalTime.parse(getTime(), DateTimeFormatter.ofPattern("HHmm"));
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		return dateTime;
	}

	public void setUpTableView() {
		columnAnkomstdatum.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
		columnSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		columnRadiopharmaceutical.setCellValueFactory(new PropertyValueFactory<>("radiopharmaceutical"));
		columnActivity.setCellValueFactory(new PropertyValueFactory<>("startActivity"));
		columnCalibrationdate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
		columnBatchNumber.setCellValueFactory(new PropertyValueFactory<>("batchNumber"));
		columnContaminationControl.setCellValueFactory(new PropertyValueFactory<>("contaminationControll"));
		columnRoom.setCellValueFactory(new PropertyValueFactory<>("room"));
		columnUser.setCellValueFactory(new PropertyValueFactory<>("user"));
		
		columnSupplier.setCellFactory(ComboBoxTableCell.forTableColumn(supplierList));
		
	}

	public Date getArrivalDate() {
		return java.sql.Date.valueOf(ankomstdatum.getValue());
	}

	public String getContaminationControl() {
		return check_kontamineringskontroll.isSelected() ? "OK" : text_kommentar.getText();
	}

	public String getTime() {
		String time = text_kalibreringstid.getText();
		return time.replace(":", "");
	}

	public void clickedSearchScrollPane() {
		System.out.println("clicked scrollpane");

		chosenRegRadio = (RegRadio) radioView.getSelectionModel().getSelectedItem();
		chosenRegRadio.print();
	}

	public void clickedEditButton() throws IOException {

		System.out.println("clicked edit");
		DataHolder.setSavedRadio(chosenRegRadio);

		//		FXMLLoader	 fLoader = new FXMLLoader();
		//		fLoader.setLocation(getClass().getResource("EditRegRadioUI.fxml"));
		//		try {
		//			System.out.println("aaaaaaaaaaaaaaaaa");
		//			fLoader.load();
		//		} catch (IOException ex) { 
		//			Logger.getLogger(NuclearAppController.class.getName(), null).log(Level.SEVERE,null,ex);
		//		}
		//		EditGuiController editController = fLoader.getController();
		//		editController.initateData(chosenRegRadio);

		Parent root = FXMLLoader.load(getClass().getResource("EditRegRadioUI.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Redigera");
		stage.setScene(new Scene(root));
		stage.show();
		
		
		//((Node)(event.getSource())).getScene().getWindow().hide();
		
	}




//		


  
  	public Date getStartSortDate() {
  		start = java.sql.Date.valueOf(startSortDate.getValue());
		return start;
  	}
  	
	public Date getEndSortDate() {
		end = java.sql.Date.valueOf(endSortDate.getValue());
		return end;
	}
	
	public void searchButtonAction(ActionEvent search) throws Exception {
		this.event = search;
		/*Radiopharmaceutical radioTabTwo = null;
		Room roomTabTwo = null;
		User userTabTwo = null;
		if(combobox_radio_tab_two != null) {
			radioTabTwo = combobox_radio_tab_two.getValue();
			//System.out.println(radioTabTwo.getRadiopharmaceuticalName());
		}
		if(combobox_room_tab_two != null) {
			roomTabTwo = combobox_room_tab_two.getValue();
			//System.out.println(roomTabTwo.getRoomCode());
		}
		if(combobox_user_tab_two != null) {
			userTabTwo = combobox_user_tab_two.getValue();
			//System.out.println(userTabTwo.getSignature());
		}*/
		searchRegRadioList.clear();
		//searchRegRadioList.addAll(new RegRadioDao().getSearchedRegRadios(getStartSortDate(), getEndSortDate()));
		searchRegRadioList.addAll(new RegRadioDao().getSearchedRegRadios(getStartSortDate(), getEndSortDate(), radio_tab_two, room_tab_two, user_tab_two));
		radioView.setItems(searchRegRadioList);

	}

	//	public RegRadio getData() {
	//		// TODO Auto-generated method stub
	//		System.out.println("get ");
	//		radioToEdit.print();
	//		System.out.println("get");
	//		return radioToEdit;
	//	}

}
