package colectivo.datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Tramo;
import colectivo.util.Time;
import net.datastructures.TreeMap;

public class CargaDatos {

	private static final String IDA = "I";
	private static final String REGRESO = "R";
	
	public static TreeMap<Integer, Parada> cargarParadas(String fileName) throws FileNotFoundException {
		Scanner read;
		TreeMap<Integer, Parada> paradas = new TreeMap<Integer, Parada>();

		read = new Scanner(new File(fileName));
		int id;
		double lat, lng;
		String direccion;
		while (read.hasNext()) {
			String renglon = read.nextLine();
			String[] tokens = renglon.split(";");
			if(tokens.length >= 4) {
				id = Integer.parseInt(tokens[0]);
				direccion = tokens[1];
				lat = Double.parseDouble(tokens[2]);
				lng = Double.parseDouble(tokens[3]);
				paradas.put(id, new Parada(id, direccion, lat, lng));
			}
		}
		read.close();

		return paradas;
	}

	public static List<Tramo> cargarTramos(String fileName, TreeMap<Integer, Parada> paradas)
			throws FileNotFoundException {
		Scanner read;
		List<Tramo> tramos = new ArrayList<Tramo>();

		read = new Scanner(new File(fileName));
		read.useDelimiter("\\s*;\\s*");
		Parada v1, v2;
		int tiempo, tipo;
		while (read.hasNext()) {
			v1 = paradas.get(read.nextInt());
			v2 = paradas.get(read.nextInt());
			tiempo = read.nextInt();
			tipo = read.nextInt();
			tramos.add(0, new Tramo(v1, v2, tiempo, tipo));
		}
		read.close();

		return tramos;
	}

	public static TreeMap<String, Linea> cargarLineas(String fileName, TreeMap<Integer, Parada> paradas)
			throws FileNotFoundException {
		Scanner read;

		TreeMap<String, Linea> lineas = new TreeMap<String, Linea>();

		read = new Scanner(new File(fileName));
		String registro;
		Linea linea;
		while (read.hasNext()) {
			registro = read.next();
			String[] campos = registro.split(";");
			linea = lineas.get(campos[0]);
			if (linea == null) {
				linea = new Linea(campos[0],Time.toMins(campos[2]), Time.toMins(campos[3]), Integer.valueOf(campos[4]));
				lineas.put(campos[0], linea);
			}
			if (campos[1].equals(IDA))
				for (int i = 5; i < campos.length; i++)
					linea.agregarParadaIda(paradas.get(Integer.valueOf(campos[i])));
			if (campos[1].equals(REGRESO))
				for (int i = 5; i < campos.length; i++)
					linea.agregarParadaRegreso(paradas.get(Integer.valueOf(campos[i])));
		}
		read.close();

		return lineas;
	}

}
