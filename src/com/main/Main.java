package com.main;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{
	@Override
	public void start(Stage primaryStage)throws Exception{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("/com/secondController/LoginFXML.fxml"));
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("/com/secondController/LoginFXML.fxml"));
			Scene scene = new Scene(root);
			primaryStage.getIcons().add(setIcon());
			primaryStage.setTitle("Login");
			primaryStage.setScene(scene);
			primaryStage.show(); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	Image setIcon() throws IOException {
		BufferedImage img = ImageIO.read(new File("src/resources/hospital_icon.png"));
		Image image =  SwingFXUtils.toFXImage(img, null);
		return image;	
	}
	public static void main(String[] args) {
		launch(args);
	}
}
