/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package graphfinder2;

import graphfinder2.typedGraph.Degrees;
import graphfinder2.typedGraph.TypedGraphCreators;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Klasa do robienia podsumowan
 * @author damian
 */
public class SummaryCreator {

	private final File directory;
	// mapa wynikow
	private SortedMap<Integer, String[]> map = null;
	// ilosc elementow
	private int fileNumber = 0;

	/**
	 * tworzy zestawienie
	 * @param directory katalog
	 * @param stage
	 */
	public SummaryCreator(File directory) {
		this.directory = directory;
		if (!directory.isDirectory()) {
			throw new RuntimeException("" + directory + " is not directory");
		}

	}

	/**
	 * Tworzy zestawienie
	 * @param stage stopien grafu lub null jesli wszystkie
	 */
	public void createSumery(Degrees stage) throws FileNotFoundException, IOException {
		File[] files = getFiles(stage);
		fileNumber = files.length;
		map = new TreeMap<Integer, String[]>();

		// petla
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			readFile(file, i);
		}

		// zapis
		String simpleFileName = "SUMMARY";
		if (stage != null) {
			simpleFileName += "_" + stage.name();
		}
		saveSummary(simpleFileName);
	}

	/**
	 * Zapisuje podsumowanie
	 * @param simpleSummaryFileName nazwa pliku bez rozszerzenia
	 */
	private void saveSummary(String simpleSummaryFileName) throws IOException {
		FileWriter fw = new FileWriter(simpleSummaryFileName + ".csv");
		for (Map.Entry<Integer, String[]> entry : map.entrySet()) {
			String line = "" + entry.getKey();
			for (String value : entry.getValue()) {
				line += ";" + (value != null ? value : "");
			}
			line += "\n";
			fw.write(line);
		}
		fw.flush();
		fw.close();
	}

	/**
	 * Czyta plik i uzupelnia mape
	 * @param file plik do przeczytania
	 * @param index numer pliku
	 */
	private void readFile(File file, int index) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		// zapis tytulu
		String simpleFileName = file.getName().split("\\.")[0];
		addToMap(0, simpleFileName, index);

		// omijanie pierwszej linijki
		String line = br.readLine();

		// czytanie pliku
		while ((line = br.readLine()) != null) {
			String[] fields = line.split(";");
			int nodeNumber = Integer.parseInt(fields[0]);
			String value = fields[1];
			// zapis
			addToMap(nodeNumber, value, index);
		}

		br.close();
		fr.close();
	}

	

	/**
	 * Zwraca pliki z danymi
	 * @return
	 */
	private File[] getFiles(final Degrees stage) {
		File[] files = directory.listFiles(new FilenameFilter() {

			public boolean accept(File dir, String name) {
				if (!name.matches(".*\\.csv")) {
					return false;
				}
				// wyluskiwanie prostej nazwy
				String simpleName = name.split("\\.")[0];
				// wyluskiwanie kreatora
				TypedGraphCreators creator = null;
				try {
					creator = TypedGraphCreators.valueOf(simpleName);
				} catch (IllegalArgumentException e) {
					return false;
				}
				// sprawdzanie czy trzeba filtrowac
				if (stage != null && !creator.getStage().equals(stage)) {
					return false;
				}
				return true;
			}
		});

		return files;
	}

	/**
	 * Zapisuje wartosc w mapie
	 * @param nodeNumber ilosc wezlow
	 * @param value wartosc do zapisania
	 * @param index numer pliku
	 */
	private void addToMap(int nodeNumber, String value, int index) {
		String[] values = map.get((Integer) nodeNumber);
		// jesli nie ma to tworzymy
		if (values == null) {
			values = new String[fileNumber];
			map.put((Integer) nodeNumber, values);
		}
		// zapis
		values[index] = value;
	}


	public static void main(String[] args) throws FileNotFoundException, IOException {
		SummaryCreator sc = new SummaryCreator(new File("."));
		sc.createSumery(Degrees.DEGREE4);

	}
}
