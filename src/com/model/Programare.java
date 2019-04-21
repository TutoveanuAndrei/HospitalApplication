package com.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the programare database table.
 * 
 */
@Entity
@NamedQuery(name="Programare.findAll", query="SELECT p FROM Programare p")
public class Programare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idProgramare;

	private Timestamp dataProgramare;

	//bi-directional many-to-one association to Animal
	@ManyToOne
	@JoinColumn(name="idAnimal")
	private Animal animal;

	//bi-directional many-to-one association to Personalmedical
	@ManyToOne
	@JoinColumn(name="idPersonalMedical")
	private Personalmedical personalmedical;

	public Programare() {
	}
	public Programare(int idProgramare, Timestamp dataProgramare) {
		this.idProgramare = idProgramare;
		this.dataProgramare = dataProgramare;
	}
	public int getIdProgramare() {
		return this.idProgramare;
	}

	public void setIdProgramare(int idProgramare) {
		this.idProgramare = idProgramare;
	}

	public Timestamp getDataProgramare() {
		return this.dataProgramare;
	}

	public void setDataProgramare(Timestamp dataProgramare) {
		this.dataProgramare = dataProgramare;
	}

	public Animal getAnimal() {
		return this.animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

	public Personalmedical getPersonalmedical() {
		return this.personalmedical;
	}

	public void setPersonalmedical(Personalmedical personalmedical) {
		this.personalmedical = personalmedical;
	}

}