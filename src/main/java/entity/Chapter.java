package entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Chapter {
	
	private String title;
	
	@EmbeddedId
	private ChapterId chapterId;
	
	@OneToMany(mappedBy="chapter", cascade={CascadeType.PERSIST})
	private Set<Section> sections = new HashSet<Section>();

	public Chapter() {}	
	public Chapter(String title, ChapterId chapterId) {
		this.title = title;
		this.chapterId = chapterId;
	}
	
	public void addSection(Section section) {
		section.setChapter(this);
		this.sections.add(section);
	}
	
}
