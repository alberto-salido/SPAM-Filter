package ficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import clasificador.ClasificadorNaiveBayes;

/**
 * 
 * @author Alberto Salido Lopez.
 * @author Jose Vicente Lara Cuadrado.
 * 
 *         Clase donde se realizan las distintas operaciones sobre ficheros
 */
public class OperacionesFicheros {

	/**
	 * Devuelve el numero de ficheros existente dentro de un directorio dado,
	 * incluyendo ficheros en subcarpetas
	 * 
	 * @param directorio
	 *            directorio del que se quieren contar el nœmero de ficheros
	 * 
	 * @return int con el nœmero de ficheros
	 */
	public static int numFicheros(String directorio) {
		int res = 0;
		File directory = new File(directorio);
		String[] ficheros = directory.list();
		for (int i = 0; i < ficheros.length; i++) {
			String fichero = ficheros[i];
			if (fichero.startsWith(".")) {
				continue;
			}
			if (fichero.contains(".txt")) {
				if (fichero.contains(".ham") || fichero.contains(".spam")) {
					res = res + 1;
				}
			} else {
				res = res + numFicheros(directorio + "/" + ficheros[i]);
			}

		}
		return res;
	}

	/**
	 * dado un fichero devuelve todas las palabras existentes y el nœmero de
	 * veces que aparecen
	 * 
	 * @param directorio
	 *            donde se encuentra el archivo del que queremos obtener sus
	 *            palabras
	 * @return map<String,Integer> donde el String es cada palabra y Integer el
	 *         nœmero de veces que aparece
	 * @throws IOException
	 */
	public static Map<String, Integer> ficheroaMap(String directorio)
			throws IOException {

		Map<String, Integer> res = new HashMap<String, Integer>();
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {

			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File(directorio);

			if (!archivo.exists()) {
				new Exception("no se ha abierto el archivo correctamente");
			}
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;

			while ((linea = br.readLine()) != null) {
				StringTokenizer tokens = new StringTokenizer(linea, "/ ");
				while (tokens.hasMoreTokens()) {
					// aqui obtengo la palabra
					String s = tokens.nextToken();
					// Prueba 2:
					// if ((s.length() > 1)) {
					// Prueba 3:
					// if ((s.length() > 1) && !esNumero(s)) {
					// Prueba 4
					// if ((s.length() > 2) && !esNumero(s)) {
					if ((s.length() > 1) && !esNumero(s)) {
						if (res.containsKey(s)) {
							res.put(s, res.get(s) + 1);
						} else {
							res.put(s, 1);
						}
					}
				}

			}
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return res;
	}

	/**
	 * lee todos archivos dentro del directorio indicado y devuelve un map con
	 * todas las palabras de todos los archivos junto con el nœmero de veces que
	 * aparece cada una
	 * 
	 * @param directory
	 *            es la ruta del directorio del cual queremos leer los archivos.
	 * @return map<String Integer> con todas las palabras y su numero de
	 *         ocurrencias.
	 * @throws IOException
	 */
	public static Map<String, Integer> directorioAMap(String directory)
			throws IOException {

		Map<String, Integer> res = new HashMap<String, Integer>();
		Map<String, Integer> aux = new HashMap<String, Integer>();

		File directorio = new File(directory);
		String[] ficheros = directorio.list();
		for (int i = 0; i < ficheros.length; i++) {
			// Ignora ficheros ocultos en UNIX.
			if (ficheros[i].startsWith("."))
				continue;
			String extension = ficheros[i].substring(ficheros[i].length() - 3);

			if (extension.equals("txt")) {
				String s = directory + "/" + ficheros[i];

				aux = ficheroaMap(s);
				Iterator<String> itaux = aux.keySet().iterator();
				while (itaux.hasNext()) {
					String palabra = (String) itaux.next();
					if (res.containsKey(palabra)) {
						res.put(palabra, res.get(palabra) + aux.get(palabra));
					} else {
						res.put(palabra, aux.get(palabra));
					}
				}

				// si es un directorio
			} else {
				aux = directorioAMap(directory + "/" + ficheros[i]);
				Iterator<String> itaux = aux.keySet().iterator();
				while (itaux.hasNext()) {
					String palabra = (String) itaux.next();
					if (res.containsKey(palabra)) {
						res.put(palabra, res.get(palabra) + aux.get(palabra));
					} else {
						res.put(palabra, aux.get(palabra));
					}
				}
			}
		}
		return res;
	}

	/**
	 * funcion que devuelve si un String contiene un nœmero o no.
	 * 
	 * @param palabra
	 *            queremos saber si lo que hay dentro es un String o un nœmero
	 *            (Integer o Double)
	 * @return true si es un nœmero y false se no lo es.
	 */
	private static boolean esNumero(String palabra) {
		try {
			int tam = palabra.length();
			if (tam < 10) {
				Integer.parseInt(palabra);
				return true;
			} else {
				Double.parseDouble(palabra);
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Dado un map con las probabilidades de todas las palabras, escribe Žstas
	 * en un fichero.
	 * 
	 * @param directorio
	 *            ruta del fichero donde se quiere guardar la informaci—n
	 * @param mp
	 *            map donde se encuentran las probabilidades de cada palabra
	 *            guardadas
	 * @param probSpam
	 *            probabilidad a priori de spam.
	 * @throws IOException
	 */
	public static void escribeProbabilidadesEnFichero(String directorio,
			Map<String, Float> mp, Float probSpam) throws IOException {
		Iterator<String> itpalabras = mp.keySet().iterator();

		BufferedWriter out = new BufferedWriter(new FileWriter(directorio));
		// Itera por palabras
		// Se a–aden al fichero las probabilidades a priori de ham y spam, estos
		// valores tendran la clave (key) prob_spam y prob_ham respectivamente,
		// para que sean identificados cuando el fichero sea cargado.

		out.write("prob_spam>" + probSpam);
		out.newLine();
		out.write("prob_ham>" + (1 - probSpam));
		out.newLine();

		while (itpalabras.hasNext()) {
			Float numeroOcurrencias = new Float(0);
			String palabra = (String) itpalabras.next();
			if (!(mp.get(palabra) == null)) {
				numeroOcurrencias = mp.get(palabra);
			}
			out.write(palabra + ">" + numeroOcurrencias);
			out.newLine();
		}
		out.close();
	}

	/**
	 * dado un fichero donde estan escritas todas las palabras junto con sus
	 * probabilidades, devuelve esta informaci—n en un map
	 * 
	 * @param directorio
	 *            ruta del fichero que queremos leer
	 * @return map con todas las palabras y sus probabilidades
	 * @throws Exception
	 */
	public static Map<String, Float> leeFicheroProbabilidades(String directorio)
			throws Exception {
		Map<String, Float> res = new HashMap<String, Float>();
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {

			// Apertura del fichero y creacion de BufferedReader para poder
			// hacer una lectura comoda (disponer del metodo readLine()).
			archivo = new File(directorio);
			//
			if (!archivo.exists()) {
				throw new Exception();
			}
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;

			while ((linea = br.readLine()) != null) {
				StringTokenizer tokens = new StringTokenizer(linea, ">");
				while (tokens.hasMoreTokens()) {
					// aqui obtengo la palabra
					String palabra = tokens.nextToken();
					String numero = tokens.nextToken();
					Float incidencias = Float.parseFloat(numero);
					res.put(palabra, incidencias);
				}
			}
		} finally {
			// En el finally cerramos el fichero, para asegurarnos
			// que se cierra tanto si todo va bien como si salta
			// una excepcion.
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				throw new Exception();
			}
		}
		return res;
	}

	/**
	 * funci—n que dada la ruta de un directorio donde se encuentran un conjunto
	 * de archivos (emails) va clasificandolos como spam o ham.
	 * 
	 * @param directorio
	 *            ruta donde se encuentra un conjunto de archivos a
	 *            clasificar,puede conterner subcarpetas.
	 * @param cnb
	 *            un clasicador NaiveBayes de emails.
	 * @return array con el nœmero de Ham y Spam clasificado correctamente e
	 *         incorrectamente.
	 * @throws IOException
	 */
	private static int[] clasificaConjuntoFicherosRec(String directorio,
			ClasificadorNaiveBayes cnb) throws IOException {
		int[] res = new int[4];
		int hamHam = 0;
		int hamSpam = 0;
		int spamHam = 0;
		int spamSpam = 0;
		int numHam = 0;
		int numSpam = 0;
		int[] aux2 = new int[4];

		Map<String, Integer> maux = new HashMap<String, Integer>();
		File directory = new File(directorio);
		String[] ficheros = directory.list();

		for (int i = 0; i < ficheros.length; i++) {
			maux = new HashMap<String, Integer>();

			if (ficheros[i].startsWith("."))
				continue;

			if (ficheros[i].contains(".txt")) {

				if (ficheros[i].contains(".ham")) {
					numHam = numHam + 1;
					maux.putAll(ficheroaMap(directorio + "/" + ficheros[i]));
					if (cnb.clasifica(maux)) {
						hamSpam = hamSpam + 1;

					} else {
						hamHam = hamHam + 1;
					}

				} else if (ficheros[i].contains(".spam")) {
					// es Spam
					numSpam = numSpam + 1;
					maux.putAll(ficheroaMap(directorio + "/" + ficheros[i]));
					if (cnb.clasifica(maux)) {
						spamSpam = spamSpam + 1;
					} else {
						spamHam = spamHam + 1;
					}
				}
				// es un directorio
			} else {

				aux2 = clasificaConjuntoFicherosRec(directorio + "/"
						+ ficheros[i], cnb);
				hamHam += aux2[0];
				hamSpam += aux2[1];
				spamHam += aux2[2];
				spamSpam += aux2[3];
			}
		}
		res[0] = hamHam;
		res[1] = hamSpam;
		res[2] = spamHam;
		res[3] = spamSpam;

		return res;
	}

	/**
	 * funci—n que devuelve el porcentaje de emails clasificados correctamente e
	 * incorrectamente dentro de ham y spam.
	 * 
	 * @param directorio
	 *            donde se encuentran los archivos(emails) a clasificar.
	 * @param cnb
	 *            clasificador naiveBayes
	 * @return estad’sticas de clasificaci—n
	 * @throws IOException
	 */
	public static Float[] clasificaConjuntoFicheros(String directorio,
			ClasificadorNaiveBayes cnb) throws IOException {
		int[] auxprob = new int[4];
		auxprob = clasificaConjuntoFicherosRec(directorio, cnb);
		int numSpam, numHam;
		numSpam = auxprob[2] + auxprob[3];
		numHam = auxprob[0] + auxprob[1];
		Float[] res = { 0f, 0f, 0f, 0f, (float) numHam, (float) numSpam };
		if (numHam != 0) {
			res[0] = new Float(((float) auxprob[0] / numHam) * 100);
			res[1] = new Float(((float) auxprob[1] / numHam) * 100);
		}
		if (numSpam != 0) {
			res[2] = new Float(((float) auxprob[2] / numSpam) * 100);
			res[3] = new Float(((float) auxprob[3] / numSpam) * 100);
		}
		return res;
	}
}
