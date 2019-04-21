package com.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the history database table.
 * 
 */
@Entity
@NamedQuery(name="History.findAll", query="SELECT h FROM History h")
public class History implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idHistory;

	private String medicalEvents;

	private String treatment;

	//bi-directional many-to-one association to Animal
	@ManyToOne
	@JoinColumn(name="idAnimal")
	private Animal animal;

	public History() {
	}
	public History(int idHistory, String medicalEvents, String treatment) {
		this.idHistory = idHistory;
		this.medicalEvents = medicalEvents;
		this.treatment = treatment;
	}
	public int getIdHistory() {
		return this.idHistory;
	}

	public void setIdHistory(int idHistory) {
		this.idHistory = idHistory;
	}
	public void printMedicalEvents() {
		System.out.println(this.medicalEvents + " " + this.idHistory + " " + this.treatment);
	}
	public String getMedicalEvents() {
		return this.medicalEvents;
	}

	public void setMedicalEvents(String medicalEvents) {
		this.medicalEvents = medicalEvents;
	}

	public String getTreatment() {
		return this.treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public Animal getAnimal() {
		return this.animal;
	}

	public void setAnimal(Animal animal) {
		this.animal = animal;
	}

}