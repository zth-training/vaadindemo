package hu.zerotohero.dao;

import hu.zerotohero.entities.Employee;
import java.util.List;

/**
 * @author Adam Saghy <adam.saghy@webvalto.hu>
 * @author Fekete György <gyorgy.fekete@webvalto.hu>
 */
public interface EmployeeDaoInterface {
	Employee findEmployeeById(Integer id);

	Employee findEmployeeWithJobById(Integer id);

	Employee findManagerByEmployee(Employee employee);

	List<Employee> findAllEmployees();
}
