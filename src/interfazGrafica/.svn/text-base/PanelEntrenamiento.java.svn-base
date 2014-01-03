package interfazGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Map;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import clasificador.ClasificadorNaiveBayes;
import ficheros.OperacionesFicheros;

/**
 * @author Alberto Salido Lopez.
 * @author Jose Vicente Lara Cuadrado.
 * 
 *         Panel para cargar los corpus de correo electronico y entrenar el
 *         sistema.
 * 
 */
public class PanelEntrenamiento extends JPanel {

	/**
	 * Constantes del sistema.
	 */
	private static final String CARGAR = "Cargar";
	private static final String OLVIDAR = "Olvidar";
	private static final String GUARDAR = "Guardar";
	private static final long serialVersionUID = 1L;

	/**
	 * Botones y etiquetas de la aplicacion.
	 */
	private JButton cargarFicheros;
	private JLabel etiquetaCarga;
	private JFileChooser ficheroEntrada;
	private JButton olvidar;
	private JLabel etiquetaOlvidar;
	private JButton guardarConocimiento;
	private JLabel etiquetaGuardar;
	private JFileChooser ficheroSalida;
	private JCheckBox esSpam;
	private JLabel registro;
	private Box cuadro;
	private JTextArea consola;

	/**
	 * Clasificador de Naive Bayes a usar,
	 */
	private ClasificadorNaiveBayes cnv;

	/**
	 * Texto que se mostrara en la consola de la aplicacion.
	 */
	private String textoConsola = "";

	/**
	 * Constructor. Crea una pesta–a para el entrenamiento.
	 * 
	 * @param vista
	 *            - Referencia al marco de la aplicacion en la que esta
	 *            integrada.
	 */
	public PanelEntrenamiento(Vista vista) {
		super();

		// Clasificador usado.
		cnv = vista.getClasificador();

		// Layout
		GroupLayout groupLayout = new GroupLayout(this);
		setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);

		// Inicializacion de etiquetas y botones de la interfaz.
		etiquetaCarga = new JLabel();
		etiquetaCarga.setText("Seleccione el corpus para entrenar:");
		esSpam = new JCheckBox();
		esSpam.setText("Es SPAM?");
		ficheroEntrada = new JFileChooser();
		ficheroEntrada.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		cargarFicheros = new JButton();
		cargarFicheros.setText(CARGAR);
		cargarFicheros.addActionListener(new Accion());
		etiquetaOlvidar = new JLabel();
		etiquetaOlvidar.setText("Olvidar lo aprendido:");
		olvidar = new JButton();
		olvidar.setText(OLVIDAR);
		olvidar.addActionListener(new Accion());
		etiquetaGuardar = new JLabel();
		etiquetaGuardar.setText("Guardar el conocimiento en fichero:");
		ficheroSalida = new JFileChooser();
		ficheroSalida.setFileSelectionMode(JFileChooser.FILES_ONLY);
		guardarConocimiento = new JButton();
		guardarConocimiento.setText(GUARDAR);
		guardarConocimiento.addActionListener(new Accion());
		registro = new JLabel();
		registro.setText("Registro de actividad:");
		cuadro = Box.createHorizontalBox();
		consola = new JTextArea(textoConsola);
		cuadro.add(new JScrollPane(consola));
		actualizaTextoConsola("Bienvenido al Modulo de Entrenamiento.\n");
		JLabel espacio = new JLabel();
		espacio.setText("");

		// Colocacion de los elementos de la interfaz en el layout.
		groupLayout.setHorizontalGroup(groupLayout
				.createSequentialGroup()
				.addGroup(
						groupLayout
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addComponent(etiquetaCarga)
								.addComponent(esSpam)
								.addComponent(etiquetaOlvidar)
								.addComponent(etiquetaGuardar)
								.addComponent(espacio).addComponent(registro)
								.addComponent(cuadro))
				.addGroup(
						groupLayout
								.createParallelGroup(
										GroupLayout.Alignment.LEADING)
								.addComponent(cargarFicheros)
								.addComponent(olvidar)
								.addComponent(guardarConocimiento)));
		groupLayout.setVerticalGroup(groupLayout
				.createSequentialGroup()
				.addComponent(etiquetaCarga)
				.addGroup(
						groupLayout
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(esSpam)
								.addComponent(cargarFicheros))
				.addGroup(
						groupLayout
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(etiquetaOlvidar)
								.addComponent(olvidar))
				.addGroup(
						groupLayout
								.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
								.addComponent(etiquetaGuardar)
								.addComponent(guardarConocimiento))
				.addComponent(espacio).addComponent(registro)
				.addComponent(cuadro));
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
	 * Metodo privado que actuliza el texto que se muestra en la consola.
	 * 
	 * @param texto
	 *            - Texto a anadir en la consola.
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
	 * @author Alberto Salido Lopez.
	 * @author Jose Vicente Lara Cuadrado.
	 * 
	 *         Clase interna para gestionar las acciones.
	 * 
	 */
	class Accion implements ActionListener {

		/**
		 * Accion a realizar cuando se pulsa el boton.
		 */
		public void actionPerformed(ActionEvent e) {

			String comando = e.getActionCommand();
			if (comando.equals(OLVIDAR)) {
				JFrame frame = new JFrame();
				int result = JOptionPane.showConfirmDialog(frame,
						"Seguro que desea que lo olvide todo?", "Aviso!", 2);
				if (result == JOptionPane.OK_OPTION) {
					cnv.olvidarConocimiento();
					actualizaTextoConsola("Conocimiento borrado.\n");
				}
			} else if (comando.equals(GUARDAR)) {
				int seleccion = ficheroSalida.showSaveDialog(null);
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					String ruta = ficheroSalida.getSelectedFile()
							.getAbsolutePath();
					try {
						OperacionesFicheros.escribeProbabilidadesEnFichero(ruta
								+ ".conoc", cnv.getProbSpamNormalizadas(), cnv.getProbSPAM());
						actualizaTextoConsola("Fichero de conocimiento guardado en '"
								+ ruta + ".conoc.\n");
					} catch (Exception e1) {
						actualizaTextoConsolaError("Error guardando ficheros.\n");
					}
				}
			} else if (comando.equals(CARGAR)) {
				int seleccion = ficheroEntrada.showDialog(null, "Selecionar");
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					try {
						Map<String, Integer> ocurrencias = OperacionesFicheros
								.directorioAMap(ficheroEntrada
										.getSelectedFile().getAbsolutePath());
						cnv.calculaProbabilidadesCondicionales(esSpam.isSelected(),
								ocurrencias);
						cnv.calculaProbilidadesAPriori(esSpam.isSelected(), OperacionesFicheros
								.numFicheros(ficheroEntrada.getSelectedFile()
										.getAbsolutePath()));
						String nombreFichero = ficheroEntrada.getSelectedFile()
								.getName();
						String spam = "HAM";
						if (esSpam.isSelected()) {
							spam = "SPAM";
						}
						actualizaTextoConsola("Fichero '" + nombreFichero
								+ "' aprendido como " + spam + ".\n");
					} catch (Exception e1) {
						actualizaTextoConsolaError("El elemento cargado no es un directorio valido.\n");
					}
					
				}
			}
		}
	}
}