package it.polito.tdp.porto.model;

public class AutorePaper {

	private int eprintId;
	private Author author;
	public AutorePaper(int eprintId, Author author) {
		super();
		this.eprintId = eprintId;
		this.author = author;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + eprintId;
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
		AutorePaper other = (AutorePaper) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (eprintId != other.eprintId)
			return false;
		return true;
	}
	public int getEprintId() {
		return eprintId;
	}
	public Author getAuthor() {
		return author;
	}
	
	
}
