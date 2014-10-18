package hu.zerotohero.services;

import hu.zerotohero.dao.EmployeeDaoInterface;
import hu.zerotohero.entities.Employee;

/**
 * @author Fekete György <gyorgy.fekete@webvalto.hu>
 */
public class EmployeeService {

	private EmployeeDaoInterface employeeDao;

	public EmployeeService(EmployeeDaoInterface employeeDao) {
		this.employeeDao = employeeDao;
	}

	public String getMaxSalaryWithOwner() {
		Long max = Long.MIN_VALUE;
		String maxEmployee = "";

		for (Employee employee : employeeDao.findAllEmployees()) {
			if (max < employee.getSalary()) {
				max = employee.getSalary();
				maxEmployee = employee.getFirstName() + " " + employee.getLastName();
			}
		}

		return maxEmployee + " - " + max.toString() + " $";
	}

	public String getMinSalaryWithOwner() {
		Long min = Long.MAX_VALUE;
		String minEmployee = "";

		for (Employee employee : employeeDao.findAllEmployees()) {
			if (min > employee.getSalary()) {
				min = employee.getSalary();
				minEmployee = employee.getFirstName() + " " + employee.getLastName();
			}
		}

		return minEmployee + " - " + min.toString() + " $";
	}

	public String getAvarageSalary() {
		Long avg = 0L;
		Integer empCount = 0;

		for (Employee employee : employeeDao.findAllEmployees()) {
			avg += employee.getSalary();
			++empCount;
		}

		return (avg / empCount) + " $";
	}
}
