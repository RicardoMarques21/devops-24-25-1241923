package com.greglturnquist.payroll;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class EmployeeTest {

    @Test
    void testEmployeeConstructorAndGetters() {
        Employee employee = new Employee("John", "Doe", "Software Engineer", 5);

        assertThat(employee.getFirstName()).isEqualTo("John");
        assertThat(employee.getLastName()).isEqualTo("Doe");
        assertThat(employee.getDescription()).isEqualTo("Software Engineer");
        assertThat(employee.getJobYears()).isEqualTo(5);
    }

    @Test
    void testSetters() {
        Employee employee = new Employee("Jane", "Smith", "Manager", 10);

        employee.setFirstName("Alice");
        employee.setLastName("Johnson");
        employee.setDescription("Product Manager");
        employee.setJobYears(8);

        assertThat(employee.getFirstName()).isEqualTo("Alice");
        assertThat(employee.getLastName()).isEqualTo("Johnson");
        assertThat(employee.getDescription()).isEqualTo("Product Manager");
        assertThat(employee.getJobYears()).isEqualTo(8);
    }

    @Test
    void testEqualsAndHashCode() {
        Employee emp1 = new Employee("John", "Doe", "Developer", 3);
        Employee emp2 = new Employee("John", "Doe", "Developer", 3);
        Employee emp3 = new Employee("Jane", "Doe", "Manager", 5);

        assertThat(emp1).isEqualTo(emp2);
        assertThat(emp1.hashCode()).isEqualTo(emp2.hashCode());
        assertThat(emp1).isNotEqualTo(emp3);
    }

    @Test
    void testToString() {
        Employee employee = new Employee("Emily", "Clark", "HR Specialist", 7);
        String expected = "Employee{id=null, firstName='Emily', lastName='Clark', description='HR Specialist', jobYears=7}";

        assertThat(employee.toString()).isEqualTo(expected);
    }
}
