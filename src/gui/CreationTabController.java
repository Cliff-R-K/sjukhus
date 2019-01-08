//package gui;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.ResourceBundle;
//
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//
//
//
//
//
//public class CreationTabController<T> implements Initializable {
//
//	
//	public Button saveButton = new Button();
//	public ComboBox<String> typeComboBox = new ComboBox<>();
//	public ArrayList<String> listOfTypes = new ArrayList<String>();
//
//
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//
//		saveButton.setVisible(true);
//		
//		
//		listOfTypes.add("Användare");
//		listOfTypes.add("Leverantör");
//		listOfTypes.add("Ämne");
//		listOfTypes.add("RadioFarmaka");
//
//		typeComboBox.getItems().addAll(listOfTypes);
//
//
//		typeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//
//			System.out.println("clicked"+ typeComboBox.getSelectionModel().getSelectedIndex());
//			//			combobox_radio.getItems().clear();
//			//			combobox_radio.getItems().addAll(FXCollections.observableArrayList(
//			//					new RadiopharmaceuticalDao().getRadiopharmaceuticalsBySupplierName(newValue.toString())));
//			//			combobox_radio.setDisable(false);
//			//			combobox_radio.getSelectionModel().selectFirst();
//
//
//			switch(typeComboBox.getSelectionModel().getSelectedIndex()) {
//			case 0:
//				break;
//			case 1:
//				break;
//			case 2:
//				break;
//			case 3:
//				break;
//			default:
//				System.out.println("nothing chosen");
//				break;
//
//			}
//		});	
//	}
//	
//	private void viewUserSettings() {
//		System.out.println("0");
//		
//	}
//	private void viewSupplierSettings() {
//		System.out.println("1");
//		
//	}
//	private void viewSubstanceSettings() {
//		System.out.println("2");
//		
//	}
//	private void viewRadioPharmakaSettings() {
//		System.out.println("3");
//		
//	}
//	
//}