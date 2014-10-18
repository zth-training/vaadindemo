package hu.zerotohero.dao;

import com.avaje.ebean.Ebean;
import hu.zerotohero.entities.Employee;

import java.util.List;

/**
 * @author Attila Budai <attila.budai@zerotohero.hu>
 * @author Adam Saghy <adam.saghy@webvalto.hu>
 * @author Fekete György <gyorgy.fekete@webvalto.hu>
 */
public class EmployeeDao implements EmployeeDaoInterface {

	@Override
	public Employee findEmployeeById(Integer id) {
		return Ebean.find(Employee.class).where().eq("id", id).findUnique();
	}

	@Override
	public Employee findEmployeeWithJobById(Integer id) {
		return Ebean.find(Employee.class).fetch("job").where().eq("id", id).findUnique();
	}

	@Override
	public Employee findManagerByEmployee(Employee employee) {
		return Ebean.find(Employee.class).where().eq("employees", employee).findUnique();
	}

	@Override
	public List<Employee> findAllEmployees() {
		return Ebean.find(Employee.class).findList();
	}

}
