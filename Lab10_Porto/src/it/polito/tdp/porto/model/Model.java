package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private Graph<Author, DefaultEdge> graph;
	private PortoDAO pdao;
	private List<Author> autori;
	private List<Collaborazioni> edges;
	
	private Map<Integer, Author> authorIdMap;
	private Map<String, Collaborazioni> collaborazioniIdMap;
	
	public Model() {
		this.pdao = new PortoDAO();
	}

	public List<Author> findCoautori(Author author) {
		if(this.graph == null || (this.graph.vertexSet().isEmpty() && this.graph.edgeSet().isEmpty())) 
			this.creaGrafo();
		
		return Graphs.neighborListOf(this.graph, author);		
	}

	private void creaGrafo() {
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		
		this.autori = this.pdao.getAllAutori(this.authorIdMap);
		
		Graphs.addAllVertices(this.graph, autori);
		
		this.autoriPaper = this.pdao.getCollaborazioni();
		List<Author> giaCoautori = new ArrayList<>();
		
				
	}
	

}
