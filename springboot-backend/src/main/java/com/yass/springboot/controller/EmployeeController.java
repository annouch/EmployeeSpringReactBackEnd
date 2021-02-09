package com.yass.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yass.springboot.exception.ResourceNotFoundException;
import com.yass.springboot.model.Employee;
import com.yass.springboot.repository.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/employees")
	public List<Employee> getAllEmployee() {
		return employeeRepository.findAll();
		// hello
	}

	// create employee

	@PostMapping("/employees")
	public Employee saveEmoloyee(@RequestBody Employee employee) {

		return employeeRepository.save(employee);
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not Found with the ID :" + id + "for Selection"));
		return ResponseEntity.ok(employee);

	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id ,@RequestBody Employee employee){
	Employee employeeUp = employeeRepository.findById(id).orElseThrow(() 
			-> new ResourceNotFoundException("Employee not Found with the ID :" +id + "for Update"));
			
			employeeUp.setFirstName(employee.getFirstName());
			employeeUp.setLastName(employee.getLastName());
			employeeUp.setEmailId(employee.getEmailId());
			
			Employee employee2 = employeeRepository.save(employeeUp);
			
			return ResponseEntity.ok(employee2);
	}
	
	@DeleteMapping("/employees/{id}")
	public String deleteEmployee(@PathVariable Long id){
		Employee employee = employeeRepository.findById(id).orElseThrow(() 
				-> new ResourceNotFoundException("Employee not Found with the ID :" +id + " for Delete"));
		employeeRepository.delete(employee);
		return "Delete successful of the employee with id = " + id ;
	}	
}
