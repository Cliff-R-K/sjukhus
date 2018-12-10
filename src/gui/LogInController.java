package gui;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dao.RegRadioDao;
import dao.UserDao;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.RegRadio;
import model.User;


public class LogInController implements Initializable {
	
	public Button logInButton = new Button();
	RegRadioDao regRadioDao = new RegRadioDao();
	private String user;
	private String pass;
	public TextField text_username = new TextField();
	public TextField text_password = new TextField();
	DbConnectionManager conn = DbConnectionManager.getInstance();
	ActionEvent event;
	
	public void handleButtonAction(ActionEvent event) throws Exception {
		this.event = event;
		user = text_username.getText();
		pass = text_password.getText();	
		System.out.println(user);
		System.out.println(pass);
		login();
	}
	
	private void login() throws Exception {
		ResultSet rs = null;
	    try {
	        if (user != null && pass != null) {
				String sqlString = "Select * from users Where signature='" + user + "' and password='" + pass + "'";
				rs = conn.excecuteQuery(sqlString);
				if (rs.next()) {
					// in this case enter when at least one result comes it means user is valid
					System.out.println("LOGIN SUCCESS");
					User user = new UserDao().get(rs.getInt(1));
					Node source = (Node) event.getSource();
					Stage stage = (Stage) source.getScene().getWindow();
					stage.close();
					Stage primaryStage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("UI.fxml"));
					primaryStage.setTitle("Nuclear App");
					primaryStage.setScene(new Scene(root));
					primaryStage.show();
	            } else {
	            	System.out.println("LOGIN FAIL");
	                //in this case enter when  result size is zero  it means user is invalid
	            	Alert alert = new Alert(AlertType.INFORMATION);
	    	        alert.setTitle("TITLE else");
	    	        alert.setHeaderText("Header else");
	    	        alert.setContentText("INFO else");
	    	        alert.showAndWait();
	            }
	        }

	        // You can also validate user by result size if its comes zero user is invalid else user is valid

	    } catch (SQLException err) {
	    	Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("TITLE");
	        alert.setHeaderText("Header");
	        alert.setContentText("INFO");
	        alert.showAndWait();
	    }

	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}
