package com.secondController;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;

import com.mysql.cj.util.StringUtils;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
	@FXML private Text userNameText;
	@FXML private Text errorText;
	@FXML private TextField usernameField;
	@FXML private Button loggingButton;
	@FXML private ImageView imageView;
	@FXML private MenuItem exit;
	@FXML private PasswordField passwordField;
	@FXML private Pane pane;
	
	private Socket socket;
    private BufferedReader serverResponse;
    private PrintWriter loginDataProvider;
    private static final String positiveStatus = "Succes!";
    private static final String negativeStatus = "Failed!";
	@Override
   	public void initialize(URL location, ResourceBundle resources) {
		
	}
	Image setIcon() throws IOException {
		BufferedImage img = ImageIO.read(new File("src/resources/hospital_icon.png"));
		Image image =  SwingFXUtils.toFXImage(img, null);
		return image;	
	}
	public void closeApp() {
		Runtime.getRuntime().addShutdownHook(new Thread(){public void run(){
		    try {
		        socket.close();
		    } catch (IOException e){ /* failed */ }
		}});
		Platform.exit();
		System.exit(0);
	}
	public void setStyle() {
		pane.setStyle("-fx-border-color: blue;");
	}
	public void displayMainWindow() throws IOException {
		Scene currentScene = loggingButton.getScene();
		currentScene.getWindow().hide();
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("/com/controller/MainWindowFXML.fxml"));				
		Scene scene = new Scene(fxmlLoader.load());
		Stage stage = new Stage();				
		stage.setTitle("Hospital");
		stage.getIcons().add(setIcon());
		stage.setScene(scene);
		stage.show();
	}
	public void displayErrorMessage(String errorMsg) {
		errorText.setText("");
		errorText.setText(errorMsg);
	}
	public boolean hasEmptyFields(String password, String username) {
		if (StringUtils.isNullOrEmpty(password) && StringUtils.isNullOrEmpty(username)) 
			return true;
		return false;
	}
	public void login() throws IOException {
		socket = new Socket("localhost", 5000);
		serverResponse = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		loginDataProvider =  new PrintWriter(socket.getOutputStream(), true);	
		String username = usernameField.getText();
		String password = passwordField.getText();
	
		if (!hasEmptyFields(password,username)) {		
			loginDataProvider.println(username);
			loginDataProvider.println(password);
			String response = serverResponse.readLine();
			if(response.equals(positiveStatus)) {
				displayMainWindow();
			}
			else if(response.equals(negativeStatus))
				displayErrorMessage("Password or username wrong");		
		}
		else
			displayErrorMessage("You need to write on both fields");			
		}
}
