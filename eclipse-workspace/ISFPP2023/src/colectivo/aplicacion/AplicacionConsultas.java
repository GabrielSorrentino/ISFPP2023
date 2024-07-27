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

	Map<String, Linea> lineas;
	Map<Integer, Parada> paradas;
	List<Tramo> tramos;
	
	public static void main(String[] argv) {
		AplicacionConsultas apl = new AplicacionConsultas();
		apl.cargarDatos();
		apl.iniciar();
	}
	
	public void cargarDatos() {
		try { // Cargar parametros
			CargaParametros.parametros();
		} catch (IOException e) {
			System.err.print(Constantes.ERROR_PARAMETROS);
			System.exit(-1);
		}
		try { // Cargar datos
			paradas = CargaDatos.cargarParadas(CargaParametros.getArchivoParada());
			tramos = CargaDatos.cargarTramos(CargaParametros.getArchivoTramo(), paradas);
			lineas = CargaDatos.cargarLineas(CargaParametros.getArchivoLinea(), paradas);
		} catch (FileNotFoundException e) {
			System.err.print(Constantes.ERROR_ARCHIVOS);
			System.exit(-1);
		}
	}
	public void iniciar() { //instancias
		Pantalla.crearInstancia(lineas, paradas); //el orden en que se instancian estos objetos es importante
		Calculo calculo = Calculo.crearInstancia(paradas, lineas, tramos);
		IGU igu = new IGU();
		Consultora consultora = new Consultora(lineas.size());
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
