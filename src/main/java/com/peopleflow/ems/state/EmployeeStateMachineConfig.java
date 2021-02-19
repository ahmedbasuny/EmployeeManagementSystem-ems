package com.peopleflow.ems.state;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@EnableStateMachineFactory
@Configuration
public class EmployeeStateMachineConfig extends StateMachineConfigurerAdapter<EmployeeState, EmployeeEvent> {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeStateMachineConfig.class);
	
	@Override
	public void configure(StateMachineStateConfigurer<EmployeeState, EmployeeEvent> states) throws Exception {
		states.withStates().initial(EmployeeState.ADDED).states(EnumSet.allOf(EmployeeState.class))
				.end(EmployeeState.ACTIVE);
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<EmployeeState, EmployeeEvent> transitions) throws Exception {
		transitions.withExternal().source(EmployeeState.ADDED).target(EmployeeState.INCHECK).event(EmployeeEvent.checked).action(checked())
			.and().withExternal().source(EmployeeState.INCHECK).target(EmployeeState.APPROVED).event(EmployeeEvent.approved).action(approved())
			.and().withExternal().source(EmployeeState.APPROVED).target(EmployeeState.ACTIVE).event(EmployeeEvent.activated).action(activated());
	}

	// we could listen to state change
	@Override
	public void configure(StateMachineConfigurationConfigurer<EmployeeState, EmployeeEvent> config) throws Exception {
		StateMachineListenerAdapter<EmployeeState, EmployeeEvent> adapter = new StateMachineListenerAdapter<EmployeeState, EmployeeEvent>() {
			@Override
			public void stateChanged(State<EmployeeState, EmployeeEvent> from, State<EmployeeState, EmployeeEvent> to) {
				logger.info("State changed from " + from.getId() + " to " + to.getId());
			}
		};
		config.withConfiguration().listener(adapter);
	}
	
	// with actions we could add more business to be happened when state changes.
	public Action<EmployeeState, EmployeeEvent> checked() {
		return context -> {
			logger.info("checked was called !!!");
		};
	}
	
	public Action<EmployeeState, EmployeeEvent> approved() {
		return context -> {
			logger.info("approved was called !!!");
		};
	}
	
	public Action<EmployeeState, EmployeeEvent> activated() {
		return context -> {
			logger.info("activated was called !!!");
		};
	}
}
