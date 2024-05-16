package colectivo.aplicacion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import colectivo.datos.CargaDatos;
import colectivo.datos.CargaParametros;
import colectivo.interfaz.IGU;
import colectivo.interfaz.Consultora;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Tramo;
import colectivo.negocio.Calculo;
import net.datastructures.TreeMap;

public class AplicacionConsultas {

	public static void main(String[] args) {

		// Cargar parametros
		try {
			CargaParametros.parametros();
		} catch (IOException e) {
			System.err.print(Constantes.ERROR_PARAMETROS);
			System.exit(-1);
		}

		// Cargar datos
		TreeMap<String, Linea> lineas = null;
		TreeMap<Integer, Parada> paradas = null;
		List<Tramo> tramos = null;
		try {
			paradas = CargaDatos.cargarParadas(CargaParametros.getArchivoParada());

			tramos = CargaDatos.cargarTramos(CargaParametros.getArchivoTramo(), paradas);

			lineas = CargaDatos.cargarLineas(CargaParametros.getArchivoLinea(), paradas);

		} catch (FileNotFoundException e) {
			System.err.print(Constantes.ERROR_ARCHIVOS);
			System.exit(-1);
		}


		IGU igu = new IGU();
		Consultora consultora = new Consultora();
		Calculo calculo = new Calculo(paradas, lineas, tramos);
		Coordinador coord = new Coordinador();
		coord.setIgu(igu);
		coord.setCalculo(calculo);
		coord.setConsultora(consultora);
		igu.setCoordinador(coord);
		consultora.setCoordinador(coord);
		try {igu.setVisible(true);}
		catch (Exception e) {e.printStackTrace();}
	}

}
