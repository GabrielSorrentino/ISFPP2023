package colectivo.aplicacion;

public class Constantes {

	public static final int TRAMO_COLECTIVO = 1;
	public static final int TRAMO_CAMINANDO = 2;
	
	public static final String ERROR_PARAMETROS = "Error al cargar parametros";
	public static final String ERROR_ARCHIVOS = "Error al cargar archivos de datos";
	public static final String ERROR_ENTRADA = "Alguno de los valores introducidos es invalido";
	
	public static final String CREDITOS = "Instancia Supervisada de Formacion Practica y Profesional\n"
			+ "Materia: Programacion Orientada a Objetos\n"
			+ "Autor: Gabriel Sorrentino\n"
			+ "Carreras: Analista Programador Universitario, Licenciatura en Informática\n"
			+ "Profesores: Renato Mazzanti, Gustavo Samec\n"
			+ "Fecha: 20 de marzo de 2024";
	
	public static final String ERROR_PRIMERA_PARADA = "La primera parada ingresada no existe o se ingreso un dato incorrecto.\nIntente de nuevo.";
	public static final String ERROR_SEGUNDA_PARADA = "La segunda parada ingresada no existe o se ingreso un dato incorrecto.\nIntente de nuevo.";
	public static final String ERROR_PARADAS_IGUALES = "Las dos paradas son iguales, basicamente no hay nada que hacer.\nIntente de nuevo.";
	public static final String ERROR_HORA = "La hora no fue ingresada correctamente. Intente de nuevo.\n(Ingrese en formato hh:mm)";
}
