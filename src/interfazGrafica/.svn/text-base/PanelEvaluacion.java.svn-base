/**
 * 
 */
package interfazGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Map;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import clasificador.ClasificadorNaiveBayes;
import ficheros.OperacionesFicheros;

/**
 * @author Alberto Salido L—pez
 * @author Jose Vicente Lara Cuadrado.
 * 
 *         Panel para evaluar un conjunto de correos.
 * 
 */
public class PanelEvaluacion extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Constantes del sistema.
	 */
	private static final String CARGAR_CONOCIMIENTO = "Seleccionar";
	private static final String CARGAR_CORPUS = "Cargar";

	/**
	 * Botones y etiquetas de la pesta–a.
	 */
	private JButton cargarConocimiento;
	private JButton cargarFicheros;
	private JLabel etiquetaCargaConocimiento;
	private JLabel etiquetaCargaFicheros;
	private JFileChooser ficherosEntrada, conocimientoEntrada;
	private JTextArea consola;
	private Box cuadro;
	private JLabel etiquetaResultado;

	/**
	 * Clasificador a ser usado.
	 */
	private ClasificadorNaiveBayes cnv;

	/**
	 * Texto que aparecer‡ en la consola de registro.
	 */
	private String textoConsola = "";

	/**
	 * Constructor. Crea los botones y las acciones.
	 * 
	 * @param vista
	 *            - Referencia a la ventana desde la que se accede a esta
	 *            pesta–a.
	 */
	public PanelEvaluacion(Vista vista) {
		super();

		// Layout.
		GroupLayout groupLayout = new GroupLayout(this);
		setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);

		// Obtiene el clasificador de Naive Bayes de la vista.
		this.cnv = vista.getClasificador();

		// Inicializa los elementos del layut.
		conocimientoEntrada = new JFileChooser();
		conocimientoEntrada.setFileSelectionMode(JFileChooser.FILES_ONLY);

		etiquetaCargaConocimiento = new JLabel();
		etiquetaCargaConocimiento
				.setText("Selecione el fichero con el conocimiento aprendido:");

		cargarConocimiento = new JButton();
		cargarConocimiento.setText(CARGAR_CONOCIMIENTO);
		cargarConocimiento.addActionListener(new Accion());

		ficherosEntrada = new JFileChooser();
		ficherosEntrada
				.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		etiquetaCargaFicheros = new JLabel();
		etiquetaCargaFicheros.setText("Selecione el corpus para evaluar:");

		cargarFicheros = new JButton();
		cargarFicheros.setText(CARGAR_CORPUS);
		cargarFicheros.addActionListener(new Accion());

		etiquetaResultado = new JLabel();
		etiquetaResultado.setText("Resultado:");

		cuadro = Box.createHorizontalBox();
		consola = new JTextArea(textoConsola);
		cuadro.add(new JScrollPane(consola));

		actualizaTextoConsola("Bienvenido al Modulo de Evaluacion.\n");

		// Posiciona los elementos en el layout.
		groupLayout.setHorizontalGroup(groupLayout
				.createSequentialGroup()
				.addGroup(
						groupLayout
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addComponent(etiquetaCargaConocimiento)
								.addComponent(etiquetaCargaFicheros)
								.addComponent(etiquetaResultado)
								.addComponent(cuadro))
				.addGroup(
						groupLayout
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addComponent(cargarConocimiento)
								.addComponent(cargarFicheros)));
		groupLayout.setVerticalGroup(groupLayout
				.createSequentialGroup()
				.addGroup(
						groupLayout
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(etiquetaCargaConocimiento)
								.addComponent(cargarConocimiento))
				.addGroup(
						groupLayout
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(etiquetaCargaFicheros)
								.addComponent(cargarFicheros))
				.addComponent(etiquetaResultado).addComponent(cuadro));

	}

	/**
	 * Metodo privador que devuelve el tiempo actual del sistema.
	 * 
	 * @return Devuelve un String con el tiempo y los minutos [HH:MM].
	 */
	private String obtenHoraActual() {
		Calendar tiempo = Calendar.getInstance();
		return "[" + tiempo.get(Calendar.HOUR_OF_DAY) + ":"
				+ tiempo.get(Calendar.MINUTE) + "]";
	}

	/**
	 * Metodo privado que actuliza el texto que se muestra en la consola.
	 * 
	 * @param texto
	 *            - Texto a a–adir en la consola.
	 */
	private void actualizaTextoConsola(String texto) {
		textoConsola += obtenHoraActual() + " " + texto;
		consola.setEditable(false);
		consola.setText(textoConsola);
	}

	/**
	 * Metodo privado que actuliza el texto que se muestra en la consola en caso
	 * de error.
	 * 
	 * @param texto
	 *            - Texto a anadir en la consola.
	 */
	private void actualizaTextoConsolaError(String text) {
		textoConsola += obtenHoraActual() + " ERROR: " + text;
		consola.setEditable(false);
		consola.setText(textoConsola);
	}

	/**
	 * 
	 * @author Alberto Salido L—pez
	 * @author Jose Vicente Lara Cuadrado.
	 * 
	 *         Clase interna para gestionar las acciones.
	 * 
	 */
	class Accion implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// Nombre del boton a utilizar.
			String comando = e.getActionCommand();
			if (comando.equals(CARGAR_CONOCIMIENTO))
				try {

					int seleccion = conocimientoEntrada.showDialog(null,
							"Selecionar");
					if (seleccion == JFileChooser.APPROVE_OPTION) {
						String nombre = conocimientoEntrada.getSelectedFile()
								.getName();
						Map<String, Float> conocimiento = OperacionesFicheros
								.leeFicheroProbabilidades(conocimientoEntrada
										.getSelectedFile().getAbsolutePath());
						cnv.cargaConocimiento(conocimiento);
						actualizaTextoConsola("Fichero de conocimiento '"
								+ nombre + "' aprendido.\n");
					}
				} catch (Exception e1) {
					actualizaTextoConsolaError("Fichero de conocimiento no valido.\n");
				}
			else if (comando.equals(CARGAR_CORPUS)) {
				if (cnv.getProbSpamNormalizadas().isEmpty()) {
					actualizaTextoConsola("No hay ningun conocimiento previo.\n");
				} else {
					int seleccion = ficherosEntrada.showDialog(null,
							"Selecionar");
					Float[] probs = new Float[4];
					if (seleccion == JFileChooser.APPROVE_OPTION) {
						try {
							probs = OperacionesFicheros
									.clasificaConjuntoFicheros(ficherosEntrada
											.getSelectedFile()
											.getAbsolutePath(), cnv);
							int numeroEmail = (int) (probs[4] + probs[5]);
							actualizaTextoConsola("Evaluacion completada:\n");
							actualizaTextoConsola("Numero total de emails procesados: "
									+ numeroEmail + "\n");
							actualizaTextoConsola("Numero de SPAM: "
									+ probs[5].intValue() + "\n");
							actualizaTextoConsola("Numero de HAM: "
									+ probs[4].intValue() + "\n");
							actualizaTextoConsola("Correctos clasificados correctamente: "
									+ probs[0].toString() + "% (HAM->HAM)\n");
							actualizaTextoConsola("Correctos clasificados incorrectamente: "
									+ probs[1].toString() + "% (HAM->SPAM)\n");
							actualizaTextoConsola("Incorrectos clasificados incorrectamente: "
									+ probs[2].toString() + "% (SPAM->HAM)\n");
							actualizaTextoConsola("Incorrectos clasificados correctamente: "
									+ probs[3].toString() + "% (SPAM->SPAM)\n");
						} catch (Exception e1) {
							actualizaTextoConsolaError("Seleccione un directorio.\n");
						}
					}
				}
			}
		}
	}
}
