package com.controller;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import com.model.Animal;
import com.model.History;
import com.model.Owner;
import com.model.Programare;
import com.util.UtilHospital;
public class MainController {
	SimpleDateFormat s;
	DateTimeFormatter s1 = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
	DateTimeFormatter standardFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	Calendar cal;
	
	@FXML private ImageView imageView;
	@FXML private ImageView rightButtonImage;
	@FXML private ImageView leftButtonImage;
	@FXML private Label label;
	@FXML private ListView<String> listView;
	@FXML private MenuBar menuBar;
	@FXML private MenuItem menuItem;
	@FXML private ScrollPane scrollPane;
	@FXML private Text text2;
	@FXML private TextField textFieldFromPane1;
	@FXML private TextField textFieldFromPane2;
	@FXML private TextArea textArea;
	@FXML private Button leftButton;
	@FXML private Button rightButton;
	@FXML private Text text;
	@FXML private TextField dateOfBirth; 
	@FXML private DatePicker datePicker;
	
	HashMap<Integer, History> hMapOfAnimals = new HashMap<>(); 
	
	public HashMap<Integer, History> getHMapOfHistory() {
		return this.hMapOfAnimals;
	}
	public List<History> getListOfHistory(){
		UtilHospital utilDb = new UtilHospital();
		utilDb.setUp();
		utilDb.startTransaction();
		return utilDb.historyList();
	}
	public List<byte[]> getImagesOfAnimals() {
		UtilHospital utilDb = new UtilHospital();
		utilDb.setUp();
		utilDb.startTransaction();
		List<byte[]> images = new ArrayList<>();
		List<Animal> list = new ArrayList<>();
		list = utilDb.animalList();
		list.forEach((element) -> images.add(element.getAnimalPicture()));
		return images;
	}
	
	public void populateListView() {
		cal.add(Calendar.DATE, 0);
		String date = convertToDayMonthYearFormat(cal.getTime());
		dataProgrmareBasedByDateAndHour(date);
	}
	public void populateTableView() {
		mousePressedOverAnElementFormListView();
	}
	public void mousePressedOverAnElementFormListView() {
		UtilHospital utilDb = new UtilHospital();
		utilDb.setUp();
		utilDb.startTransaction();
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				String programare = listView.getSelectionModel().getSelectedItem();
				if (programare.equals("empty"))
					return;
 				ObservableList<Animal> animalName = getAnimalBasedByProgrmare(programare);
				displayAnimalPicture(animalName);
				setTextFields(animalName);
			}
		});
	}
	public void displayAnimalPicture(ObservableList<Animal> animalName) {
		try {
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(animalName.get(0).getAnimalPicture()));
			Image image =  SwingFXUtils.toFXImage(img, null);
			imageView.setImage(image);
		} catch (IOException e) {
			System.out.println("Couldn't convert to image");
			e.printStackTrace();
		}
	}
	
	public void setTextFields(ObservableList<Animal> animalName){
		textArea.clear();
		if(animalName.size() == 0){	
			textFieldFromPane1.setText("none");
			textFieldFromPane2.setText("none"); 
			textArea.appendText("none");
			dateOfBirth.setText("none");
		}
		else{
			int idOfAnimal = animalName.get(0).getIdAnimal();
			dateOfBirth.setText(Integer.toString(animalName.get(0).getBirth()));
			textFieldFromPane1.setText(animalName.get(0).getName());
			textFieldFromPane2.setText(animalName.get(0).getOwner().getNameOwner());
			textArea.appendText(hMapOfAnimals.get(idOfAnimal).getMedicalEvents()+"\n");
			textArea.appendText(hMapOfAnimals.get(idOfAnimal).getTreatment());
			textArea.setEditable(false);
			textFieldFromPane1.setEditable(false);
			textFieldFromPane2.setEditable(false);
		}
		
	}
	public void pickDay() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.now();
		String formattedString = localDate.format(dtf);
		text.setText(formattedString);
	}
	public void emptyTextSpaces() {
		textFieldFromPane1.clear();
		textFieldFromPane2.clear();
		textArea.clear();
		dateOfBirth.clear();
	}
	
	public void leftButton() {
		cal.add(Calendar.DATE, - 1);
		displayTheDay();
	}
	public void rightButton() {
		cal.add(Calendar.DATE, + 1);
		displayTheDay();
	}
	public void displayTheDay() {
		String date = convertToDayMonthYearFormat(cal.getTime());
		text.setText(date);
		emptyTextSpaces();
		initializeWithNone();
		dataProgrmareBasedByDateAndHour(date);
	}
	public void closeApp() {
		Platform.exit();
		System.exit(0);
	}
	public void datePickerMethod(ActionEvent event){
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		datePicker.setShowWeekNumbers(true);		
		Date nextDate = Date.from(Instant.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault())));
		cal.setTime(nextDate);
		displayTheDay();
	}
	public void populateMap() {
		UtilHospital utilDb = new UtilHospital();
		utilDb.setUp();
		utilDb.startTransaction();
		List<History> history = (List<History>) utilDb.historyList();
		
		for(History h: history)
			hMapOfAnimals.put(h.getAnimal().getIdAnimal(),h);
		utilDb.stop();
	}
	
	public String convertToDayMonthYearFormat(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		return format.format(date.getTime());
	}
	public void dataProgrmareBasedByDateAndHour(String programare) {
		UtilHospital utilDb = new UtilHospital();
		utilDb.setUp();
		utilDb.startTransaction();
		List<Programare> programareDbList = (List<Programare>) utilDb.programareList();
		ObservableList <String> dates = FXCollections.observableArrayList();
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy kk:mm"); //kk is for 0-24 format
		for(Programare p: programareDbList) {
			String formatted1 = format1.format(p.getDataProgramare().getTime());
			String formatted2 = convertToDayMonthYearFormat(p.getDataProgramare());
			
			String singleDate = formatted1;
			String singleDate2 = formatted2;
			if(singleDate2.equals(programare))
				dates.add(singleDate);
		}
		if(dates.size() == 0)
			dates.add(0,"empty");
		listView.setItems(dates);
		listView.refresh();
		utilDb.stop();
	}
	public void initializeWithNone() {
		try {
		BufferedImage img = ImageIO.read(new File("src/resources/neutral.jpg"));
		Image image =  SwingFXUtils.toFXImage(img, null);
		imageView.setImage(image);
		}
		catch (IOException e){
			System.out.println("Couldn't convert to image");
			e.printStackTrace();
		}
		String init = "none";
		textFieldFromPane1.setText(init);
		textFieldFromPane2.setText(init);
		dateOfBirth.setText(init);
		textArea.appendText(init);
		textArea.setEditable(false);
		textFieldFromPane1.setEditable(false);
		textFieldFromPane2.setEditable(false);
	}
	public void initializeButtons() {
		try {
			Image rightArrow =  SwingFXUtils.toFXImage(ImageIO.read(new File("src/resources/right_arrow.png")), null);
			Image leftArrow = SwingFXUtils.toFXImage(ImageIO.read(new File("src/resources/left_arrow.png")), null);
	
			leftButton.setGraphic(new ImageView(leftArrow));
			rightButton.setGraphic(new ImageView(rightArrow));
			}
			catch (IOException e){
				System.out.println("Couldn't convert to image");
				e.printStackTrace();
			}
	}
	public ObservableList<String> getAnimalColor (List<Animal>animals){
		ObservableList<String> color = FXCollections.observableArrayList();
		for(Animal a:animals)
			color.add(a.getColor());
		return color;
	}
	public ObservableList<Animal> getAnimals(){
		UtilHospital utilDb = new UtilHospital();
		utilDb.setUp();
		utilDb.startTransaction();
		ObservableList<Animal> names = FXCollections.observableArrayList();
		List<Animal> animals = (List<Animal>) utilDb.animalList();
		for(Animal a: animals) {
			names.add(a);
		}
		utilDb.stop();
		return names;
	}
	public ObservableList<Animal> getAnimalBasedByProgrmare(String programare){
		UtilHospital utilDb = new UtilHospital();
		utilDb.setUp();
		utilDb.startTransaction();
		
		//List<Animal> datas = new ArrayList<>();
		
		ObservableList <Animal> dates = FXCollections.observableArrayList();
		List<Programare> programareDbList = (List<Programare>) utilDb.programareList();
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		for(Programare p: programareDbList) {
			String formatted = format1.format(p.getDataProgramare().getTime());
			String singleDate = formatted;
			if(singleDate.equals(programare))
				dates.add(p.getAnimal());
		}
		return dates;
	}
	public ObservableList<Owner>  getOwnerBasedByProgrmare(String programare){
		UtilHospital utilDb = new UtilHospital();
		utilDb.setUp();
		utilDb.startTransaction();
		ObservableList <Owner> dates = FXCollections.observableArrayList();
		List<Programare> programareDbList = (List<Programare>) utilDb.programareList();
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		for(Programare p: programareDbList) {
			String formatted = format1.format(p.getDataProgramare().getTime());
			String singleDate = formatted;
			if(singleDate.equals(programare))
				dates.add(p.getAnimal().getOwner());
		}
		return dates;
	}
	public ObservableList<String> getDataProgramare(List<Programare>programare){
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		ObservableList <String> dates = FXCollections.observableArrayList();
		for(Programare p:programare) {
			String formatted = format1.format(p.getDataProgramare().getTime());
			String singleDate = formatted;
			dates.add(singleDate);
		}
		return dates;
	}
	public ObservableList<String> getAnimalName (List<Animal>animals){
		ObservableList<String> names = FXCollections.observableArrayList();
		for(Animal a: animals) {
			names.add(a.getName());
		}
		return names;
	}
	public List<Integer> getAnimalBirth(){
		UtilHospital utilDb = new UtilHospital();
		utilDb.setUp();
		utilDb.startTransaction();
		List<Animal> animals = utilDb.animalList();
		List<Integer> animalDateOfBirth = new ArrayList<>();
		for(Animal a: animals)
			animalDateOfBirth.add(a.getBirth());
		
		animalDateOfBirth.sort((Integer date1, Integer date2) -> date1.compareTo(date2));
			return animalDateOfBirth;
	}
	public void currentDay() {
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE,0);	
	}

	public void initialize(){
		initializeButtons();
		pickDay();
		currentDay();
		initializeWithNone();
		populateMap();
		populateListView();
	}
}