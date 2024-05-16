package colectivo.modelo;

import java.util.ArrayList;
import java.util.List;

public class Parada {

	private int id;
	private String direccion;
	private List<Linea> lineas;
	private double lat, lng;
	
	public Parada(int id, String direccion) {
		super();
		this.id = id;
		this.direccion = direccion;
		this.lineas = new ArrayList<Linea>();
	}

	public Parada(int id, String direccion, double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
		this.id = id;
		this.direccion = direccion;
		this.lineas = new ArrayList<Linea>();
	}
	
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public double getLng() {
		return this.lng;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public double getLat() {
		return this.lat;
	}
	
	public void agregarLinea(Linea linea) {
		this.lineas.add(linea);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<Linea> getLineas() {
		return lineas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Parada other = (Parada) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Parada [id=" + id + ", direccion=" + direccion + "]";
	}	
	
	
	
}
