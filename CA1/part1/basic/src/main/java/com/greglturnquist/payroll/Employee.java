package com.greglturnquist.payroll;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Represents an Employee entity with job details.
 */
@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull(message = "First name cannot be null")
	@Size(min = 1, message = "First name cannot be empty")
	private String firstName;

	@NotNull(message = "Last name cannot be null")
	@Size(min = 1, message = "Last name cannot be empty")
	private String lastName;

	@NotNull(message = "Description cannot be null")
	@Size(min = 1, message = "Description cannot be empty")
	private String description;

	@NotNull(message = "Job years cannot be null")
	@Min(value = 0, message = "Job years must be a non-negative integer")
	private Integer jobYears;

	@NotNull(message = "Email cannot be null")
	@Email(message = "Invalid email format")
	private String email;

	protected Employee() {}

	public Employee(String firstName, String lastName, String description, Integer jobYears, String email) {
		setFirstName(firstName);
		setLastName(lastName);
		setDescription(description);
		setJobYears(jobYears);
		setEmail(email);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (firstName == null || firstName.trim().isEmpty()) {
			throw new IllegalArgumentException("First name cannot be null or empty");
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if (lastName == null || lastName.trim().isEmpty()) {
			throw new IllegalArgumentException("Last name cannot be null or empty");
		}
		this.lastName = lastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null || description.trim().isEmpty()) {
			throw new IllegalArgumentException("Description cannot be null or empty");
		}
		this.description = description;
	}

	public Integer getJobYears() {
		return jobYears;
	}

	public void setJobYears(Integer jobYears) {
		if (jobYears == null || jobYears < 0) {
			throw new IllegalArgumentException("Job years must be a non-negative integer");
		}
		this.jobYears = jobYears;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("Email cannot be null or empty");
		}
		if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new IllegalArgumentException("Invalid email format");
		}
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return Objects.equals(id, employee.id) &&
				Objects.equals(firstName, employee.firstName) &&
				Objects.equals(lastName, employee.lastName) &&
				Objects.equals(description, employee.description) &&
				Objects.equals(jobYears, employee.jobYears) &&
				Objects.equals(email, employee.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, description, jobYears, email);
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", description='" + description + '\'' +
				", jobYears=" + jobYears +
				", email='" + email + '\'' +
				'}';
	}
}
