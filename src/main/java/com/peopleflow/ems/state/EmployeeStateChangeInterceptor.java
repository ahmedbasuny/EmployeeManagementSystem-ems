package com.peopleflow.ems.state;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import com.peopleflow.ems.model.Employee;
import com.peopleflow.ems.service.EmployeeService;

@Component
public class EmployeeStateChangeInterceptor extends StateMachineInterceptorAdapter<EmployeeState, EmployeeEvent>{
	
	@Autowired
	private EmployeeService employeeService;
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeStateChangeInterceptor.class);

	@Override
	public void preStateChange(State<EmployeeState, EmployeeEvent> state, Message<EmployeeEvent> message,
			Transition<EmployeeState, EmployeeEvent> transition, StateMachine<EmployeeState, EmployeeEvent> stateMachine,
			StateMachine<EmployeeState, EmployeeEvent> rootStateMachine) {
		
		Optional.ofNullable(message).ifPresent( msg -> {
			Optional.ofNullable(Integer.class.cast(msg.getHeaders().getOrDefault(EmployeeService.EMPLOYEE_ID_HEADER, 1)))
				.ifPresent( empId -> {
					Employee updatedEmp = employeeService.updateAndPresistState(empId, state.getId());
					logger.info("updated employee with new state ==> " + updatedEmp);
				});
		});
	}
	
}
