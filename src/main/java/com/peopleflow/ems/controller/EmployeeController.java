package com.peopleflow.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peopleflow.ems.model.Employee;
import com.peopleflow.ems.service.EmployeeService;
import com.peopleflow.ems.state.EmployeeEvent;
import com.peopleflow.ems.util.Utils;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	Utils utils;

	@GetMapping("/employees")
	public ResponseEntity<?> getAllEmployees() {
		try {
			return new ResponseEntity<>(employeeService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			return utils.returnError(e);
		}
	}
	
	@PostMapping("/employees")
	ResponseEntity<?> createNewEmployee(@RequestBody Employee employee) {
		try {
			return new ResponseEntity<>(employeeService.createNewEmployee(employee), HttpStatus.CREATED);
		} catch (Exception e) {
			return utils.returnError(e);
		}
	}

	@PutMapping("/employees/check/{empId}")
	ResponseEntity<?> updateEmployeeStateToChacked(@PathVariable Integer empId) {
		try {
			employeeService.updateEmployeeState(empId, EmployeeEvent.checked);
			return new ResponseEntity<>(employeeService.getEmployeeById(empId), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return utils.returnError(e);
		}
	}
	
	@PutMapping("/employees/approve/{empId}")
	ResponseEntity<?> updateEmployeeStateToApproved(@PathVariable Integer empId) {
		try {
			employeeService.updateEmployeeState(empId, EmployeeEvent.approved);
			return new ResponseEntity<>(employeeService.getEmployeeById(empId), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return utils.returnError(e);
		}
	}
	
	@PutMapping("/employees/activate/{empId}")
	ResponseEntity<?> updateEmployeeStateToActivated(@PathVariable Integer empId) {
		try {
			employeeService.updateEmployeeState(empId, EmployeeEvent.activated);
			return new ResponseEntity<>(employeeService.getEmployeeById(empId), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return utils.returnError(e);
		}
	}

}
