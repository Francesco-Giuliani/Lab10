package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private Graph<Author, Collaborazioni> graph;
	private PortoDAO pdao;
	private List<Author> autori;
	private List<Collaborazioni> edges;
	
	private Map<Integer, Author> authorIdMap;
	private Map<String, Collaborazioni> collIdMap;
	private Map<Integer, Paper> paperIdMap;
	
	public Model() {
		this.authorIdMap = new HashMap<Integer, Author>();
		this.collIdMap = new HashMap<>();
		this.paperIdMap = new HashMap<>();
		
		this.pdao = new PortoDAO(this.authorIdMap, this.collIdMap, this.paperIdMap);
	}

	public List<Author> findCoautori(Author author) {
		if(this.graph == null || (this.graph.vertexSet().isEmpty() && this.graph.edgeSet().isEmpty())) 
			this.creaGrafo();
		
		return Graphs.neighborListOf(this.graph, author);		
	}

	private void creaGrafo() {
		this.graph = new SimpleGraph<>(Collaborazioni.class);
		
		this.autori = this.pdao.getAllAutori();
		//System.out.println("\n****** Autori:\n"+ this.autori);
		
		Graphs.addAllVertices(this.graph, autori);
		
		this.edges = this.pdao.getCollaborazioni();
		
		
		for(Collaborazioni c : this.edges) {
			this.graph.addEdge(c.getAut1(), c.getAut2(), c);
		}
		
		//System.out.println("\n****** Edges:\n"+this.edges);	
					
	}
	public Author getAutoreById(int id) {
		return this.pdao.getAutore(id);
	}
	
	public List<Paper> findConnectingPapers(Author startAut, Author arrAut){
		List<Paper> res = new ArrayList<>();
		
		ShortestPathAlgorithm<Author, Collaborazioni> pathAlg = new DijkstraShortestPath<>(this.graph);
		GraphPath<Author, Collaborazioni>  path = pathAlg.getPath(startAut, arrAut);
		
		for(Collaborazioni c: path.getEdgeList()) {
			Paper p = this.pdao.getArticolo(c.getPaperIds().get(0));
			res.add(p);
		}
		return res;
	}
	public List<Author> getReachableNonCoauthors(Author start){
		ConnectivityInspector<Author, Collaborazioni>  ci = new ConnectivityInspector<>(this.graph);
		List<Author> connSet = new ArrayList<>(ci.connectedSetOf(start));
		List<Author> neigAut = Graphs.neighborListOf(graph, start);
		neigAut.add(start);
		connSet.removeAll(neigAut);
		List<Author> res = new ArrayList<>(connSet);
		return res;
	}

}
