package colectivo.interfaz;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import colectivo.util.Time;
import colectivo.aplicacion.Coordinador;
import colectivo.modelo.*;
import colectivo.negocio.Calculo;
import java.util.List;

public class Consultora extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtPrimeraParada;
	private JTextField txtSegundaParada;
	private JTextField txtHora;
	private Coordinador coord;
	private int totalLineas; //cantidad de lineas por defecto
	
	public Consultora(int totalLineas) {
		this.totalLineas = totalLineas;
		setBounds(100, 100, 450, 300);
		setTitle("Consultas");
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		JLabel lblPrimeraParada = new JLabel("ID de la primera parada");
		
		txtPrimeraParada = new JTextField();
		txtPrimeraParada.setColumns(10);
		
		JLabel lblSegundaParada = new JLabel("ID de la segunda Parada");
		
		txtSegundaParada = new JTextField();
		txtSegundaParada.setColumns(10);
		
		JLabel lblHora = new JLabel("Hora");
		
		txtHora = new JTextField();
		txtHora.setColumns(10);
		
		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calcularTramo(txtPrimeraParada.getText(), txtSegundaParada.getText(),
						txtHora.getText());
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(212, Short.MAX_VALUE)
					.addComponent(btnCalcular)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnCerrar)
					.addGap(46))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblHora)
						.addComponent(lblSegundaParada, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(txtHora, Alignment.LEADING)
							.addComponent(txtSegundaParada, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(txtPrimeraParada, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPrimeraParada, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPrimeraParada)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtPrimeraParada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblSegundaParada)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtSegundaParada, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblHora)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtHora, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCerrar)
						.addComponent(btnCalcular))
					.addGap(21))
		);
		getContentPane().setLayout(groupLayout);
	}
	public void setCoordinador(Coordinador coord) {
		this.coord = coord;
	}
	private void calcularTramo(String PraParada, String SdaParada, String hora) {
		try { //verifica que los ID ingresados sean numeros y la hora haya sido ingresada bien
			Integer.parseInt(PraParada);
			Integer.parseInt(SdaParada);
			Time.toMins(hora);
			mostrarResultado(PraParada, SdaParada, hora);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Alguno de los valores fue ingresado incorrectamente.\n"
					+ "Intente de nuevo.");
			return;
		}
	}
	private void mostrarResultado(String IDactual, String IDdestino, String hora) {
		Calculo c = coord.getCalculo();
		String respuesta = "";
		List<List<Tramo>> matriz = c.recorridos(IDactual, IDdestino, Time.toMins(hora), totalLineas);
	}

}
