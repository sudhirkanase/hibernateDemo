package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ChapterId implements Serializable {
	
	private String isbn;	
	
	@Column(name="chapter_number")
	private Integer chapterNr;	
	
	public ChapterId() {}
	public ChapterId(String isbn, Integer chapterNr) {
		this.isbn = isbn;
		this.chapterNr = chapterNr;
	}

	@Override
	public int hashCode() {
		int result = isbn.hashCode();
		result = 31 * result + chapterNr.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;		
		ChapterId chapterId = (ChapterId) o;		
		if (!chapterNr.equals(chapterId.chapterNr)) return false;
		if (!isbn.equals(chapterId.isbn)) return false;		
		return true;
	}
	
}
