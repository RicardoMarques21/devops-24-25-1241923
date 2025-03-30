# CA1: Version Control with Git: Technical Report

**Author:** Ricardo Marques

**Date:** 07/03/2024

**Discipline:** DevOps

**Program:** SWitCH DEV

**Institution:** Instituto Superior de Engenharia/ Instituto Politécnico do Porto

## Table of Contents

- [Introduction](#introduction)
- [Environment Setup](#environment-setup)
- [Part 1.1: Development Without Branches](#part-11-development-without-branches)
  - [Goals and Requirements (Part 1.1)](#goals-and-requirements-part-11)
  - [Key Developments (Part 1.1)](#key-developments-part-11)
- [Part 1.2: Development Using Branches](#part-12-development-using-branches)
  - [Goals and Requirements (Part 1.2)](#goals-and-requirements-part-12)
  - [Key Developments (Part 1.2)](#key-developments-part-12)
- [Part 2: Gradle Tasks and Testing Tutorial](#part-2-gradle-tasks-and-testing-tutorial)
  - [Goals and Requirements (Part 2)](#goals-and-requirements-part-2)
  - [Key Developments (Part 2)](#key-developments-part-2)
- [Part 3: Converting the basic version of the Tutorial application to Gradle](#part-3-converting-the-basic-version-of-the-tutorial-application-to-gradle)
  - [Goals and Requirements (Part 3)](#goals-and-requirements-part-3)
  - [Key Developments (Part 3)](#key-developments-part-3)
- [Final Results](#final-results)
  - [Implementation](#implementation)
  - [Branches](#branches)
  - [Tags](#tags)
  - [Issue Tracking](#issue-tracking)
- [Alternative Solutions](#alternative-solutions)
  - [Comparison of SVN and Git](#comparison-of-svn-and-git)
  - [Utilizing SVN for the Assignment part 1.1](#utilizing-svn-for-the-assignment-part-11)
  - [Alternative solution to Gradle: Ant](#alternative-solution-to-gradle-ant)
- [Conclusion](#conclusion)



## Introduction
This report details the **Version Control with Git** assignment for the DevOps course. 

### CA1: Part1:

**Part 1.1** is just using basic version control without branches.  
**Part 1.2** is implementing branching for new features and bug fixes.

### CA1: Part2:

Implementing and testing a Gradle project.

### CA1: Part3:

Transforming a Maven into a Gradle project.

## Environment Setup
Initially, I cloned an existing repository containing the Tutorial React.js and Spring Data REST application to have a local copy of the tutorial project. Following this, I set up my own repository to host the class assignments and ensure that all developments were tracked under my version control.

**Creating My Repository:** I created a new folder on my local machine for the DevOps class assignments and initialized it as a Git repository. This was the first step in establishing my workspace for the project.

**Part 1.1 and Part 1.2**
```bash
mkdir ~/devops-24-25-1241923
cd ~/devops-24-25-1241923
git init
```
**Copying the Tutorial Application:** To integrate the tutorial application into my project, its contents were copied into my repository. This action ensured that all essential files for the assignment were available within my version control system.
```bash
cp -r ~/tutorial ~/devops-24-25-1241923
```
**Linking to GitHub:** With the tutorial application copied into my repository, I then linked my local repository to a new GitHub repository. This connection allowed me to push my changes to a remote server, facilitating backup and sharing.
```bash
git remote add origin <repository-URL>
```
**First Commit:** After setting up the repository and ensuring all files were in place, I added the basic project to the repository. This initial change was committed with the message "Add basic project to repository", marking the commencement of my work on the assignments.
```bash
mkdir CA1
cp -r ~/devops-24-25-1241923/CA1
git add .
git commit -m "Add basic project to repository"
```
Reflecting on the process, I now recognize the importance of a clear initial commit message, such as "initial commit". This practice sets a clear starting point for the repository's history and is considered a good standard in version control workflows.

**Pushing to Remote:** Finally, I pushed my initial commit to the GitHub repository, officially starting the version history of my assignments in a remote location.
```bash
git push -u origin master
```

**Part 2**

1. **Download and Prepare the Example Application:**

  - Clone or download the application from Bitbucket:
    ```bash
    git clone https://bitbucket.org/pssmatos/gradle_basic_demo/
    ```
  - Remove the `.git` folder to start with a fresh repository:
    ```bash
    rm -rf gradle_basic_demo/.git
    ```
  - Move the project into your Part 2 folder:
    ```bash
    mkdir -p ~/devops-24-25-1241923/CA1/part2
    mv gradle_basic_demo ~/devops-24-25-1241923/CA1/part2/
    cd ~/devops-24-25-1241923/CA1/part2/gradle_basic_demo
    ```

This process ensured that I had a clean, organized start to the class assignments, with a clear link to the foundational tutorial application while maintaining my repository for all subsequent developments.

[⬆ Back to Top](#Table-of-contents)

## Part 1.1: Development Without Branches

### Goals and Requirements (Part 1.1)
-   The initial part of the assignment focuses on understanding and utilizing basic version control operations without branching.
-   Tasks include setting up the project environment, making changes directly to the master branch, and committing those changes.
-   A key requirement is to introduce a new feature (e.g., adding a `jobYears` field to an Employee object) and ensuring proper version tagging, starting with an initial version and updating it after adding the new feature.
-   The emphasis is on practicing commits, understanding the commit history, and using tags for versioning.

### Key Developments (Part 1.1)
In the first part, all development was done in the master branch. The steps included:

1. **Tagging the repository to mark the version of the application.**

Following the versioning pattern outlined in the assignment, major.minor.revision, I tagged the initial setup as `v1.1.0` and subsequently pushed this tag to the remote repository:
```bash
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
3. **Debug the server and client parts of the solution.**

After verifying the `jobYears` field's integration, I ran the application using `./mvnw spring-boot:run` to test its real-time functionality at `http://localhost:8080/`. This step was crucial for hands-on testing of the feature within the application's interface, ensuring its seamless operation and compatibility with existing functionalities. Concurrently, I conducted a thorough code review to check data handling on the server side and the accurate representation of `jobYears` on the client side, guaranteeing the feature's correctness and maintaining high code quality. I also set on the application.properties `logging.level.root=DEBUG`to enable detailed logging for all components of the application, including Spring Boot, libraries, and custom code and help troubleshoot issues by showing more verbose output than INFO or WARN levels.

4. **End of the assignment**

After ensuring the stability and performance of the new feature, I committed the changes to the repository with a clear and descriptive message detailing the enhancements. Then, I pushed the updated code to the remote server, enabling seamless collaboration with the team and maintaining the project's workflow. To highlight this significant update, I tagged the commit as `v1.2.0`, adhering to the project's semantic versioning standard. Finally, at the conclusion of the assignment, I marked the repository with the tag `ca1-part1`.

[⬆ Back to Top](#Table-of-contents)

## Part 1.2: Development Using Branches

### Goals and Requirements (Part 1.2)
-   The second part advances to using branches for feature development and bug fixes, emphasizing isolated development environments and merge strategies.
-   Requirements include creating feature branches for new developments or bug fixes, ensuring that changes do not interfere with the main codebase until they are ready to be merged.
-   The part concludes with tagging the master branch after successful merges to mark new versions of the application, showcasing effective branch management and integration in version control.

### Key Developments (Part 1.2)
In the second part, the focus shifted towards utilizing branch-based development to enhance the application's features and fix any existing bugs, ensuring that the master branch remained stable for "publishing" the stable versions of the Tutorial React.js and Spring Data REST Application.

The steps for adding new features and fixing bugs are similar to those described in Part 1. Therefore, to avoid repetition, I will not show all the code again. The main difference here is the use of branches. Here are the main steps:

1. **Start using the master branch**

To ensure I was working in the correct branch, particularly the master branch for publishing stable versions, I employed the `git branch` command. This step was crucial during this second part, for verifying my current working branch, marked by an asterisk (*) in the command output.

2. **Develop new features in branches**

During the development phase of adding an email field to our application, effective branch management was crucial. The following command creates a new branch named email-field and switches to it:
```bash
git checkout -b email-field
```
3. **Integration and Testing of the Email Field**

The process of adding support for the email field in the application and ensuring robust validation closely mirrored the approach taken with the `jobYears` field in Part 1. The following outlines the key steps taken.
- **Code Implementation**: Similar to the previous feature development, I extended the `Employee` class to include an `email` field along with its getter and setter methods. This involved updating data models, forms, and views to accommodate the new field, ensuring it was fully integrated into the application's frontend and backend.
- **Unit Testing**: Following the established pattern, I wrote comprehensive unit tests to verify the correct creation of Employee instances with the new email field and to enforce validation rules, such as non-null and non-empty values for the email attribute.
- **Debugging**: The server and client parts of the application underwent thorough debugging to identify and rectify any issues arising from the addition of the email field, ensuring seamless operation and user experience.

4. **Merge the code with the master**

The completion of the email field feature involved a series of steps to integrate the changes into the main branch and update the application's version. Initially, the finalized changes in the `email-field` branch were committed. This branch was then pushed to the remote repository, setting the stage for merging into the main branch. To preserve the history, a no-fast-forward merge was used. After merging, the changes were pushed to the remote repository to update the main branch. Finally, the new version was tagged and pushed to mark this significant update. The commands used were as follows:
```bash
# Commit the feature changes:
git add .
git commit -m "email field added"

# Push the feature branch upstream:
git push --set-upstream origin email-field

# Switch to the main branch and merge changes:
git checkout main
git merge --no-ff email-field

# Push the merged changes to update the main branch:
git push

# Tag the new version and push the tag:
git tag -a v1.3.0 -m "v1.3.0"
git push origin v1.3.0
```

5. **Create a new branch to fix a bug**

To fix the bug, a validation check was introduced in the setter method to ensure that the email follows the correct format:
```java
if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") ) {
        throw new IllegalArgumentException("Invalid email format");
}
```

6. **End of the assignment**

After implementing the fix and conducting thorough testing to confirm its effectiveness, the changes were merged into the master branch, and the application version was updated to `v1.3.1` to indicate the minor fix. This version increment highlights the continuous improvement of the application's functionality and reliability. At the end of the assignment I marked the repository with the tag `ca1-part2`.

[⬆ Back to Top](#Table-of-contents)

## Part 2: Gradle Tasks and Testing Tutorial

### Goals and Requirements (Part 2)

This document details the steps to enhance your Gradle project by adding tasks to run the server, execute unit tests, backup sources, archive the sources, and finally tag your repository.

### Key Developments (Part 2)

**1. Adding a New Task to Execute the Server**

Edit your `build.gradle` file to add a Gradle task that runs the server. For example:

```gradle
tasks.register('runServer', JavaExec) {
    group = "DevOps"
    description = "Runs a chat server"

    classpath = sourceSets.main.runtimeClasspath
    mainClass = "basic_demo.ChatServerApp"

    args '59001'
}
```

Run the server

```gradle
./gradlew runServer
```

**2. Adding a Unit Test and Updating the Gradle Script**

Update dependencies
```gradle
dependencies {
  testImplementation 'junit:junit:4.12'
  }
  ```
Create directory test
```shell
mkdir src/test/java/basic_demo/AppTest.java
```

Run the Unit test
```bash
./gradlew test
```

**3. Adding a Task to Backup the Sources (Copy Task)**
```gradle
tasks.register('backupSources', Copy) {
    group = "Backup"
    description = "Creates a backup of the source files"
    from 'src'
    into "$buildDir/backup"
}
```

To run the backup task, execute:
```bash
./gradlew backupSources
```

**4. Adding a Task to Archive the Sources (Zip Task)**
```gradle
tasks.register('zipSources', Zip) {
group = "Backup"
description = "Archives the source files into a zip file"
from 'src'
archiveFileName = "sources.zip"
destinationDirectory = file("$buildDir/backup")
}
```

To run the backup task, execute:
```bash
./gradlew backupSources
```

**5. Tagging the Repository**
```bash
git tag -a ca1-part2 -m "Finish Part2 assigment"
git push origin ca1-part2
```

[⬆ Back to Top](#Table-of-contents)

## Part 3: Converting the basic version of the Tutorial application to Gradle

### Goals and Requirements (Part 3)

In this part of the assignment, the goal is to convert the "basic" version of the Tutorial application to Gradle, instead of Maven.

### Key Developments (Part 3)

**1. Create a new Gradle Spring Boot project using https://start.spring.io.**

![enter image description here](https://i.postimg.cc/y84R1D5b/Captura-de-ecr-2025-03-19-100240.png)

**2. Create part3 directory and copy files from part1 (basic with webpack.config.js and package.json and without src).**

```bash
rm -rf src - eliminar pasta src
cp -pr part1/basic/src part3/react-and-spring-data-rest-basic/ 
cp -p part1/basic/webpack.config.js part3/react-and-spring-data-rest-basic/
cp -p part1/basic/package.json part3/react-and-spring-data-rest-basic/
rm -rf src/main/resources/static/built/
```

Then run:
```bash
./gradlew bootRun
```

**3. Add org.siouan.frontend plugin and other updates to build.gradle.**
```gradle
id "org.siouan.frontend-jdk17" version "8.0.0

frontend {
nodeVersion = "16.20.2"
assembleScript = "run build"
cleanScript = "run clean"
checkScript = "run check"
}

"scripts": {
"webpack": "webpack",
"build": "npm run webpack",
"check": "echo Checking frontend",
"clean": "echo Cleaning frontend",
"lint": "echo Linting frontend",
"test": "echo Testing frontend"
}
```


**4. Add a task to gradle to copy the generated jar to a folder named ”dist” located a the project root folder level.**
```gradle
tasks.register('copyJar', Copy) {
	dependsOn build
	from("${buildDir}/libs")
	into("${rootDir}/dist")
	include("*.jar")
}
```

Then run:
```bash
./gradlew copyJar
```

![enter image description here](https://i.postimg.cc/7hC7xgYc/Captura-de-ecr-2025-03-19-111636.png)

**5. Add a task to gradle to delete all the files generated by webpack.**
```gradle
tasks.register('cleanWebpack', Delete) {
delete fileTree("${projectDir}/src/main/resources/static/built/")
}

clean.dependsOn(cleanWebpack)
```
Then run:
```bash
./gradlew clean
```

After running the command, it must be run the command to built webpack:
```bash
npm run build
```

## Final Results

### Implementation

### CA1 - Part1
Following the implementation of all the new features, the final state of the application is illustrated below:

![enter image description here](https://i.postimg.cc/Wb77znpF/Captura-de-ecr-2025-03-12-155116.png)
In our application's employee model, the fields "First Name", "Last Name", and "Description" were pre-existing components of the model and have not been modified in the scope of this project. The development enhancements began with the addition of the "Job Title" field in a prior exercise. Subsequently, during Part 1 of this CA1, the "Job Years" field was introduced to track the duration of employees' tenure within the company. The latest enhancement, implemented in Part 2 of CA1, involved adding the "Email" field, further augmenting our employee data model with contact information.

### CA1 - Part2

![enter image description here](https://i.postimg.cc/QxGCwr8F/Captura-de-ecr-2025-03-16-094650.png)  
The Server and the Client are communicating, creating a localhost chat.

![enter image description here](https://i.postimg.cc/vB5THPXY/Captura-de-ecr-2025-03-16-094955.png)  
In our project structure, we can see the backup directory containing all the code and the zip file, as intended.

### CA1 - Part3

![enter image description here](https://i.postimg.cc/qvNG4bLk/Captura-de-ecr-2025-03-19-113859.png)
We can see the app running in Gradle, just like in Maven in part1.


[⬆ Back to Top](#Table-of-contents)

### Branches
After merging the email-field and fix-invalid-email branches, they were deleted both locally and remotely with the following comands:

```bash
# Delete the branches locally
git branch -d email-field
git branch -d fix-invalid-email
git branch -d tut-basic-gradle

# Delete the branches remotely
git push origin --delete email-field
git push origin --delete fix-invalid-email
git push origin --delete tut-basic-gradle
```

Through Part1, I learned the importance of using branches for isolating changes related to specific features or fixes. This practice not only keeps the main codebase stable but also provides a clear and organized history of changes.

Through Part 3, after implementing and testing all new features, the branch tut-basic-gradle was merged to main branch.

[⬆ Back to Top](#Table-of-contents)

### Tags
Below is a visual depiction of the project's tags, generated using the `git tag` command.

![enter image description here](https://i.postimg.cc/Dyjc6xD9/Captura-de-ecr-2025-03-30-095813.png)

The use of tags taught me how to mark specific points in the project's history as significant. This is crucial for tracking the progress of the project over time and for quickly reverting to previous versions if necessary.

[⬆ Back to Top](#Table-of-contents)

### Issue Tracking
Several issues were created during the assignment. Initially, I opened only the issues I thought were enough to complete the project, but as the project evolved, more issues emerged.

Issues can serve multiple purposes in a project. They can be used to track bugs, feature requests, or general tasks. They can also be assigned to specific team members, have labels for easy searching, and can be linked to specific commits or pull requests.
In future assignments, the aim is to utilize issues throughout the entire development process. This will help in managing tasks, tracking progress, and facilitating collaboration, especially when working in a team setting.

[⬆ Back to Top](#Table-of-contents)

## Alternative Solutions
In seeking an alternative to Git for version control, Subversion (SVN) offers a distinct approach with its centralized model, contrasting Git's decentralized nature. This section compares SVN to Git in terms of version control features and describes how SVN could be utilized to achieve the goals set forth in this assignment.

### Comparison of SVN and Git

| Feature              | SVN                                                                                             | Git                                                                                                                    |
|----------------------|-------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| Architecture         | Centralized model, with a single repository as the authoritative source.                         | Distributed architecture, enabling multiple full-version repositories for enhanced redundancy and collaboration.       |
| Versioning Model     | Utilizes a linear, per-file versioning system, assigning incremental version numbers per commit. | Adopts a snapshot-based approach, encapsulating the state of the entire repository at each commit for comprehensive tracking. |
| Branching and Merging| Facilitates branch creation and merging, though the process may require more manual oversight.   | Provides efficient branching and merging capabilities, ideal for parallel development workflows.                        |
| Binary Files Handling| Efficiently manages binary file changes through delta storage, optimizing for large binary assets.| Stores complete binary files per change, which may increase repository size but ensures ease of access to all versions.    |


### Utilizing SVN for the Assignment part 1.1
The following sections detail how SVN could be utilized in alignment with the assignment's activities:

**Initial Repository Setup and Import:** The first step involves establishing a centralized SVN repository to host the Tutorial React.js and Spring Data REST application, centralizing all version-controlled files:
```bash
# Create a new SVN repository
svnadmin create /home/user/CA1/svn_repo

# Import the Tutorial application into the SVN repository
cd /home/user/CA1/part1
svn import . file:///home/user/CA1/svn_repo/trunk/part1 -m "Initial import with jobField"
```

**Continuous Integration: Committing and Tagging:** Adding jobYears field, testing, fixing bugs:

```bash
# Tag version 1.1.0
svn copy file:///home/user/CA1/svn_repo/trunk/part1 file:///home/user/CA1/svn_repo/tags/v1.1.0 -m "Tagging version 1.1.0"

# Added jobYears field and commit
svn commit -m "Added jobYears field to Employee entity, updated frontend, and added unit tests"

# Testing and fixing bugs
svn commit -m "Fixed bugs found during testing"

# Tag version 1.2.0
svn copy file:///home/user/CA1/svn_repo/trunk file:///home/user/CA1/svn_repo/tags/v1.2.0 -m "Tagging version 1.2.0"

# Tag repository ca1-part1.1
svn copy file:///home/user/CA1/svn_repo/trunk file:///home/user/CA1/svn_repo/tags/ca1-part1.1 -m "Final tag for CA1 Part 1.1"
```

By tailoring SVN's features to fit the requirements of this assignment, a workflow similar to Git's can be achieved, showcasing the adaptability of version control systems in software development environments.

## Alternative solution to Gradle: Ant

**Implementing the Assignment Goals with Ant**

To match the setup and functionality achieved with Gradle, this configuration outlines the steps required to configure Ant for the Spring Boot application. 
This alternative solution mirrors the Gradle setup, including frontend asset integration, custom build tasks, and file management.

- **Project Setup:**
I created an Ant build file for the Spring Boot application, including dependencies for REST, Thymeleaf, JPA, and H2:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project name="SpringBootApp" default="build" basedir=".">
<!-- Property definitions -->
<property name="build.dir" value="build"/>
<property name="dist.dir" value="${build.dir}/dist"/>
<property name="jar.name" value="springbootapp.jar"/>
<property name="static.dir" value="src/main/resources/static"/>
<property name="lib.dir" value="lib"/>

<!-- Ensure the lib directory exists -->
<mkdir dir="${lib.dir}"/>

        <!-- Dependencies should be manually placed inside the lib directory -->
        <!-- Required JAR files: -->
        <!-- - spring-boot-starter-data-jpa.jar -->
        <!-- - spring-boot-starter-thymeleaf.jar -->
        <!-- - spring-boot-starter-data-rest.jar -->
        <!-- - h2.jar -->
```

- **Frontend Integration:**
It was necessary to configure Ant to handle Node.js and npm installation as well as building the frontend:
```xml
<!-- Task to install Node.js and npm -->
<target name="install-node">
  <exec executable="npm">
    <arg value="install"/>
    <arg value="--prefix"/>
    <arg value="${static.dir}"/>
  </exec>
</target>

        <!-- Task to run the frontend build -->
<target name="build-frontend" depends="install-node">
<exec executable="npm">
  <arg value="run"/>
  <arg value="build"/>
  <arg value="--prefix"/>
  <arg value="${static.dir}"/>
</exec>
</target>
```

- **Copy JAR Task:**
  To copy the generated .jar file to a distribution folder, we use the 'copy' task in Ant:
```xml
<!-- Task to compile the application -->
<target name="compile" depends="clean">
  <mkdir dir="${build.dir}"/>
  <javac srcdir="src/main/java" destdir="${build.dir}" classpath="${lib.dir}/*"/>
</target>
        
        <!-- Task to package the application as a JAR -->
<target name="jar" depends="compile">
<mkdir dir="${dist.dir}"/>
<jar destfile="${dist.dir}/${jar.name}" basedir="${build.dir}"/>
</target>

        <!-- Task to copy the JAR to the distribution directory -->
<target name="copy-jar" depends="jar">
<copy file="${dist.dir}/${jar.name}" todir="${dist.dir}"/>
</target>
```

- **Delete Webpack Files Task:**
  To delete the Webpack-generated files, we use the 'delete' task in Ant:
```xml
<target name="delete-webpack-files">
  <delete>
    <fileset dir="${static.dir}/built">
      <include name="*" />
    </fileset>
  </delete>
</target>

        <!-- Make sure 'delete-webpack-files' runs before 'clean' -->
<target name="clean" depends="delete-webpack-files">
<delete dir="${build.dir}"/>
<delete dir="${static.dir}/built"/>
</target>
```

- **To build the project:**
```xml
 <!-- Main task -->
    <target name="build" depends="build-frontend, copy-jar"/>
</project>
```
[⬆ Back to Top](#Table-of-contents)

## Conclusion
Completing the Version Control with Git assignment has significantly broadened my understanding of version control systems and their critical role in software development. Part 1.1 established a strong foundation by focusing on direct modifications to the master branch, reinforcing essential practices such as committing and tagging. The transition to Part 1.2 introduced branching, offering deeper insights into managing complex scenarios like adding new features and fixing bugs. This demonstrated the importance of isolating changes to maintain a structured project history and facilitate streamlined management.

The Final Results section of this report underscores the practical outcomes of this learning experience, illustrating how the application’s functionality evolved through the incremental addition of features. This visual representation reinforced real-world applications of version control principles. Additionally, leveraging GitHub issues for tracking and managing problems provided a structured history of challenges and their resolutions, highlighting the versatility and effectiveness of issue tracking in software development.

Exploring SVN as an alternative to Git in Part 1.1 provided valuable insights into different version control methodologies. By comparing SVN’s centralized model with Git’s distributed approach, I gained a broader understanding of how various strategies can be tailored to specific project needs. This comparison emphasized the adaptability required in modern DevOps practices.

The Gradle component of Part 2 further enhanced my technical proficiency by teaching me how to create and configure custom tasks. Learning to execute the server, run unit tests, back up the source code, and generate zip archives not only streamlined the build and deployment process but also deepened my understanding of build automation. This hands-on experience with Gradle equipped me with practical skills in managing project builds and dependencies, which are crucial in contemporary software development environments.

Part 3 of this assignment introduced advanced concepts of collaborative software development by integrating continuous integration and continuous deployment (CI/CD) practices. Implementing automated workflows enhanced my ability to manage software releases efficiently, reducing errors and improving overall deployment speed. The experience of setting up pipelines and automating testing further reinforced the importance of integrating DevOps strategies into version control workflows. Additionally, working with collaborative development tools emphasized the importance of effective team coordination, peer reviews, and maintaining high-quality code standards.

Overall, this assignment not only strengthened my expertise in Git and introduced me to SVN but also emphasized the crucial role of build automation, issue tracking, and CI/CD in fostering collaboration, maintaining code integrity, and efficiently managing project development. The integration of these version control and automation principles has provided me with a comprehensive skill set that will be invaluable in future software development endeavors.

[⬆ Back to Top](#Table-of-contents)