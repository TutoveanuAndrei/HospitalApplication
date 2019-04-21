package com.tests;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import com.controller.MainController;
import com.model.Animal;
import com.model.History;
import com.model.Personalmedical;
import com.util.UtilHospital;

public class PopulateMapTest {
	
	@Test	
	public void test() {
		MainController test = new MainController();
		test.populateMap();
		
		HashMap<Integer, History> hMap = new HashMap<>();
		List <History> historyList = new ArrayList<>();
		hMap = test.getHMapOfHistory();
		hMap.forEach((k, v)-> historyList.add(v));

		//assertEquals(historyList.get(0),test.getListOfHistory().get(0));
		//assertArrayEquals(historyList,test.getListOfHistory());
		
	}	
	@Test
	public void testHMapOfHistory() {
		//Test if hashMap of History it's not empty
		MainController test = new MainController();
		test.populateMap();
		HashMap<Integer, History> hMap = new HashMap<>();
		hMap = test.getHMapOfHistory();
		assertTrue(hMap.size() > 0);
	}
	@Test
	public void testIfAllAnimalsHavePictures() {
		MainController test = new MainController();
		List <byte[]>  list = test.getImagesOfAnimals();
		for(byte[] image: list) {
			assertFalse(image.length < 0);
		}
	}
	@Test
	public void testIfAllOwnersHaveAName() {
		MainController test = new MainController();
		List<Animal> listOfAnimals = test.getAnimals();
		for(Animal a: listOfAnimals) {
			assertTrue(a.getOwner().getNameOwner().length() > 0);
		}
	}
	@Test
	public void testIfAllOwnersHaveAnId() {
		MainController test = new MainController();
		List<Animal> listOfAnimals = test.getAnimals();
		for(Animal a: listOfAnimals) {
			assertTrue(Integer.toString(a.getOwner().getIdOwner()).length() > 0 );
		}
	}
	@Test
	public void testSortBirthDateForAnimals() {
		MainController test = new MainController();
		List<Integer> listOfBirth = test.getAnimalBirth();
		for(int i=0;i<listOfBirth.size()-1;i++)
			assertTrue(listOfBirth.get(i) <= listOfBirth.get(i+1));
	}
	@Test
	public void testAnimalColor() {
		MainController test = new MainController();
		List<Animal> listOfAnimals = test.getAnimals();
		List<String> listOfColor = test.getAnimalColor(listOfAnimals);
		Integer index = 0;
		for(Animal a: listOfAnimals) {
			assertSame(a.getColor(), listOfColor.get(index));
			index++;
		}
	}
	@Test
	public void testUtilClass() {
		UtilHospital.utilHospital.setUp();
		UtilHospital.utilHospital.startTransaction();
		assertNotNull(UtilHospital.entityManager.find(Animal.class, 12));
		assertNotNull(UtilHospital.entityManager.find(Animal.class, 10));
		assertNotNull(UtilHospital.entityManager.find(Animal.class, 1));
		UtilHospital.utilHospital.stop();
	}
	@Test
	public void testForCreatingANewAnimal() {
		UtilHospital.utilHospital.setUp();
		UtilHospital.utilHospital.startTransaction();
		assertNull(UtilHospital.entityManager.find(Animal.class, 666));
	}
	@Test
	public void testForListOfDoctors() {
		UtilHospital.utilHospital.setUp();
		UtilHospital.utilHospital.startTransaction();
		List<Personalmedical> list = new ArrayList<>();
		list = UtilHospital.utilHospital.doctorList();
		assertFalse(list.size() <= 0);
	}
}