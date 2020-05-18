package org.insa.graphs.algorithm.utils; 
 
import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertTrue; 
 
import java.io.IOException; 
import java.util.List; 
 
import org.insa.graphs.model.Graph; 
import org.insa.graphs.model.Path; 
 
import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm; 
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm; 
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm; 
import org.insa.graphs.algorithm.ArcInspectorFactory; 
import org.insa.graphs.algorithm.AbstractSolution; 
import org.insa.graphs.algorithm.ArcInspector; 
import org.insa.graphs.algorithm.shortestpath.ShortestPathData; 
 
import org.insa.graphs.model.io.BinaryGraphReader; 
import java.io.DataInputStream; 
import java.io.FileInputStream; 
 
import org.junit.BeforeClass; 
import org.junit.Test; 
 
public class ShortestPathTest { 
	// On se sert de BellmanFord comme référence étant donné qu'il était déjà codé 
 
	// Some paths... 
	private static Path shortPathD, shortPathB, shortPathA, shortPathD2, shortPathB2, shortPathA2; 
	private static AbstractSolution.Status emptyPathD, emptyPathA; 
	private static AbstractSolution.Status nonexistentPathD, nonexistentPathA; 
 
	@BeforeClass 
	public static void initAll() throws IOException { 
		// Test sur la carte carrée 
		// Récupération des données de la carte carrée 
		FileInputStream input1 = new FileInputStream("C:/Users/User1/Desktop/maps_pour_begraphe/guyane.mapgr"); 
		// Contient des chemins inexistants 
		FileInputStream input2 = new FileInputStream("C:/Users/User1/Desktop/maps_pour_begraphe/guyane.mapgr"); 
		DataInputStream dataInput1 = new DataInputStream(input1); 
		BinaryGraphReader binary1 = new BinaryGraphReader(dataInput1); 
		Graph graph1 = binary1.read(); 
		binary1.close(); 
		DataInputStream dataInput2 = new DataInputStream(input2); 
		BinaryGraphReader binary2 = new BinaryGraphReader(dataInput2); 
		Graph graph2 = binary2.read(); 
		binary2.close(); 
		
		// Création des chemins recherchés
		List<ArcInspector> Listeinspector = ArcInspectorFactory.getAllFilters(); 
		ShortestPathData data = new ShortestPathData(graph1, graph1.getNodes().get(683), graph1.getNodes().get(10758), 
				Listeinspector.get(0)); 
		ShortestPathData data2 = new ShortestPathData(graph1, graph1.getNodes().get(10633), graph1.getNodes().get(8504), 
				Listeinspector.get(0)); 
		ShortestPathData data3 = new ShortestPathData(graph2, graph2.get(1), graph2.get(1), 
				Listeinspector.get(0)); 
		ShortestPathData data4 = new ShortestPathData(graph2, graph2.getNodes().get(14717), graph2.getNodes().get(1126), 
				Listeinspector.get(0)); 
 
		// Réalisation des algorithmes sur data 
		DijkstraAlgorithm D1 = new DijkstraAlgorithm(data); 
		shortPathD = D1.run().getPath(); 
		BellmanFordAlgorithm B1 = new BellmanFordAlgorithm(data); 
		shortPathB = B1.run().getPath(); 
		AStarAlgorithm A1 = new AStarAlgorithm(data); 
		shortPathA = A1.run().getPath(); 
		
		// Lancement des algorithmes sur data2
		DijkstraAlgorithm D2 = new DijkstraAlgorithm(data2); 
		shortPathD2 = D2.run().getPath(); 
		BellmanFordAlgorithm B2 = new BellmanFordAlgorithm(data2); 
		shortPathB2 = B2.run().getPath(); 
		AStarAlgorithm A2 = new AStarAlgorithm(data2); 
		shortPathA2 = A2.run().getPath(); 
		
		// Lancement des algorithmes sur data3
		DijkstraAlgorithm D3 = new DijkstraAlgorithm(data3); 
		emptyPathD = D3.run().getStatus(); 
		AStarAlgorithm A3 = new AStarAlgorithm(data3); 
		emptyPathA = A3.run().getStatus(); 
		
		// Lancement des algorithmes sur data4
		DijkstraAlgorithm D4 = new DijkstraAlgorithm(data4); 
		nonexistentPathD = D4.run().getStatus(); 
		AStarAlgorithm A4 = new AStarAlgorithm(data4); 
		nonexistentPathA = A4.run().getStatus();
 

 
	} 
 
	// Chemin court existant -> vérification de la longueure avec Bellman ford en référence 
	// référence, test sur les deux cartes 
	@Test 
	public void Test1() { 
		assertEquals((long) (shortPathA.getLength()), (long) (shortPathB.getLength())); 
		assertEquals((long) (shortPathB.getLength()), (long) (shortPathD.getLength())); 
		assertEquals((long) (shortPathA2.getLength()), (long) (shortPathB2.getLength())); 
		assertEquals((long) (shortPathB2.getLength()), (long) (shortPathD2.getLength())); 
 
	} 
 
	// Chemin court existant -> vérification du temps avec Bellman ford en référence 
	@Test 
	public void Test2() { 
		assertEquals((long) (shortPathA.getMinimumTravelTime()), (long) (shortPathB.getMinimumTravelTime())); 
		assertEquals((long) (shortPathB.getMinimumTravelTime()), (long) (shortPathD.getMinimumTravelTime())); 
		assertEquals((long) (shortPathA2.getMinimumTravelTime()), (long) (shortPathB2.getMinimumTravelTime())); 
		assertEquals((long) (shortPathB2.getMinimumTravelTime()), (long) (shortPathD2.getMinimumTravelTime())); 
 
	} 
 
	// Chemin de longueur nulle, on vérifie le status 
	@Test 
	public void Test3() { 
		assertTrue(emptyPathA.equals(AbstractSolution.Status.INFEASIBLE)); 
		assertTrue(emptyPathD.equals(AbstractSolution.Status.INFEASIBLE)); 
	} 
 
	// Chemin inexistant, vérification du status 
	@Test 
	public void Test4() { 
		assertTrue(nonexistentPathD.equals(AbstractSolution.Status.INFEASIBLE)); 
		assertTrue(nonexistentPathA.equals(AbstractSolution.Status.INFEASIBLE)); 
	} 
 
} 
