package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.exceptions.AutoreInesistenteException;
import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.AutorePaper;
import it.polito.tdp.porto.model.Collaborazioni;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {
	
	private Map<Integer, Author> authorIdMap;
	private Map<String, Collaborazioni> collIdMap;
	private Map<Integer, Paper> paperIdMap;

	public PortoDAO(Map<Integer, Author> authorIdMap, Map<String, Collaborazioni> collIdMap,
			Map<Integer, Paper> paperIdMap) {
		this.authorIdMap = authorIdMap;
		this.collIdMap = collIdMap;
		this.paperIdMap = paperIdMap;
	}

	/**
	 * Dato l'id ottengo l'autore.
	 * @param int id autore
	 * @return l'oggetto di tipo Author associato all'id fornito
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
				
				Author old = this.authorIdMap.get(autore.getId());
				if(old == null) {
					old = autore;	
					this.authorIdMap.put(old.getId(), old);
				}
				return old;
			}

			st.close();
			conn.close();
			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/**
	 * Dato l'id ottengo l'articolo.
	 * @param int id articolo
	 * @return l'oggetto di tipo Paper associato all'id fornito
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
				
				Paper old = this.paperIdMap.get(paper.getEprintid());
				if(old == null) {
					old = paper;	
					this.paperIdMap.put(paper.getEprintid(), paper);
				}
				return old;
			}

			st.close();
			conn.close();
			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Author> getAllAutori() {
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

	public List<Collaborazioni> getCollaborazioni() {
		
		final String sql = "select c1.eprintid as artid, c1.authorid as ida1, c2.authorid as ida2\n" + 
				"from creator as c1, creator as c2\n" + 
				"where c1.eprintid = c2.eprintid and c1.authorid < c2.authorid\n";
		
		List<Collaborazioni> res = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Author a1 = this.authorIdMap.get(rs.getInt("ida1")), a2 = this.authorIdMap.get(rs.getInt("ida2"));
				
				if(a1 == null || a2==null)
					throw new AutoreInesistenteException();
				
				int pId = rs.getInt("artid");
				
				Collaborazioni c = new Collaborazioni(a1, a2);
				Collaborazioni old = this.collIdMap.get(c.getId());
				if(old == null) {
					old = c;
					this.collIdMap.put(old.getId(), old);
				}
				if(!old.getPaperIds().contains(pId))
					old.getPaperIds().add(pId);
			
				res.add(old);
			}
				
			st.close();
			conn.close();
			return res;
		}catch(SQLException sqle) {
			sqle.printStackTrace();
			System.out.println("Errore DB");
			return null;
		}catch(AutoreInesistenteException aie) {
			aie.printStackTrace();
			System.out.println("Autore inesistente");
			return null;
		}
	}
	//fai lista coppie autore autore con joins su due tab creator ponendo authorid1<authorid2
	
}