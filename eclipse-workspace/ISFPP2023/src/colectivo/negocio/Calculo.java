package colectivo.negocio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.YenKShortestPath;
import org.jgrapht.graph.DirectedMultigraph;

import colectivo.aplicacion.Constantes;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Tramo;
import colectivo.util.Time;

public class Calculo {

	private Graph<Parada, ParadaLinea> red;
	private Map<Integer, Parada> paradaMap;
	private Map<String, Tramo> tramoMap;
	private static Calculo instancia = null;

	private Calculo(Map<Integer, Parada> paradaMap, Map<String, Linea> lineaMap, List<Tramo> tramos) {
		// Map paradas
		this.paradaMap = paradaMap;

		// Map tramo
		tramoMap = new TreeMap<String, Tramo>();
		for (Tramo t : tramos)
			tramoMap.put(t.getInicio().getId() + "-" + t.getFin().getId(), t);

		red = new DirectedMultigraph<>(null, null, false);

		// Cargar paradas
		for (Parada p : paradaMap.values())
			red.addVertex(p);

		// Cargar tramos lineas
		Parada origen, destino;
		for (Linea l : lineaMap.values()) {
			for (int i = 0; i < l.getParadasIda().size() - 1; i++) {
				origen = l.getParadasIda().get(i);
				destino = l.getParadasIda().get(i + 1);
				red.addEdge(origen, destino, new ParadaLinea(origen, l));
			}
			for (int i = 0; i < l.getParadasRegreso().size() - 1; i++) {
				origen = l.getParadasRegreso().get(i);
				destino = l.getParadasRegreso().get(i + 1);
				red.addEdge(origen, destino, new ParadaLinea(origen, l));
			}
		}

		// Cargar tramos caminando
		Linea linea;
		for (Tramo t : tramos)
			if (t.getTipo() == Constantes.TRAMO_CAMINANDO) {
				linea = new Linea(t.getInicio().getId() + "-" + t.getFin().getId(), Time.toMins("00:00"),
						Time.toMins("24:00"), 0);
				red.addEdge(t.getInicio(), t.getFin(), new ParadaLinea(t.getInicio(), linea));
			}
	}

	public static Calculo crearInstancia(Map<Integer, Parada> paradaMap, Map<String, Linea> lineaMap, List<Tramo> tramos) {
		instancia = new Calculo(paradaMap, lineaMap, tramos);
		return instancia;
	}

	public static Calculo getInstancia() {
		return instancia;
	}
	/**@Param parada de origen (donde se encuentra el usuario), parada de destino, horario y cantidad de lineas
	 * @returns List<List<Tramo>>: Matriz dinamica con los tramos que van de la parada de origen al destino
	**/
	public List<List<Tramo>> recorridos(Parada paradaOrigen, Parada paradaDestino, int horario, int nroLineas) {
		// Todos los recorridos
		YenKShortestPath<Parada, ParadaLinea> yksp = new YenKShortestPath<Parada, ParadaLinea>(red);
		List<GraphPath<Parada, ParadaLinea>> caminos = yksp.getPaths(paradaOrigen, paradaDestino, Integer.MAX_VALUE);

		// Eliminar recorridos superan cambioLineas
		if (nroLineas != -1) {
			List<Linea> lineas;
			Iterator<GraphPath<Parada, ParadaLinea>> r = caminos.iterator();
			while (r.hasNext()) {
				lineas = new ArrayList<Linea>();
				int cambioLineas = 0;
				for (ParadaLinea pl : r.next().getEdgeList())
					if (lineas.isEmpty())
						lineas.add(pl.getLinea());
					else if (!lineas.get(lineas.size() - 1).equals(pl.getLinea()))
						lineas.add(pl.getLinea());
				for (Linea l : lineas)
					if (l.getFrecuencia() != 0)
						cambioLineas++;
				if (cambioLineas > nroLineas)
					r.remove();
			}
		}

		// Realizar calculo de tiempo y preparar resultados
		List<List<Tramo>> listaTramos = new ArrayList<List<Tramo>>();
		Tramo t = null;
		int proximoColectivo;
		int tiempo = 0;
		List<Tramo> tramos;
		List<ParadaLinea> paradalineas;
		List<Parada> paradas;
		Parada origen = null;
		Parada destino = null;
		Map<Integer, Parada> pMap;
		for (GraphPath<Parada, ParadaLinea> gp : caminos) {
			pMap = new TreeMap<Integer, Parada>();
			paradas = gp.getVertexList();
			for (Parada p : paradas)
				pMap.put(p.getId(), new Parada(p.getId(), paradaMap.get(p.getId()).getDireccion()));
			proximoColectivo = horario;
			tramos = new ArrayList<Tramo>();
			paradalineas = gp.getEdgeList();
			for (int i = 0; i < paradalineas.size(); i++) {
				t = tramoMap.get(paradas.get(i).getId() + "-" + paradas.get(i + 1).getId());
				origen = pMap.get(paradas.get(i).getId());
				origen.agregarLinea(paradalineas.get(i).getLinea());
				destino = pMap.get(paradas.get(i + 1).getId());
				proximoColectivo = proximoColectivo(paradalineas.get(i).getLinea(), paradas.get(i),
						proximoColectivo + tiempo);
				tramos.add(new Tramo(origen, destino, t.getTipo(), proximoColectivo));
				tiempo = t.getTiempo();
			}
			destino.agregarLinea(origen.getLineas().get(0));
			tramos.add(new Tramo(destino, destino, t.getTipo(), proximoColectivo + t.getTiempo()));
			listaTramos.add(tramos);
		}
		return listaTramos;
	}

	private int proximoColectivo(Linea linea, Parada parada, int horario) {
		boolean ida;
		int nroParada = linea.getParadasIda().indexOf(parada);
		
		if (nroParada == -1) { // si no esta entre las paradas del recorrido de ida
			nroParada = linea.getParadasRegreso().indexOf(parada);
			ida = false;
		} else ida = true;
		//Tramo caminando (si ni siquiera esta en el regreso de la parada)
		if (nroParada == -1)
			return horario;

		// Calcular el tiempo desde el inicio del recorrido a la parada
		Parada origen, destino;
		int tiempo = 0;
		int tope;
		if (ida) tope = nroParada;
		else tope = linea.getParadasIda().size() - 1;
		for (int i = 0; i < tope; i++) {
			origen = linea.getParadasIda().get(i);
			destino = linea.getParadasIda().get(i + 1);
			tiempo += tramoMap.get(origen.getId() + "-" + destino.getId()).getTiempo();
		}
		if (!ida)
			for (int i = 0; i < nroParada; i++) {
				origen = linea.getParadasRegreso().get(i);
				destino = linea.getParadasRegreso().get(i + 1);
				tiempo += tramoMap.get(origen.getId() + "-" + destino.getId()).getTiempo();
			}

		// Ya paso el ultimo colectivo
		if (linea.getFinaliza() + tiempo < horario)
			return -1;

		// Tiempo del proximo colectivo
		for (int j = linea.getComienza(); j <= linea.getFinaliza(); j += linea.getFrecuencia())
			if (j + tiempo >= horario)
				return j + tiempo;

		return -1;
	}

	//clase compuesta utilizada para hacer el grafo, que asocia a una parada y una linea entre si
	private class ParadaLinea {
		private Parada parada;
		private Linea linea;

		public ParadaLinea(Parada parada, Linea linea) {
			this.parada = parada;
			this.linea = linea;
		}

		public Parada getParada() {
			return parada;
		}

		public void setParada(Parada parada) {
			this.parada = parada;
		}

		public Linea getLinea() {
			return linea;
		}

		public void setLinea(Linea linea) {
			this.linea = linea;
		}

		@Override
		public String toString() {
			return parada.getId() + " " + linea.getNombre();
		}

	}

}
