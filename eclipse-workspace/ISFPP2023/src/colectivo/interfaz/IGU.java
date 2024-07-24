package colectivo.interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import colectivo.aplicacion.Coordinador;
import colectivo.util.Time;
import colectivo.modelo.Linea;
import colectivo.modelo.Parada;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IGU extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Coordinador coord;

	public IGU(Map<String, Linea> lineas, Map<Integer, Parada> paradas) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 485, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnPrograma = new JMenu("Programa");
		menuBar.add(mnPrograma);
		
		JMenuItem mntmCreditos = new JMenuItem("Creditos");
		mntmCreditos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Instancia Supervisada de Formacion Practica y Profesional\n"
						+ "Materia: Programacion Orientada a Objetos\n"
						+ "Autor: Gabriel Sorrentino\n"
						+ "Carreras: Analista Programador Universitario, Licenciatura en Informática\n"
						+ "Profesores: Renato Mazzanti, Gustavo Samec\n"
						+ "Fecha: 20 de marzo de 2024");
			}
		});
		mnPrograma.add(mntmCreditos);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que quieres salir?",
                        "Confirmación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
			}
		});
		mnPrograma.add(mntmSalir);
		
		JMenu mnConsultas = new JMenu("Consultas");
		menuBar.add(mnConsultas);
		
		JMenuItem mntmCalcular = new JMenuItem("Realizar calculos");
		mntmCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				coord.abrirConsultora();
			}
		});
		mnConsultas.add(mntmCalcular);
		
		JMenuItem mntmVerParadas = new JMenuItem("Ver paradas");
		mntmVerParadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//operacion para mostrar paradas
			}
		});
		mnConsultas.add(mntmVerParadas);
		
		JMenuItem mntmVerLineas = new JMenuItem("Ver lineas");
		mntmVerLineas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//operacion para mostrar lineas
			}
		});
		
		mnConsultas.add(mntmVerLineas);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		setTitle("ISFPP");
		
		JLabel lblISFPP = new JLabel("Instancia Supervisada de Formacion Practica y Profesional");
		lblISFPP.setFont(new Font("Georgia", Font.BOLD, 14));
		
		JLabel lblPOO = new JLabel("Programacion Orientada a Objetos");
		lblPOO.setFont(new Font("Arial", Font.BOLD, 13));
		
		//imagen de colectivo como decoracion
		ImageIcon foto = new ImageIcon(getClass().getResource("colectivo.jpeg"));
		JLabel lblColectivo = new JLabel();
		lblColectivo.setIcon(foto);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblISFPP))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(118)
							.addComponent(lblPOO))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(97)
							.addComponent(lblColectivo)))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblISFPP)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPOO)
					.addGap(18)
					.addComponent(lblColectivo)
					.addContainerGap(59, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	public void setCoordinador(Coordinador coord) {
		this.coord = coord;
	}

}
