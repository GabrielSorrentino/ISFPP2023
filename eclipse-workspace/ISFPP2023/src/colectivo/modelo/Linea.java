package colectivo.modelo;

import java.util.ArrayList;
import java.util.List;

public class Linea {

	private String nombre;
	private int comienza;
	private int finaliza;
	private int frecuencia;
	private List<Parada> paradasIda;
	private List<Parada> paradasRegreso;
	
	public Linea(String nombre, int comienza, int finaliza, int frecuencia) {
		super();		
		this.nombre = nombre;
		this.comienza = comienza;
		this.finaliza = finaliza;
		this.frecuencia = frecuencia;
		this.paradasIda = new ArrayList<Parada>();
		this.paradasRegreso = new ArrayList<Parada>();
	}

	public void agregarParadaIda(Parada parada) {		
		paradasIda.add(parada);
		parada.agregarLinea(this);
	}
	
	public void agregarParadaRegreso(Parada parada) {		
		paradasRegreso.add(parada);
		parada.agregarLinea(this);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getComienza() {
		return comienza;
	}

	public void setComienza(int comienza) {
		this.comienza = comienza;
	}

	public int getFinaliza() {
		return finaliza;
	}

	public void setFinaliza(int finaliza) {
		this.finaliza = finaliza;
	}

	public int getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}

	public List<Parada> getParadasIda() {
		return paradasIda;
	}
	
	public List<Parada> getParadasRegreso() {
		return paradasRegreso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Linea other = (Linea) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nombre;
	}	
	
}
