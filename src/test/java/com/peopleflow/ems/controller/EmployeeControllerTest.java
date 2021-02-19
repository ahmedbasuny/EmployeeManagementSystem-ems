package com.peopleflow.ems.controller;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.peopleflow.ems.model.Employee;
import com.peopleflow.ems.service.EmployeeService;
import com.peopleflow.ems.state.EmployeeEvent;
import com.peopleflow.ems.state.EmployeeState;
import com.peopleflow.ems.util.Utils;



@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {

	@MockBean
	EmployeeService employeeServie;
	
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
    private Utils utils;
	
	@Autowired
	ObjectMapper objectMapper;

	@Test
	void testGetAll() throws Exception {
		Employee mockEmployee1 = new Employee(1, "Ahmed", "Basuny", "2 years contract", 28, EmployeeState.ADDED);
		Employee mockEmployee2 = new Employee(2, "Ali", "Mousa", "2 years contract", 28, EmployeeState.ADDED);
		Employee mockEmployee3 = new Employee(3, "Mohamed", "Mostafa", "2 years contract", 28, EmployeeState.ADDED);
		List<Employee> empList = new ArrayList<>();
		empList.add(mockEmployee1);
		empList.add(mockEmployee2);
		empList.add(mockEmployee3);
		Mockito.when(this.employeeServie.getAll()).thenReturn(empList);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees").accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(200))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(3)));
	}

	@Test
	void testCreateNewEmployee() throws Exception {
		Employee newEmployee = new Employee("Ahmed", "Basuny", "2 years contract", 28);
		Employee mockEmployee = new Employee(1, "Ahmed", "Basuny", "2 years contract", 28, EmployeeState.ADDED);
		Mockito.when(this.employeeServie.createNewEmployee(newEmployee)).thenReturn(mockEmployee);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees").accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(newEmployee)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void testUpdateEmployeeStateToChacked() throws Exception {
		Employee mockEmployee = new Employee(1, "Ahmed", "Basuny", "2 years contract", 28, EmployeeState.INCHECK);
		Mockito.when(this.employeeServie.updateEmployeeState(1, EmployeeEvent.checked)).thenReturn(null);
		Mockito.when(this.employeeServie.getEmployeeById(1)).thenReturn(mockEmployee);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/check/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isAccepted())
		.andExpect(MockMvcResultMatchers.jsonPath("$.state").value(EmployeeState.INCHECK.toString()));
	}

	@Test
	void testUpdateEmployeeStateToApproved() throws Exception {
		Employee mockEmployee = new Employee(1, "Ahmed", "Basuny", "2 years contract", 28, EmployeeState.APPROVED);
		Mockito.when(this.employeeServie.updateEmployeeState(1, EmployeeEvent.approved)).thenReturn(null);
		Mockito.when(this.employeeServie.getEmployeeById(1)).thenReturn(mockEmployee);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/approve/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isAccepted())
		.andExpect(MockMvcResultMatchers.jsonPath("$.state").value(EmployeeState.APPROVED.toString()));
	}

	@Test
	void testUpdateEmployeeStateToActivated() throws Exception {
		Employee mockEmployee = new Employee(1, "Ahmed", "Basuny", "2 years contract", 28, EmployeeState.ACTIVE);
		Mockito.when(this.employeeServie.updateEmployeeState(1, EmployeeEvent.checked)).thenReturn(null);
		Mockito.when(this.employeeServie.getEmployeeById(1)).thenReturn(mockEmployee);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/activate/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isAccepted())
		.andExpect(MockMvcResultMatchers.jsonPath("$.state").value(EmployeeState.ACTIVE.toString()));
	}
}
