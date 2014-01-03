/**
 * 
 */
package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import clasificador.ClasificadorNaiveBayes;

/**
 * @author Alberto Salido L—pez
 * @author Jose Vicente Lara Cuadrado
 * 
 *         Clase para la creacion de la interfaz de usuario de la aplicacion.
 * 
 */
public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Marco de la aplicacion.
	 */
	private JFrame marco;

	/**
	 * Controlador de pesta–as.
	 */
	private JTabbedPane controladorPaneles;

	/**
	 * Clasificador de Naive Bayes para los emails.
	 */
	private ClasificadorNaiveBayes clasificador;

	private JButton informacion;

	/**
	 * Constructor. Crea una nueva ventana.
	 */
	public Vista(ClasificadorNaiveBayes clasificador) {

		this.clasificador = clasificador;

		// Propiedades de la ventana.
		marco = new JFrame();

		// Controlador de pesta–as.
		controladorPaneles = new JTabbedPane();
		controladorPaneles
				.addTab("Entrenamiento", new PanelEntrenamiento(this));
		controladorPaneles.addTab("Evaluacion", new PanelEvaluacion(this));
		controladorPaneles
				.addTab("Clasificacion", new PanelClasificacion(this));

		// A–ade el controlador al marco de la aplicacion.
		marco.add(controladorPaneles, BorderLayout.CENTER);

		// Informacion sobre la aplicacion.
		informacion = new JButton();
		informacion.setText("Informacion");
		informacion.addActionListener(new Accion());

		marco.add(informacion, BorderLayout.SOUTH);

		marco.setTitle("Filtro Anti-SPAM");
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		marco.setSize(650, 400);
		marco.setVisible(true);
		marco.setResizable(false);

	}

	/**
	 * Devuelve el clasificador usado.
	 * 
	 * @return - Clasificador de Naive Bayes usado en la aplicacion.
	 */
	public ClasificadorNaiveBayes getClasificador() {
		return this.clasificador;
	}

	class Accion implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			JOptionPane
					.showMessageDialog(
							null,
							"Realizado por:\nLara Cuadrado, Jose Vicence.\nSalido Lopez, Alberto.\n\nInteligencia Artificial 2. 2013");

		}
	}

}
