package com.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the personalmedical database table.
 * 
 */
@Entity
@NamedQuery(name="Personalmedical.findAll", query="SELECT p FROM Personalmedical p")
public class Personalmedical implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idpersonalMedical;

	private String name;

	private String password;

	private float salary;

	//bi-directional many-to-one association to Programare
	@OneToMany(mappedBy="personalmedical")
	private List<Programare> programares;

	public Personalmedical() {
	}
	public Personalmedical(int idpersonalMedical, String name, float salary,String password) {
		this.idpersonalMedical = idpersonalMedical;
		this.name = name;
		this.salary = salary;
		this.password = password;
	}
	public int getIdpersonalMedical() {
		return this.idpersonalMedical;
	}

	public void setIdpersonalMedical(int idpersonalMedical) {
		this.idpersonalMedical = idpersonalMedical;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public float getSalary() {
		return this.salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public List<Programare> getProgramares() {
		return this.programares;
	}

	public void setProgramares(List<Programare> programares) {
		this.programares = programares;
	}

	public Programare addProgramare(Programare programare) {
		getProgramares().add(programare);
		programare.setPersonalmedical(this);

		return programare;
	}

	public Programare removeProgramare(Programare programare) {
		getProgramares().remove(programare);
		programare.setPersonalmedical(null);

		return programare;
	}

}