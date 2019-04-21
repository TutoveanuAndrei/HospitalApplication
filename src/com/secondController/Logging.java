package com.secondController;
import java.util.List;

import com.model.Personalmedical;
import com.util.UtilHospital;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class Logging {
	@FXML private Text userNameText;
	@FXML private Text paswordText;
	@FXML private Text errorText;
	@FXML private TextField textFieldUserName;
	@FXML private TextField textFieldPassword;
	@FXML private Button loggingButton;
	
	public void populateLoggingMenu() {
		UtilHospital utilDb = new UtilHospital();
		utilDb.setUp();
		utilDb.startTransaction();
		String username = textFieldUserName.getAccessibleText();
		String password = textFieldPassword.getAccessibleText();
		boolean connected = false;
		while(!connected){
			List<Personalmedical> doctorList = (List<Personalmedical>)utilDb.doctorList();
			for(Personalmedical doctor: doctorList) {
				if(doctor.getName().equals(username) && doctor.getPassword().equals(password))
					connected = true;
			}
			if(!connected) {
				textFieldUserName.clear();
				textFieldPassword.clear();
				errorText.setText("Username or password wrong!!");
			}
			else{
				errorText.setText("Connected!!");
				}
		}
	}	
	public void initialize() {
		populateLoggingMenu();
	}
}
