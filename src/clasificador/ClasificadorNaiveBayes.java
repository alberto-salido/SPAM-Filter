/**
 * 
 */
package clasificador;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Alberto Salido Lopez.
 * @author Jose Vicente Lara Cuadrado.
 * 
 *         Clasificador de Naive Bayes para determinar si un correo electronico
 *         es SPAM o no.
 * 
 */
public class ClasificadorNaiveBayes {

	/**
	 * Tablas hash con las ocurrencias de cada palabra en HAM y SPAM, con las
	 * probabilidades condicionadas de cada palabra que aparecen en un correo
	 * electronico SPAM y HAM y probabilidades condicionadas de cada palabra
	 * normalizadas.
	 */
	private Map<String, Float> numSPAM;
	private Map<String, Float> numHAM;
	private Map<String, Float> probCondSpam;
	private Map<String, Float> probCondHam;
	private Map<String, Float> probConNorm;

	/**
	 * Probabilidad a priori de que un correo sea SPAM o HAM.
	 */
	private Float probSpam;
	private Float probHam;

	/**
	 * Numero de ficheros SPAM, HAM y numero total.
	 */
	private int numeroFicherosSpam;
	private int numeroFicherosHam;
	private int numeroFicheros;

	/**
	 * Numero total de ejemplos en el sistema. Es decir, numero total de
	 * palabras almacenandas.
	 */
	private Integer numeroPalabrasHam;
	private Integer numeroPalabrasSpam;

	/**
	 * Constructor. Crea un nuevo clasificador de Naive Bayes sin datos
	 * aprendidos.
	 */
	public ClasificadorNaiveBayes() {

		this.numHAM = new HashMap<String, Float>();
		this.numSPAM = new HashMap<String, Float>();
		this.probCondHam = new HashMap<String, Float>();
		this.probCondSpam = new HashMap<String, Float>();
		this.probConNorm = new HashMap<String, Float>();
		this.probHam = new Float(0);
		this.probSpam = new Float(0);
		this.numeroFicherosHam = 0;
		this.numeroFicherosSpam = 0;
		this.numeroFicheros = 0;
		this.numeroPalabrasHam = 0;
		this.numeroPalabrasSpam = 0;
	}

	/**
	 * Devuelve la probabilidad a priori de que un email sea HAM.
	 * 
	 * @return Float con la probabilidad a priori de HAM
	 */
	public Float getProbHAM() {
		return this.probHam;
	}

	/**
	 * Devuelve la probabilidad a priori de que un email sea SPAM.
	 * 
	 * @return Float con la probabilidad a priori de SPAM.
	 */
	public Float getProbSPAM() {
		return this.probSpam;
	}

	/**
	 * Devuelve las probabilidades condicionadas normalizadas.
	 * 
	 * @return Hashtable con las probabilidades condicioandas normalizadas.
	 */
	public Map<String, Float> getProbSpamNormalizadas() {
		return this.probConNorm;
	}

	/**
	 * Probablidades condicionadas de palabras SPAM.
	 * 
	 * @return Probabilidades condicionadas de palabras SPAM sin normalizar.
	 */
	public Map<String, Float> getProbCondSPAM() {
		return this.probCondSpam;
	}

	/**
	 * Probablidades condicionadas de palabras HAM.
	 * 
	 * @return Probabilidades condicionadas de palabras HAM sin normalizar.
	 */
	public Map<String, Float> getProbCondHAM() {
		return this.probCondHam;
	}

	/**
	 * Resetea las probabilidades aprendidas.
	 */
	public void olvidarConocimiento() {
		this.numHAM = new HashMap<String, Float>();
		this.numSPAM = new HashMap<String, Float>();
		this.probCondHam = new HashMap<String, Float>();
		this.probCondSpam = new HashMap<String, Float>();
		this.probConNorm = new HashMap<String, Float>();
		this.probHam = new Float(0);
		this.probSpam = new Float(0);
		this.numeroFicherosHam = 0;
		this.numeroFicherosSpam = 0;
		this.numeroFicheros = 0;
		this.numeroPalabrasHam = 0;
		this.numeroPalabrasSpam = 0;
	}

	/**
	 * Calcula las probabilidades a priori dado el numero de ficheros que hay de
	 * un tipo determinado. Esta probabilidad se calcula como el numero de
	 * ficheros de un tipo determinado entre el total de ficheros en el sistema.
	 * 
	 * @param spam
	 *            - Indica si los ficheros son SPAM o no.
	 * @param numeroFicheros
	 *            - Numero de ficheros del tipo indicado anteriormente.
	 */
	public void calculaProbilidadesAPriori(boolean spam, int numeroFicheros) {
		this.numeroFicheros += numeroFicheros;
		if (spam) {
			this.numeroFicherosSpam += numeroFicheros;
		} else {
			this.numeroFicherosHam += numeroFicheros;
		}
		this.probSpam = new Float((float) this.numeroFicherosSpam
				/ this.numeroFicheros);
		this.probHam = new Float((float) this.numeroFicherosHam
				/ this.numeroFicheros);
	}

	/**
	 * Calcula las probabilidades condicionadas a que una palabra que aparece en
	 * un email SPAM o HAM, y normaliza su valor en funcion del numero de
	 * muestras totales en el sistema.
	 * 
	 * @param esSPAM
	 *            - Indica si el Map pasado es de SPAM o de HAM.
	 * @param ocurrencias
	 *            - Map con las ocurrencias de cada palabra.
	 */
	public void calculaProbabilidadesCondicionales(boolean esSPAM,
			Map<String, Integer> ocurrencias) {

		// Actualiza el numero total de palabras de cada tipo.
		Iterator<String> mapIterator = ocurrencias.keySet().iterator();
		while (mapIterator.hasNext()) {
			String key = mapIterator.next();

			if (esSPAM) {
				this.numeroPalabrasSpam += ocurrencias.get(key);
			} else {
				this.numeroPalabrasHam += ocurrencias.get(key);
			}
		}

		// Actualiza el numero de palabras de cada tipo
		mapIterator = ocurrencias.keySet().iterator();
		while (mapIterator.hasNext()) {
			String key = mapIterator.next();
			float valorAntiguo = 0;

			if (esSPAM) {
				if (this.numSPAM.containsKey(key)) {
					valorAntiguo = this.numSPAM.get(key);
				}
				this.numSPAM.put(key,
						valorAntiguo + ((float) ocurrencias.get(key)));
			} else {
				if (this.numHAM.containsKey(key)) {
					valorAntiguo = this.numHAM.get(key);
				}
				this.numHAM.put(key,
						valorAntiguo + ((float) ocurrencias.get(key)));
			}
		}

		// Actualiza las prob. cond.
		actualizaProbabilidadesCondicionales();

		// Normaliza
		normalizaConjuntos();
	}

	/**
	 * Actualiza las probabilidades condicionadas en funcion del conocimiento
	 * aprendido. La probablidad condicionada de una palabra se calcula como el
	 * numero de veces que aparece esa palabra en un tipo de email (SPAM o HAM)
	 * entre el total de palabras del mismo tipo.
	 */
	private void actualizaProbabilidadesCondicionales() {
		Iterator<String> it = this.numSPAM.keySet().iterator();
		while (it.hasNext()) {
			String palabra = it.next();
			this.probCondSpam.put(palabra, numSPAM.get(palabra)
					/ this.numeroPalabrasSpam);
		}
		it = this.numHAM.keySet().iterator();
		while (it.hasNext()) {
			String palabra = it.next();
			this.probCondHam.put(palabra, numHAM.get(palabra)
					/ this.numeroPalabrasHam);
		}
	}

	/**
	 * Normaliza las probabilidaes condicionadas en funcion del numero de
	 * elementos totales de cada tipo.
	 */
	private void normalizaConjuntos() {

		this.probConNorm = new HashMap<String, Float>();

		Iterator<String> it;

		Map<String, Float> spam = this.getProbCondSPAM();
		Map<String, Float> ham = this.getProbCondHAM();

		if (spam.keySet().size() > ham.keySet().size()) {
			it = ham.keySet().iterator();
		} else {
			it = spam.keySet().iterator();
		}

		while (it.hasNext()) {
			String palabra = it.next();
			Float valorSpam = 0f, valorHam = 0f;
			if (spam.containsKey(palabra)) {
				valorSpam = spam.get(palabra);
			}
			if (ham.containsKey(palabra)) {
				valorHam = ham.get(palabra);
			}
			this.probConNorm.put(palabra, normaliza(valorSpam, valorHam));
		}
	}

	/**
	 * Normaliza las probabilidades de dos numeros pasados por parametro.
	 * 
	 * @param v1
	 *            - Probabilidad 1
	 * @param v2
	 *            - Probabilidad 2
	 * @return Probabilidad normalizada con respecto al primer valor.
	 */
	private Float normaliza(Float v1, Float v2) {
		Float suma = v1 + v2;
		Float a = ((float) v1 / suma);
		return a;
	}

	/**
	 * Carga un cocnimiento estructurado como una tabla hash con las palabras y
	 * sus probabilidades condicionadas.
	 * 
	 * @param conocimiento
	 */
	public void cargaConocimiento(Map<String, Float> conocimiento) {
		Iterator<String> it = conocimiento.keySet().iterator();

		// Actualiza con el contenido del fichero.
		while (it.hasNext()) {
			String palabra = it.next();

			// Las probabilidades a priori debene estar almacenadas en el Map
			// con las claves (key) prob_spam y prob_ham, respectivamente.
			// El resto de probabilidades seran normalizadas con respecto a
			// SPAM.
			if (palabra.equals("prob_spam")) {
				this.probSpam = conocimiento.get("prob_spam");
			} else if (palabra.equals("prob_ham")) {
				this.probHam = conocimiento.get("prob_ham");
			} else {
				this.probConNorm.put(palabra, conocimiento.get(palabra));
			}
		}

		// Comprueba que las probabilidades a priori han sido a–adidas, sino
		// lanza una excepcion.
		if (this.getProbHAM() == 0 || this.getProbSPAM() == 0) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 
	 * Clasifica un email indicando si este es SPAM o HAM.
	 * 
	 * @param email
	 *            - Map con las palabras que aparecen en un email que se quiere
	 *            clasificar.
	 * @return True si el correo es clasificado como SPAM, false en caso
	 *         contrario.
	 */
	public boolean clasifica(Map<String, Integer> email) {
		Iterator<String> it = email.keySet().iterator();
		Float logProbHam = new Float(Math.log10(this.getProbHAM()));
		Float logProbSpam = new Float(Math.log10(this.getProbSPAM()));
		Float sumProbCondHam = 0f;
		Float sumProbCondSpam = 0f;

		Map<String, Float> probSpamNorm = this.getProbSpamNormalizadas();

		while (it.hasNext()) {
			String palabra = (String) it.next();
			if (probSpamNorm.containsKey(palabra)) {
				sumProbCondHam += new Float(Math.log10((1 - probSpamNorm.get(
						palabra).doubleValue())
						* email.get(palabra)));
				sumProbCondSpam += new Float(Math.log10(probSpamNorm.get(
						palabra).doubleValue()
						* email.get(palabra)));
			}
		}

		logProbHam += sumProbCondHam;
		logProbSpam += sumProbCondSpam;

		if (logProbHam > logProbSpam) {
			return false;
		} else {
			return true;
		}
	}
}
