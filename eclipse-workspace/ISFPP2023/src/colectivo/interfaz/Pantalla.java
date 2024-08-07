package colectivo.interfaz;

import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Pantalla {
	private Map<String, Linea> lineas;
	private Map<Integer, Parada> paradas;
	private static Pantalla instancia;
	private Pantalla(Map<String, Linea> lineas, Map<Integer, Parada> paradas) {
		this.lineas = lineas;
		this.paradas = paradas;
	}
	public static void crearInstancia (Map<String, Linea> lineas, Map<Integer, Parada> paradas) {
		instancia = new Pantalla(lineas, paradas);
	}
	public static Pantalla getInstancia() {
		if (instancia == null) JOptionPane.showMessageDialog(null, "¡La instancia es nula! Cuidado.");
		return instancia;
	}
	public Linea obtenerLinea(String nombre) {
		return lineas.get(nombre);
	}
	public Parada obtenerParada(int n) {
		return paradas.get(n);
	}
	public void mostrarLineas() {
		StringBuilder sb = new StringBuilder("Nombre -- Hora a la que comienza -- Hora a la que finaliza -- Frecuencia (en minutos)\n"
				+ ">>Paradas en el recorrido de ida\n<<Paradas en el recorrido de regreso\n\n");
		for (Linea l: lineas.values())
			sb.append(l.toStringExtendido() + "\n>>" + l.getParadasIda().toString() + "\n<<" + l.getParadasRegreso().toString() + "\n\n");
		JOptionPane.showMessageDialog(null, sb.toString());
	}
	public void mostrarParadas() {
		StringBuilder sb = new StringBuilder("ID -- Direccion -- Latitud -- Longitud -- Lineas que pasan por la parada\n");
		for (Parada p : paradas.values())
			sb.append(p.datosParadaSeparados() + "\n");
		JOptionPane.showMessageDialog(null, sb.toString());
	}
}
