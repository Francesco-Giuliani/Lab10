package it.polito.tdp.porto.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		int id = 199 ;
		
		Author a =  model.getAutoreById(id);
		
		List<Author> collaboratori = model.findCoautori(a);
		
		System.out.println("Collaboratori:\n"+collaboratori);
		
		System.out.println(model.getReachableNonCoauthors(a));
		
		Author startAut = a, arrAut = model.getReachableNonCoauthors(a).get(0);
		List<Paper> connectingPapers = model.findConnectingPapers(startAut, arrAut);
		System.out.format("Articoli che collegano %s a %s:\n"+connectingPapers, startAut, arrAut);
	}

}
