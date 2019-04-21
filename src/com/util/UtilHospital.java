package com.util;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.model.Animal;
import com.model.History;
import com.model.Owner;
import com.model.Personalmedical;
import com.model.Programare;

public class UtilHospital {
	public static EntityManagerFactory entityManagerFactory;
	public static EntityManager entityManager;
	
	//Singleton
	public static UtilHospital utilHospital = new UtilHospital();
	
	public static UtilHospital getInstance() {
		return utilHospital;
	}
	public static void setInstance(UtilHospital hospital) {
		UtilHospital.utilHospital = hospital;
	}
	
	public void setUp(){
		entityManagerFactory = Persistence.createEntityManagerFactory("hospitalProject");
		entityManager = entityManagerFactory.createEntityManager();
	}
		
	public void saveAnimal(Animal animal) {
		entityManager.persist(animal);		
	}
	public void saveOwner(Owner owner) {
		entityManager.persist(owner);
	}
	public void saveHistory(History history) {
		entityManager.persist(history);
	}
	public void savePersonalMedical(Personalmedical personalMedical) {
		entityManager.persist(personalMedical);
	}
	public void saveProgramare(Programare programare) {
		entityManager.persist(programare);
	}
	
	public void startTransaction() {
		entityManager.getTransaction().begin();
	}
	public void commitTransaction() {
		entityManager.getTransaction().commit();
	}
	public void stop() {
		entityManager.close();
	}
	

	public void printAllAnimalsFromDB() {
		@SuppressWarnings("unchecked")
		List<Animal> results = entityManager.createNativeQuery("Select * fromhospital.Animal",Animal.class).getResultList();
		for(Animal animal : results) {
			System.out.println("Animal: " + animal.getName() + " hasID " + animal.getIdAnimal() + " hasColor: " + animal.getColor() + " hasBirthYear " + animal.getBirth() );
		}
	}
	
	//CRUD Animal
	/**
	 * @param idAnimal
	 * @param animalPicture
	 * @param bornYear
	 * @param color
	 * @param name
	 * @function used for create an animal based by @param
	 */
	public void createAnimal(int idAnimal, byte[]animalPicture,int bornYear ,String color ,String name) {
		entityManager.getTransaction().begin();
		entityManager.persist(new Animal(idAnimal,animalPicture,bornYear,color,name));
		entityManager.getTransaction().commit();
	}
	/**
	 * @param idAnimal
	 * @method used to find an animal based by id
	 */
	public void readAnimal(int idAnimal) {
		Animal obj = entityManager.find(Animal.class, idAnimal);
		System.out.println(obj.getBirth() + " " + obj.getColor() + " " + obj.getIdAnimal() + " " + obj.getName());
	}
	/**
	 * @param idAnimal
	 * @param bornYear
	 * @param color
	 * @param name
	 * @method used for modify a registration in database
	 */
	public void updateAnimal(int idAnimal,int bornYear ,String color ,String name) {
		Animal obj = entityManager.find(Animal.class, idAnimal);
		entityManager.getTransaction().begin();
		obj.setBirth(bornYear);
		obj.setColor(color);
		obj.setName(name);
		entityManager.getTransaction().commit();
	}	
	/**
	 * @param idAnimal
	 * @method used for delete a registration based by id of an animal
	 */
	public void deleteAnimal(int idAnimal) {
		Animal obj = entityManager.find(Animal.class, idAnimal);
		entityManager.getTransaction().begin();
		entityManager.remove(obj);
		entityManager.getTransaction().commit();
	}
	
	//CRUD PersonalMediacal
	/**
	 * @param idPersonalMedical
	 * @param name
	 * @param salary
	 * @param password
	 * @method used for create a doctor based by @param
	 */
	public void createPersonalMedical(int idPersonalMedical, String name,int salary,String password) {
		entityManager.getTransaction().begin();
		entityManager.persist(new Personalmedical(idPersonalMedical,name,salary,password));
		entityManager.getTransaction().commit();
	}	
	/**
	 * @param idPersonalMedical
	 * @method used for modify a registration in database
	 */
	public void readPersonalMedical(int idPersonalMedical) {
		Personalmedical obj = entityManager.find(Personalmedical.class, idPersonalMedical);
		System.out.println(obj.getIdpersonalMedical() + " " + obj.getName() + " "+ obj.getSalary() );
	}
	/**
	 * @param idPersonalMedical
	 * @param name
	 * @param salary
	 * @method used for modify a registration in database
	 */
	public void updatePersonalMedical(int idPersonalMedical, String name,  int salary) {
		Personalmedical obj = entityManager.find(Personalmedical.class, idPersonalMedical);
		entityManager.getTransaction().begin();
		obj.setIdpersonalMedical(idPersonalMedical);
		obj.setName(name);
		obj.setSalary(salary);
		entityManager.getTransaction().commit();
	}
	/**
	 * @param idPersonalMedical
	 * @method used for delete a registration based by id of a doctor
	 */
	public void deletePersonalMedical(int idPersonalMedical) {
		Personalmedical obj = entityManager.find(Personalmedical.class, idPersonalMedical);
		entityManager.getTransaction().begin();
		entityManager.remove(obj);
		entityManager.getTransaction().commit();
	}
	
	//CRUD Programare
	/**
	 * @param idProgramare
	 * @param appoiment
	 * @method used for create an appoiment based by @param
	 */
	public void createProgramare(int idProgramare, Timestamp appoiment) {
		entityManager.getTransaction().begin();
		entityManager.persist(new Programare(idProgramare,appoiment));
		entityManager.getTransaction().commit();
	}
	/**
	 * @param idProgramare
	 * @method used for modify a registration in database
	 */
	public void readProgramare(int idProgramare) {
		Programare obj = entityManager.find(Programare.class,idProgramare );
		System.out.println(obj.getIdProgramare() + " " + obj.getDataProgramare());
	}
	/**
	 * @param idProgramare
	 * @param appoiment
	 * @method used for modify a registration in database
	 */
	public void updateProgramare(int idProgramare, Timestamp appoiment) {
		Programare obj = entityManager.find(Programare.class, idProgramare);
		entityManager.getTransaction().begin();
		obj.setIdProgramare(idProgramare);
		obj.setDataProgramare(appoiment);
		entityManager.getTransaction().commit();
	}
	/**
	 * @param idPersonalMedical
	 * @method used for delete a registration based by id of a doctor
	 */
	public void deleteProgramare(int idPersonalMedical) {
		Programare obj = entityManager.find(Programare.class, idPersonalMedical);
		entityManager.getTransaction().begin();
		entityManager.remove(obj);
		entityManager.getTransaction().commit();
	}
	
	//CRUD Owner
	/**
	 * @param idOwner
	 * @param nameOwner
	 * @method used for create an owner based by @param
	 */
	public void createOwner(int idOwner, String nameOwner) {
		entityManager.getTransaction().begin();
		entityManager.persist(new Owner(idOwner,nameOwner));
		entityManager.getTransaction().commit();
	}
	/**
	 * @param idOwner
	 * @param nameOwner
	 * @method used for modify an owner registration in database
	 */
	public void readOwner(int idOwner, String nameOwner) {
		Owner obj = entityManager.find(Owner.class, idOwner);
		System.out.println(obj.getIdOwner() + " " + obj.getNameOwner());
	}
	/**
	 * @param idOwner
	 * @param nameOwner
	 * @method used for modify an owner registration in database
	 */
	public void updateOwner(int idOwner, String nameOwner) {
		Owner obj = entityManager.find(Owner.class, idOwner);
		entityManager.getTransaction().begin();
		obj.setIdOwner(idOwner);
		obj.setNameOwner(nameOwner);
		entityManager.getTransaction().commit();
	}
	/**
	 * @param idOwner
	 * @method used for delete an owner registration based by his id
	 */
	public void deleteOwner(int idOwner) {
		Owner obj = entityManager.find(Owner.class, idOwner);
		entityManager.getTransaction().begin();
		entityManager.remove(obj);
		entityManager.getTransaction().commit();
	}

	//CRUD History
	/**
	 * @param idHistory
	 * @param medicalEvents
	 * @param treatment
	 * @method used for creating a history for an animal based by @param
	 */
	public void createHistory(int idHistory, String medicalEvents, String treatment) {
		entityManager.getTransaction().begin();
		entityManager.persist(new History(idHistory,medicalEvents,treatment));
		entityManager.getTransaction().commit();
	}
	/**
	 * @param idHistory
	 * @param medicalEvents
	 * @param treatment
	 * @method used for modify a history registration in database
	 */
	public void readHistory(int idHistory, String medicalEvents, String treatment) {
		History obj = entityManager.find(History.class, idHistory);
		System.out.println(obj.getIdHistory() + " " + obj.getMedicalEvents() + " " + obj.getTreatment() );
	}
	/**
	 * @param idHistory
	 * @param medicalEvents
	 * @param treatment
	 * @method used for modify an animal history registration in database
	 */
	public void updateHistory(int idHistory, String medicalEvents, String treatment) {
		History obj = entityManager.find(History.class, idHistory);
		entityManager.getTransaction().begin();
		obj.setIdHistory(idHistory);
		obj.setMedicalEvents(medicalEvents);
		obj.setTreatment(treatment);
		entityManager.getTransaction().commit();
	}
	/**
	 * @param idHistory
	 * @method used for delete an animal history registration in database
	 */
	public void deleteHistory(int idHistory) {
		History obj = entityManager.find(History.class, idHistory);
		entityManager.getTransaction().begin();
		entityManager.remove(obj);
		entityManager.getTransaction().commit();
	}
	
	//Generics
	/**	
	 * @generics for deleting elements in all classes
	 */
	@SuppressWarnings("unchecked")
	public static <T> void deleteRT (Class<T> clazz, int id) {
		
		T obj = (T) entityManager.find(clazz.getClass(), id);		
		entityManager.getTransaction().begin();
		entityManager.remove(obj);
		entityManager.getTransaction().commit();
	}
	/**
	 * @generics for saving elements in all classes 
	 */
	public static <T> void saveRT (Class<T> clazz) {
		entityManager.persist(clazz);
	}	
	
	/**
	 * @return list of registration for each table
	 */
	@SuppressWarnings("unchecked")
	public List<Animal> animalList() {
		return entityManager.createNativeQuery("Select * from hospital.animal", Animal.class).getResultList();	
	}
	@SuppressWarnings("unchecked")
	public List<Programare>programareList(){
		return entityManager.createNativeQuery("Select * from hospital.programare",Programare.class).getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Personalmedical>doctorList(){
		return entityManager.createNativeQuery("Select * from hospital.Personalmedical",Personalmedical.class).getResultList();
	}	
	@SuppressWarnings("unchecked")
	public List<History>historyList(){
		return entityManager.createNativeQuery("Select * from hospital.history",History.class).getResultList();
	}
}
