package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
public class Section {
	
	@Id
	@Column(name="section_number")
	private Integer sectionNr;	
	
	private String title;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="isbn_fk", referencedColumnName="isbn"),
		@JoinColumn(name="chapter_number_fk", referencedColumnName="chapter_number")
	})
	private Chapter  chapter;

	public Section() {}	
	public Section(Integer sectionNr, String title) {
		this.sectionNr = sectionNr;
		this.title = title;
	}
	
	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

}
