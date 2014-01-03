/**
 * 
 */
package interfazGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
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
 * @author Alberto Salido Lopez.
 * @author Jose Vicente Lara Cuadrado.
 * 
 *         Clase para comprobar si un correo es SPAM o no, en base a lo
 *         aprendido en el Modulo de aprendizaje.
 */
public class PanelClasificacion extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Constantes del sistema.
	 */
	private static final String CARGAR_CONOCIMIENTO = "Seleccionar";
	private static final String CARGAR_CORREO = "Cargar";

	/**
	 * Botones y etiquetas de la aplicacion.
	 */
	private JLabel etiquetaCargarConocimiento;
	private JButton cargarConocimiento;
	private JFileChooser conocimientoEntrada;
	private JButton cargarCorreo;
	private JLabel etiquetaCarga;
	private JFileChooser emailEntrada;
	private JLabel etiquetaResultados;
	private Box cuadro;
	private JTextArea consola;

	/**
	 * Clasificador de Naive Bayes.
	 */
	private ClasificadorNaiveBayes cnv;

	/**
	 * Texto que se mostrara en la consola de la aplcicacion.
	 */
	private String textoConsola = "";

	/**
	 * Constructor. Crea una nueva pesta–a para clasificar emails.
	 * 
	 * @param vista
	 *            - Referencia a la ventan principal de la aplicacion.
	 */
	public PanelClasificacion(Vista vista) {
		super();

		cnv = vista.getClasificador();

		// Layout
		GroupLayout groupLayout = new GroupLayout(this);
		setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);

		// Inicializa los elementos del panel.
		etiquetaCargarConocimiento = new JLabel();
		etiquetaCargarConocimiento
				.setText("Seleccione el fichero de conocimiento aprendido:");

		cargarConocimiento = new JButton();
		cargarConocimiento.setText(CARGAR_CONOCIMIENTO);
		cargarConocimiento.addActionListener(new Accion());

		conocimientoEntrada = new JFileChooser();
		conocimientoEntrada.setFileSelectionMode(JFileChooser.FILES_ONLY);

		etiquetaCarga = new JLabel();
		etiquetaCarga.setText("Seleccione el email:");

		cargarCorreo = new JButton();
		cargarCorreo.setText(CARGAR_CORREO);
		cargarCorreo.addActionListener(new Accion());

		emailEntrada = new JFileChooser();
		emailEntrada.setFileSelectionMode(JFileChooser.FILES_ONLY);

		etiquetaResultados = new JLabel();
		etiquetaResultados.setText("Resultado:");

		cuadro = Box.createHorizontalBox();
		consola = new JTextArea(textoConsola);
		cuadro.add(new JScrollPane(consola));

		actualizaTextoConsola("Bienvenido al Modulo de Clasificacion\n");

		// Posiciona los elementos en el layout.
		groupLayout.setHorizontalGroup(groupLayout
				.createSequentialGroup()
				.addGroup(
						groupLayout
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addComponent(etiquetaCargarConocimiento)
								.addComponent(etiquetaCarga)
								.addComponent(etiquetaResultados)
								.addComponent(cuadro))
				.addGroup(
						groupLayout
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addComponent(cargarConocimiento)
								.addComponent(cargarCorreo)));
		groupLayout.setVerticalGroup(groupLayout
				.createSequentialGroup()
				.addGroup(
						groupLayout
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(etiquetaCargarConocimiento)
								.addComponent(cargarConocimiento))
				.addGroup(
						groupLayout
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(etiquetaCarga)
								.addComponent(cargarCorreo))
				.addComponent(etiquetaResultados).addComponent(cuadro));
	}

	/**
	 * Metodo privado que devuelve el tiempo actual del sistema.
	 * 
	 * @return Devuelve un String con el tiempo y los minutos [HH:MM].
	 */
	private String obtenHoraActual() {
		Calendar tiempo = Calendar.getInstance();
		return "[" + tiempo.get(Calendar.HOUR_OF_DAY) + ":"
				+ tiempo.get(Calendar.MINUTE) + "]";
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
	 * Metodo privador que actuliza el texto que se muestra en la consola.
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
			if (comando.equals(CARGAR_CONOCIMIENTO)) {
				int seleccion = conocimientoEntrada.showDialog(null,
						"Selecionar");
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					String nombre = conocimientoEntrada.getSelectedFile()
							.getName();
					Map<String, Float> conocimiento = new HashMap<String, Float>();
					try {
						conocimiento = OperacionesFicheros
								.leeFicheroProbabilidades(conocimientoEntrada
										.getSelectedFile().getAbsolutePath());
						cnv.cargaConocimiento(conocimiento);
						actualizaTextoConsola("Fichero '" + nombre
								+ "' aprendido.\n");
					} catch (Exception e1) {
						actualizaTextoConsolaError("Fichero de conocimiento no valido.\n");
					}
				}
			} else if (comando.equals(CARGAR_CORREO)) {
				if (cnv.getProbSpamNormalizadas().isEmpty()) {
					actualizaTextoConsola("No hay ningun conocimiento previo.\n");
				} else {
					int seleccion = conocimientoEntrada.showDialog(null,
							"Selecionar");
					if (seleccion == JFileChooser.APPROVE_OPTION) {
						try {
							Map<String, Integer> ocurrencias = OperacionesFicheros
									.ficheroaMap(conocimientoEntrada
											.getSelectedFile()
											.getAbsolutePath());
							boolean spam = cnv.clasifica(ocurrencias);
							if (spam) {
								actualizaTextoConsola("El Email seleccionado es SPAM\n");
							} else {
								actualizaTextoConsola("El Email seleccionado es HAM\n");
							}
						} catch (IOException e1) {
							actualizaTextoConsolaError("Fichero no valido.\n");
						}
					}
				}
			}
		}
	}
}
