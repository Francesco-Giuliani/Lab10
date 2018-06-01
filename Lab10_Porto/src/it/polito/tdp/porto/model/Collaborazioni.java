package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;

public class Collaborazioni {
	
	private String id;
	private Author aut1;
	private Author aut2;
	private List<Integer> paperIds;
	
	public Collaborazioni(Author aut1, Author aut2) {
		super();
		this.aut1 = aut1;
		this.aut2 = aut2;
		this.paperIds = new ArrayList<>();
		if(aut1.getId()<=aut2.getId())
			this.id = "A1:"+this.aut1.getId()+"-A2:"+this.aut2.getId();
		else
			this.id = "A1:"+this.aut2.getId()+"-A2:"+this.aut1.getId();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collaborazioni other = (Collaborazioni) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} 
		if(id.contains(((Integer) other.aut1.getId()).toString()) && id.contains(((Integer) other.aut2.getId()).toString()))
			return true;
		
		return false;
	}
	public String getId() {
		return id;
	}
	public Author getAut1() {
		return aut1;
	}
	public Author getAut2() {
		return aut2;
	}
	public List<Integer> getPaperIds() {
		return paperIds;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setAut1(Author aut1) {
		this.aut1 = aut1;
	}
	public void setAut2(Author aut2) {
		this.aut2 = aut2;
	}
	public void setPaperIdss(List<Integer> paperIds) {
		this.paperIds = paperIds;
	}
	public void addPaperId(Integer paperId) {
		if(!this.paperIds.contains(paperId))
			this.paperIds.add(paperId);
	}
	@Override
	public String toString() {
		return "{"+id + " (" + aut1 + ") (" + aut2 + ") (" + paperIds + ")}";
	}
	
}
