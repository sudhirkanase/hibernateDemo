package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
public class Department {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="username_fk", referencedColumnName="username"),
		@JoinColumn(name="department_number_fk", referencedColumnName="department_number")
	})
	private Employee employee;	
		
	public Department() {	}
	public Department(String name) {
		this.name = name;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
