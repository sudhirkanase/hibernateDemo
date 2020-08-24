package entity;

import java.util.Set;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
public class Employee {

	private String email;

	@EmbeddedId
	private EmployeeId employeeId;
	
	public Employee() {}
	public Employee(String email, EmployeeId employeeId) {
		this.email = email;
		this.employeeId = employeeId;
	}
	
	@Override
	public String toString() {
		return "Employee [email=" + email + ", employeeId=" + employeeId + "]";
	}	

}
