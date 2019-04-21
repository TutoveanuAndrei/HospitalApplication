package com.model;
import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the animal database table.
 * 
 */
@Entity
@NamedQuery(name="Animal.findAll", query="SELECT a FROM Animal a")
public class Animal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idAnimal;

	private byte[] animalPicture;

	private int birth;

	private String color;

	private String name;

	//bi-directional many-to-one association to Owner
	@ManyToOne
	@JoinColumn(name="nameOwner")
	private Owner owner;

	//bi-directional many-to-one association to History
	@OneToMany(mappedBy="animal")
	private List<History> histories;

	//bi-directional many-to-one association to Programare
	@OneToMany(mappedBy="animal")
	private List<Programare> programares;

	public Animal() {
	}

	public Animal(int idAnimal,byte[] animalPicture, int birth, String color, String name){
		this.idAnimal = idAnimal;
		this.animalPicture = animalPicture;
		this.birth = birth;
		this.color = color;
		this.name = name;
	}
	
	public int getIdAnimal() {
		return this.idAnimal;
	}
	public void setIdAnimal(int idAnimal) {
		this.idAnimal = idAnimal;
	}

	public byte[] getAnimalPicture() {
		return this.animalPicture;
	}

	public void setAnimalPicture(byte[] animalPicture) {
		this.animalPicture = animalPicture;
	}

	public int getBirth() {
		return this.birth;
	}

	public void setBirth(int birth) {
		this.birth = birth;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public List<History> getHistories() {
		return this.histories;
	}

	public void setHistories(List<History> histories) {
		this.histories = histories;
	}

	public History addHistory(History history) {
		getHistories().add(history);
		history.setAnimal(this);

		return history;
	}

	public History removeHistory(History history) {
		getHistories().remove(history);
		history.setAnimal(null);

		return history;
	}
	public void printAnimal() {
		System.out.println(this.getName() + " " + this.getIdAnimal() + " " + this.getColor() + " " + this.getBirth()
		+ " " +this.getOwner().getIdOwner() + " " + this.getOwner().getNameOwner() + " " + this.getClass().toString());
	}
	public List<Programare> getProgramares() {
		return this.programares;
	}

	public void setProgramares(List<Programare> programares) {
		this.programares = programares;
	}

	public Programare addProgramare(Programare programare) {
		getProgramares().add(programare);
		programare.setAnimal(this);

		return programare;
	}

	public Programare removeProgramare(Programare programare) {
		getProgramares().remove(programare);
		programare.setAnimal(null);

		return programare;
	}

	public List<History> getHistory() {
		return this.histories;
	}

}