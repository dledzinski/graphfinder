/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphfinder2.gui;

import graphfinder2.graph.Graph;
import graphfinder2.graph.Node;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Klasa reprezentacji graficznej grafu
 * @author damian
 */
public class GraphBufferedImage {

	private static final int RADIUS = 150;
	private static final int SHIFT = 20;
	private static final int OVAL_RADIUS = 12;
	private final BufferedImage bi;
	private final Graph graph;
	private final boolean doubleRing;
	// mapa pozycji klucz to wezel a wartosc to pozycja
	private final Map<Integer, Integer> nodePosX = new HashMap<Integer, Integer>();
	private final Map<Integer, Integer> nodePosY = new HashMap<Integer, Integer>();

	/**
	 * Konstruktor, twozry obrazek
	 * @param graph graf ktory ma byc narysowany
	 * @param doubleRing true jesli to NDR
	 */
	public GraphBufferedImage(Graph graph, boolean doubleRing) {
		int a = RADIUS * 2 + SHIFT * 2;
		bi = new BufferedImage(a, a, BufferedImage.TYPE_INT_ARGB);

		// zapis grafu i cykli
		this.graph = graph;
		this.doubleRing = doubleRing;

		// tworzenie pozycji
		int nodeCount = graph.getNodes().length;
		for (Node node : graph.getNodes()) {
			if (!doubleRing) {
				nodePosX.put(node.getIndex(), getRingNodePosX(node.getIndex(), nodeCount));
				nodePosY.put(node.getIndex(), getRingNodePosY(node.getIndex(), nodeCount));
			} else {
				nodePosX.put(node.getIndex(), getNDRNodePosX(node.getIndex(), nodeCount));
				nodePosY.put(node.getIndex(), getNDRNodePosY(node.getIndex(), nodeCount));
			}
		}

		// rysowanie
		paintGraph();
	}

	/**
	 * Zwraca obiekt imageicon
	 * @return
	 */
	public ImageIcon getImageIcon() {
		return new ImageIcon(bi);
	}

	/**
	 * Zapisuje obrazek png we wskazanym pliku
	 * @param filePath
	 * @throws IOException
	 */
	public void save(String filePath) throws IOException {
		ImageIO.write(bi, "png", new File(filePath));
	}

	/**
	 * Rysuje graf
	 * @param g
	 */
	private void paintGraph() {
		Graphics g = bi.getGraphics();
		// kolorowanie na bialo
		g.setColor(Color.white);
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());

		// krawędzie
		for (Node node : graph.getNodes()) {
			// przegladanie sasiadow
			for (int neighbour : new HashSet<Integer>(node.getNeighbours())) {
				// rysowanie
				paintEdge(g, node.getIndex(), neighbour);
			}
		}

		// węzły
		for (Node node : graph.getNodes()) {
			paintNode(g, node.getIndex());
		}

		// konczenie
		g.dispose();
	}

	/**
	 * Rysuje wezel
	 * @param g kontekst graficzny
	 * @param nodeIndex wezel
	 */
	public void paintNode(Graphics g, Integer nodeIndex) {
		int x = nodePosX.get(nodeIndex);
		int y = nodePosY.get(nodeIndex);
		FontMetrics fm = g.getFontMetrics();
		String nodeName = nodeIndex.toString();
		int w = fm.stringWidth(nodeName);
		int h = fm.getHeight();

		int or = OVAL_RADIUS;

		// wypełnienie kółek
		g.setColor(Color.cyan);
		g.fillOval(x - or, y - or, or * 2, or * 2);

		// obramowanie kółek
		g.setColor(Color.blue);
		g.drawOval(x - or, y - or, or * 2, or * 2);

		// napis
		g.setColor(Color.black);
		g.drawString(nodeName, x - w / 2, y - h / 2 + fm.getAscent());
	}

	/**
	 * Rysuje krawedz
	 * @param g kontekst graficzny
	 * @param node1Index pierwszy wezel
	 * @param node2Index drugi wezel
	 */
	public void paintEdge(Graphics g, Integer node1Index, Integer node2Index) {
		g.setColor(Color.black);
		g.drawLine(nodePosX.get(node1Index), nodePosY.get(node1Index), nodePosX.get(node2Index), nodePosY.get(node2Index));
	}

	/**
	 * Zwraca polozenie wezla na ringu
	 * @param index numer wezla
	 * @param n ilosc wezlow
	 * @return
	 */
	private static int getRingNodePosX(int index, int n) {
		double alpha = (Math.PI * 2 * index) / n - (Math.PI / 2);
		return ((int) (Math.cos(alpha) * RADIUS)) + RADIUS + SHIFT;
	}

	/**
	 * Zwraca polozenie wezla na ringu
	 * @param index numer wezla
	 * @param n ilosc wezlow
	 * @return
	 */
	private static int getRingNodePosY(int index, int n) {
		double alpha = (Math.PI * 2 * index) / n - (Math.PI / 2);
		return ((int) (Math.sin(alpha) * RADIUS)) + RADIUS + SHIFT;
	}

	/**
	 * Zwraca polozenie wezla na podwojnym ringu
	 * @param index numer wezla
	 * @param n ilosc wezlow
	 * @return
	 */
	private static int getNDRNodePosX(int index, int n) {
		double alpha = (Math.PI * 2 * (index % (n / 2))) / (n / 2) - (Math.PI / 2);
		return ((int) (Math.cos(alpha) * RADIUS * (index < n / 2 ? 1 : 0.66))) + RADIUS + SHIFT;
	}

	/**
	 * Zwraca polozenie wezla na podwojnym ringu
	 * @param index numer wezla
	 * @param n ilosc wezlow
	 * @return
	 */
	private static int getNDRNodePosY(int index, int n) {
		double alpha = (Math.PI * 2 * (index % (n / 2))) / (n / 2) - (Math.PI / 2);
		return ((int) (Math.sin(alpha) * RADIUS * (index < n / 2  ? 1 : 0.66))) + RADIUS + SHIFT;
	}
}
