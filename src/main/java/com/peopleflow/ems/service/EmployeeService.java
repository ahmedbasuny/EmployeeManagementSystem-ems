package com.peopleflow.ems.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import com.peopleflow.ems.exception.EmployeeDataNotValidException;
import com.peopleflow.ems.exception.EmployeeNotFoundException;
import com.peopleflow.ems.model.Employee;
import com.peopleflow.ems.repository.EmployeeRepository;
import com.peopleflow.ems.state.EmployeeEvent;
import com.peopleflow.ems.state.EmployeeState;
import com.peopleflow.ems.state.EmployeeStateChangeInterceptor;

@Service
public class EmployeeService {

	public static final String EMPLOYEE_ID_HEADER = "employee_id";

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private StateMachineFactory<EmployeeState, EmployeeEvent> stateMachineFactory;

	@Autowired
	private EmployeeStateChangeInterceptor employeeStateChangeInterceptor;

	public List<Employee> getAll() {
		return this.employeeRepo.findAll();
	}
	
	public Employee createNewEmployee(Employee emp) {
		this.validateEmployeeData(emp);
		emp.setState(EmployeeState.ADDED);
		return employeeRepo.save(emp);
	}

	//we can add any custom validation.
	public boolean validateEmployeeData(Employee emp) {
		if (emp.getFirstName().trim().equals("") || emp.getLastName().trim().equals("")) {
			throw new EmployeeDataNotValidException();
		}
		return true;
	}

	public StateMachine<EmployeeState, EmployeeEvent> updateEmployeeState(Integer empId, EmployeeEvent event) {
		Employee emp = getEmployeeById(empId);
		StateMachine<EmployeeState, EmployeeEvent> stateMachine = buildStateMachine(emp);
		sendEvent(empId, stateMachine, event);
		return stateMachine;
	}
	
	public Employee getEmployeeById(Integer empId) {
		Optional<Employee> emp = Optional.ofNullable(employeeRepo.findById(empId)
									.orElseThrow(() -> new EmployeeNotFoundException(empId)));
		return emp.get();
	}
	
	public StateMachine<EmployeeState, EmployeeEvent> buildStateMachine(Employee emp) {
		StateMachine<EmployeeState, EmployeeEvent> stateMachine = stateMachineFactory.getStateMachine(emp.getId().toString());
		stateMachine.stop();
		stateMachine.getStateMachineAccessor().doWithAllRegions(smAccessor -> {
			smAccessor.addStateMachineInterceptor(employeeStateChangeInterceptor);
			smAccessor.resetStateMachine(new DefaultStateMachineContext<>(emp.getState(), null, null, null));
		});
		stateMachine.start();
		return stateMachine;
	}
	
	
	public Employee updateAndPresistState(Integer empId, EmployeeState state) {
		Employee emp = getEmployeeById(empId);
		emp.setState(state);
		return employeeRepo.save(emp);
	}
	
	private void sendEvent(Integer empId, StateMachine<EmployeeState, EmployeeEvent> stateMachine, EmployeeEvent event) {
		Message<EmployeeEvent> msg = MessageBuilder.withPayload(event).setHeader(EMPLOYEE_ID_HEADER, empId).build();
		stateMachine.sendEvent(msg);
	}
}
