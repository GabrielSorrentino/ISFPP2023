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
import colectivo.aplicacion.Constantes;
import colectivo.modelo.*;
import colectivo.negocio.Calculo;
import java.util.List;

public class Consultora extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtPrimeraParada;
	private JTextField txtSegundaParada;
	private JTextField txtHora;
	private Coordinador coord;
	private int totalLineas; //cantidad total de lineas por defecto
	
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
	protected void calcularTramo(String PraParada, String SdaParada, String hora) {
		Parada origen, destino;
		int horario;
		Calculo c = Calculo.getInstancia();
		Pantalla pantalla = Pantalla.getInstancia();
		try {
			origen = pantalla.obtenerParada(Integer.parseInt(PraParada));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, Constantes.ERROR_PRIMERA_PARADA);
			return;
		}
		try {
			destino = pantalla.obtenerParada(Integer.parseInt(SdaParada));
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, Constantes.ERROR_SEGUNDA_PARADA);
			return;
		}
		if (origen.equals(destino)) {
			JOptionPane.showMessageDialog(null, Constantes.ERROR_PARADAS_IGUALES);
			return;
		}
		try {
			horario = Time.toMins(hora);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, Constantes.ERROR_HORA);
			return;
		}
		List<List<Tramo>> recorridos = c.recorridos(origen, destino, horario, totalLineas);
		if (recorridos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "¡Oh, no! No se encontraron recorridos posibles.");
	        return;
		}
		StringBuilder infoRecorridos = new StringBuilder("Recorridos posibles:\n");
		for (List<Tramo> recorrido : recorridos) {
            infoRecorridos.append("Recorrido:\n");
            for (Tramo tramo : recorrido) {
                String lineaNombre = tramo.getTipo() == 2 ? tramo.getInicio().getLineas().get(0).getNombre() : "Caminando";
                infoRecorridos.append(String.format("De %s a %s en %s, tiempo estimado: %d minutos\n",
                    tramo.getInicio().getDireccion(),
                    tramo.getFin().getDireccion(),
                    lineaNombre,
                    tramo.getTiempo()
                ));
            }
            infoRecorridos.append("\n");
        }

        JOptionPane.showMessageDialog(null, infoRecorridos.toString());
	}
	
}
