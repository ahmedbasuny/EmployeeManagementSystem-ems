package com.peopleflow.ems.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.peopleflow.ems.state.EmployeeState;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description="Employee Details")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ApiModelProperty(notes="first name shouldn't be blank")
	private String firstName;
	
	@ApiModelProperty(notes="last name shouldn't be blank")
	private String lastName;

	@ApiModelProperty(notes="contractInfo description")
	private String contractInfo;
	
	@ApiModelProperty(notes="age description")
	private Integer age;
	
	@ApiModelProperty(notes="state description")
	@Enumerated(EnumType.STRING)
	private EmployeeState state;
	
	public Employee() {}

	public Employee(String firstName, String lastName, String contractInfo, Integer age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.contractInfo = contractInfo;
		this.age = age;
	}

	public Employee(Integer id, String firstName, String lastName, String contractInfo, Integer age, EmployeeState state) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contractInfo = contractInfo;
		this.age = age;
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContractInfo() {
		return contractInfo;
	}

	public void setContractInfo(String contractInfo) {
		this.contractInfo = contractInfo;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public EmployeeState getState() {
		return state;
	}

	public void setState(EmployeeState state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Employee [firstName=" + firstName + ", lastName=" + lastName + ", contractInfo=" + contractInfo
				+ ", age=" + age + ", state=" + state + "]";
	}
	
}
