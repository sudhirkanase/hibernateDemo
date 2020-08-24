package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmployeeId implements Serializable {

	private String username;
	
	@Column(name="department_number")
	private String departmentNumber;

	public EmployeeId() {}
	public EmployeeId(String username, String departmentNumber) {
		this.username = username;
		this.departmentNumber = departmentNumber;
	}

	@Override
	public int hashCode() {
		int result = username.hashCode();
		result = 31 * result + departmentNumber.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;		
		EmployeeId employeeId = (EmployeeId) o;		
		if (!departmentNumber.equals(employeeId.departmentNumber)) return false;
		if (!username.equals(employeeId.username)) return false;		
		return true;
	}
	
	@Override
	public String toString() {
		return "EmployeeId [username=" + username + ", departmentNumber="
				+ departmentNumber + "]";
	}

}
