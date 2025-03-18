package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testEmployeeConstructorAndGetters() {
        Employee employee = new Employee("John", "Doe", "Software Engineer", "Developer", 5, "john.doe@example.com");

        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("Software Engineer", employee.getJobTitle());
        assertEquals("Developer", employee.getDescription());
        assertEquals(5, employee.getJobYears());
        assertEquals("john.doe@example.com", employee.getEmail());
    }

    @Test
    void testSetters() {
        Employee employee = new Employee("Jane", "Smith", "Manager", "Team Lead", 10, "jane.smith@example.com");

        employee.setFirstName("Alice");
        employee.setLastName("Johnson");
        employee.setJobTitle("Product Manager");
        employee.setDescription("Leads product development");
        employee.setJobYears(8);
        employee.setEmail("alice.johnson@example.com");

        assertEquals("Alice", employee.getFirstName());
        assertEquals("Johnson", employee.getLastName());
        assertEquals("Product Manager", employee.getJobTitle());
        assertEquals("Leads product development", employee.getDescription());
        assertEquals(8, employee.getJobYears());
        assertEquals("alice.johnson@example.com", employee.getEmail());
    }

    @Test
    void testEqualsAndHashCode() {
        Employee emp1 = new Employee("John", "Doe", "Developer", "Software Engineer", 3, "john.doe@example.com");
        Employee emp2 = new Employee("John", "Doe", "Developer", "Software Engineer", 3, "john.doe@example.com");
        Employee emp3 = new Employee("Jane", "Doe", "Manager", "HR Specialist", 5, "jane.doe@example.com");

        assertEquals(emp1, emp2);
        assertEquals(emp1.hashCode(), emp2.hashCode());
        assertNotEquals(emp1, emp3);
    }

    @Test
    void testToString() {
        Employee employee = new Employee("Emily", "Clark", "HR Specialist", "Employee Relations", 7, "emily.clark@example.com");
        String expected = "Employee{id=null, firstName='Emily', lastName='Clark', jobTitle='HR Specialist', description='Employee Relations', jobYears=7, email='emily.clark@example.com'}";

        assertEquals(expected, employee.toString());
    }

    @Test
    void testInvalidFirstName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Employee("", "Doe", "Developer", "Software Engineer", 3, "john.doe@example.com")
        );
        assertEquals("First name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidLastName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Employee("John", "", "Developer", "Software Engineer", 3, "john.doe@example.com")
        );
        assertEquals("Last name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidJobTitle() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Employee("John", "Doe", "", "Software Engineer", 3, "john.doe@example.com")
        );
        assertEquals("Job title cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Employee("John", "Doe", "Developer", "", 3, "john.doe@example.com")
        );
        assertEquals("Description cannot be null or empty", exception.getMessage());
    }

    @Test
    void testInvalidJobYears() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Employee("John", "Doe", "Developer", "Software Engineer", -1, "john.doe@example.com")
        );
        assertEquals("Job years must be a non-negative integer", exception.getMessage());
    }

    @Test
    void testInvalidEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Employee("John", "Doe", "Developer", "Software Engineer", 3, "invalid-email")
        );
        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    void testNullEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Employee("John", "Doe", "Developer", "Software Engineer", 3, null)
        );
        assertEquals("Email cannot be null or empty", exception.getMessage());
    }
}
