package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Turma implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private Double notas;
	
	public Turma() {
	}

	public Turma(Integer id, String name, Double notas) {
		this.id = id;
		this.name = name;
		this.notas = notas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getNotas() {
		return notas;
	}	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turma other = (Turma) obj;
		return Objects.equals(id, other.id);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Turma [id=" + id + ", name=" + name + ", notas=" + notas + "]";
	}
	

}
