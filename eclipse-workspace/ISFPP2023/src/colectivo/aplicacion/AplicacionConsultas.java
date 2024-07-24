package colectivo.aplicacion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import colectivo.datos.CargaDatos;
import colectivo.datos.CargaParametros;
import colectivo.interfaz.IGU;
import colectivo.interfaz.Consultora;
import colectivo.interfaz.Pantalla;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import colectivo.modelo.Tramo;
import colectivo.negocio.Calculo;

public class AplicacionConsultas {

	public static void main(String[] argv) {

		// Cargar parametros
		try {
			CargaParametros.parametros();
		} catch (IOException e) {
			System.err.print(Constantes.ERROR_PARAMETROS);
			System.exit(-1);
		}

		// Cargar datos
		Map<String, Linea> lineas = null;
		Map<Integer, Parada> paradas = null;
		List<Tramo> tramos = null;
		try {
			paradas = CargaDatos.cargarParadas(CargaParametros.getArchivoParada());

			tramos = CargaDatos.cargarTramos(CargaParametros.getArchivoTramo(), paradas);

			lineas = CargaDatos.cargarLineas(CargaParametros.getArchivoLinea(), paradas);

		} catch (FileNotFoundException e) {
			System.err.print(Constantes.ERROR_ARCHIVOS);
			System.exit(-1);
		}


		IGU igu = new IGU(lineas, paradas);
		Consultora consultora = new Consultora(lineas.size());
		Pantalla.crearInstancia(lineas, paradas);
		Calculo calculo = Calculo.crearInstancia(paradas, lineas, tramos);
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
