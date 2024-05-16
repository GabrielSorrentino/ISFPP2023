package colectivo.aplicacion;
import colectivo.negocio.Calculo;
import colectivo.interfaz.IGU;
import colectivo.interfaz.Consultora;
public class Coordinador {
	private Calculo calculo;
	private IGU igu;
	private Consultora consultora;
	
	public Calculo getCalculo() {
		return calculo;
	}
	public void setCalculo(Calculo calculo) {
		this.calculo = calculo;
	}
	public IGU getIgu() {
		return igu;
	}
	public void setIgu(IGU igu) {
		this.igu = igu;
	}
	public Consultora getConsultora() {
		return consultora;
	}
	public void setConsultora(Consultora consultora) {
		this.consultora = consultora;
	}
	public void abrirConsultora() {
		this.consultora.setVisible(true);
	}
	
}
