package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.AutorePaper;
import it.polito.tdp.porto.model.Collaborazioni;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Author> getAllAutori(Map<Integer, Author> authorIdMap) {
		final String sql = "SELECT * FROM author";

		List<Author>res = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				int id = rs.getInt("id");
				Author old = authorIdMap.get(id);
				if(old == null) {
					old = new Author(id, rs.getString("lastname"), rs.getString("firstname"));
					authorIdMap.put(id, old);
				}
				res.add(old);
			}
			st.close();
			conn.close();
			
			return res;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<AutorePaper> getCollaborazioni() {
		
		final String sql = "select c1.eprintid as artid, c1.authorid as ida1, c2.authorid as ida2\n" + 
				"from creator as c1, creator as c2\n" + 
				"where c1.eprintid = c2.eprintid and c1.authorid < c2.authorid\n" + 
				"order by artid";
		
		List<Collaborazioni> res;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				
			}
		}
		
		
		
		
		return null;
	}
	//fai lista coppie autore autore con joins su due tab creator ponendo authorid1<authorid2
	
}