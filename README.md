# CA1: Version Control with Git: Technical Report

**Author:** Ricardo Marques

**Date:** 07/03/2024

**Discipline:** DevOps

**Program:** SWitCH DEV

**Institution:** Instituto Superior de Engenharia/ Instituto Politécnico do Porto

## Table of Contents

- [Introduction](#introduction)
- [Environment Setup](#environment-setup)
- [Part 1: Development Without Branches](#part-1-development-without-branches)
    - [Goals and Requirements](#goals-and-requirements)
    - [Key Developments](#key-developments)

## Introduction
This report details the **Version Control with Git** assignment for the DevOps course. 

**Part 1** is just using basic version control without branches.

## Environment Setup
Initially, I cloned an existing repository containing the Tutorial React.js and Spring Data REST application to have a local copy of the tutorial project. Following this, I set up my own repository to host the class assignments and ensure that all developments were tracked under my version control.

**Creating My Repository:** I created a new folder on my local machine for the DevOps class assignments and initialized it as a Git repository. This was the first step in establishing my workspace for the project.
```shell
mkdir ~/devops-24-25-1241923
cd ~/devops-24-25-1241923
git init
```
**Copying the Tutorial Application:** To integrate the tutorial application into my project, its contents were copied into my repository. This action ensured that all essential files for the assignment were available within my version control system.
```shell
cp -r ~/tutorial ~/devops-24-25-1241923
```
**Linking to GitHub:** With the tutorial application copied into my repository, I then linked my local repository to a new GitHub repository. This connection allowed me to push my changes to a remote server, facilitating backup and sharing.
```shell
git remote add origin <repository-URL>
```
**First Commit:** After setting up the repository and ensuring all files were in place, I added the basic project to the repository. This initial change was committed with the message "Add basic project to repository", marking the commencement of my work on the assignments.
```shell
mkdir CA1
cp -r ~/devops-24-25-1241923/CA1
git add .
git commit -m "Add basic project to repository"
```
Reflecting on the process, I now recognize the importance of a clear initial commit message, such as "initial commit". This practice sets a clear starting point for the repository's history and is considered a good standard in version control workflows.

**Pushing to Remote:** Finally, I pushed my initial commit to the GitHub repository, officially starting the version history of my assignments in a remote location.
```shell
git push -u origin master
```
This process ensured that I had a clean, organized start to the class assignments, with a clear link to the foundational tutorial application while maintaining my repository for all subsequent developments.

## Part 1: Development Without Branches

### Goals and Requirements
-   The initial part of the assignment focuses on understanding and utilizing basic version control operations without branching.
-   Tasks include setting up the project environment, making changes directly to the master branch, and committing those changes.
-   A key requirement is to introduce a new feature (e.g., adding a `jobYears` field to an Employee object) and ensuring proper version tagging, starting with an initial version and updating it after adding the new feature.
-   The emphasis is on practicing commits, understanding the commit history, and using tags for versioning.

### Key Developments
In the first part, all development was done in the master branch. The steps included:

1. **Tagging the repository to mark the version of the application.**

Following the versioning pattern outlined in the assignment, major.minor.revision, I tagged the initial setup as `v1.1.0` and subsequently pushed this tag to the remote repository:
```shell
git tag -a v1.1.0 -m "v1.1.0"
git push origin v1.1.0
```
2. **Develop a new feature to add a new field to the application.**

The core task of this first part was to develop a new feature by adding a `jobYears` field to the application, which records the number of years an employee has been with the company. Additionally, I implemented unit tests to ensure the creation of Employees and the validation of their attributes were functioning correctly, especially to enforce that only integer values were allowed for the `jobYears` field and not null and not empty values were allowed for String-type fields.
The following files were modified to incorporate this new feature:
- **Employee.java**: This Java class, representing the employee model, was updated to include a new integer field named `jobYears`. The modification involved adding the field itself along with its getter and setter methods to allow for data encapsulation and access and the validation of all parameters. Below are the key additions and modifications made to the `Employee` class to support the new functionality and ensure robust data validation:

```java
@Entity
public class Employee {

	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private String description;

	@NotNull(message = "Job years cannot be null")
	@Min(value = 0, message = "Job years must be a non-negative integer")
	private Integer jobYears;

	protected Employee() {}

	public Employee(String firstName, String lastName, String description, Integer jobYears) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.jobYears = jobYears;
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
				Objects.equals(jobYears, employee.jobYears);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, description, jobYears);
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
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getJobYears() {
		return jobYears;
	}

	public void setJobYears(Integer jobYears) {
		this.jobYears = jobYears;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", description='" + description + '\'' +
				", jobYears=" + jobYears +
				'}';
	}
}
```
The project includes unit tests for the `Employee` class to ensure it functions as intended. The tests cover the following aspects:

- **Constructor and Getters:**  
  Verifies that creating an `Employee` with valid input correctly initializes all attributes, and that the getter methods return the expected values.

- **Setters:**  
  Confirms that the setter methods update the properties of an `Employee` instance correctly.

- **Equality and Hashing:**  
  Ensures that the `equals` method correctly determines equality between two `Employee` objects with the same data and that the `hashCode` method produces matching hash codes for equal objects.

- **String Representation:**  
  Checks that the `toString` method returns a string that accurately reflects the state of an `Employee` object.

To run the tests, execute:

```bash
./mvnw test
```


**DatabaseLoader.java**: This class, responsible for pre-loading the database with sample data, was altered to include `jobYears` information for the sample employees. This change ensured that the application could demonstrate the functionality of the new field right from the start. Below is the code snippet illustrating the modification made to `DatabaseLoader` to include `jobYears` for the sample employees:
```java
@Override
	public void run(String... strings) throws Exception { // <4>
        this.repository.save(new Employee("Frodo", "Baggins", "Ring Bearer", 5));
        this.repository.save(new Employee("Samwise", "Gamgee", "Loyal Companion", 3));
	}
```

**app.js**: The React components within `app.js` were modified to support the display of the new `jobYears` field within the employee list. The `EmployeeList` and `Employee` components now include a column for "Job Years" in the rendered table, allowing users to view the number of years an employee has been with the company alongside their other details.
The following code snippet illustrates the changes made to `app.js` to incorporate the `jobYears` field into the application's frontend:
```javascript
class EmployeeList extends React.Component{
    render() {
        const employees = this.props.employees.map(employee =>
            <Employee key={employee._links.self.href} employee={employee}/>
        );
        return (
            <table>
                <tbody>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Description</th>
                        <th>Job Title</th>
                        <th>Job Years</th>
                    </tr>
                    {employees}
                </tbody>
            </table>
        )
    }
}
```
```javascript
class Employee extends React.Component{
    render() {
        return (
            <tr>
                <td>{this.props.employee.firstName}</td>
                <td>{this.props.employee.lastName}</td>
                <td>{this.props.employee.description}</td>
                <td>{this.props.employee.jobTitle}</td>
                <td>{this.props.employee.jobYears}</td>
            </tr>
        )
    }
}
```
5. **Debug the server and client parts of the solution.**

After verifying the `jobYears` field's integration, I ran the application using `./mvnw spring-boot:run` to test its real-time functionality at `http://localhost:8080/`. This step was crucial for hands-on testing of the feature within the application's interface, ensuring its seamless operation and compatibility with existing functionalities. Concurrently, I conducted a thorough code review to check data handling on the server side and the accurate representation of `jobYears` on the client side, guaranteeing the feature's correctness and maintaining high code quality. I also set on the application.properties `logging.level.root=DEBUG`to enable detailed logging for all components of the application, including Spring Boot, libraries, and custom code and help troubleshoot issues by showing more verbose output than INFO or WARN levels.

6. **End of the assignment**

After ensuring the stability and performance of the new feature, I committed the changes to the repository with a clear and descriptive message detailing the enhancements. Then, I pushed the updated code to the remote server, enabling seamless collaboration with the team and maintaining the project's workflow. To highlight this significant update, I tagged the commit as `v1.2.0`, adhering to the project's semantic versioning standard. Finally, at the conclusion of the assignment, I marked the repository with the tag `ca1-part1`.
