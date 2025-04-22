# CA2: Virtualization with Vagrant

**Author:** Ricardo Marques

**Date:** 01/04/2025

**Discipline:** DevOps

**Program:** SWitCH DEV

**Institution:** Instituto Superior de Engenharia/ Instituto Politécnico do Porto

## Table of Contents

- [Introduction](#introduction)
- [Goals and Requirements](#goals-and-requirements)
  - [Part1](#part1)
  - [Part2](#part2)
- [Environment Setup](#environment-setup)
  - [Part1](#part1-1)
  - [Part2](#part2-1)
- [Key Developments](#key-developments)
  - [Part1](#part1-2)
    - [Installed and configured all required development tools inside the Ubuntu Server VM](#installed-and-configured-all-required-development-tools-inside-the-ubuntu-server-vm-)
    - [Successfully built and ran the Spring Boot tutorial project and the gradle_basic_demo project inside the VM](#successfully-built-and-ran-the-spring-boot-tutorial-project-and-the-gradle_basic_demo-project-inside-the-vm)
    - [Accessed Spring Boot web applications from the host machine’s browser using the VM’s IP address](#accessed-spring-boot-web-applications-from-the-host-machines-browser-using-the-vms-ip-address)
    - [Tagging the repository with CA2-part1 after pushing README.md](#tagging-the-repository-with-ca2-part1-after-pushing-readmemd)
  - [Part2](#part2-2)
    - [Used Bitbucket Repository as Initial Solution](#used-bitbucket-repository-as-initial-solution)
    - [Setup Vagrant and Create VMs](#setup-vagrant-and-create-vms)
    - [Provision Web and DB Machines](#provision-web-and-db-machines)
    - [Spring Boot Configuration](#spring-boot-configuration)
    - [Built and launched the Spring Boot application in the web VM with a remote database](#built-and-launched-the-spring-boot-application-in-the-web-vm-with-a-remote-database)
    - [Tagging the repository with CA2-part2 after final testing](#tagging-the-repository-with-ca2-part2-after-final-testing)
- [Alternative Solution:Using Hyper-V with Vagrant](#alternative-solutionusing-hyper-v-with-vagrant)
- [Conclusion](#conclusion)

## Introduction
This documentation summarizes the work completed for the CA2 assignment. The objective was to simulate a realistic software development environment using a virtual machine and apply practices such as version control, building and testing Java projects with Gradle, and managing simple client-server communication. All development was done using Ubuntu Server in a virtualized environment, reflecting production-like conditions.

## Goals and Requirements
### Part1
1. Set up a virtual machine using VirtualBox or UTM with Ubuntu Server.

2. Install all necessary tools inside the VM (Git, JDK, Maven, Gradle).

3. Clone the personal repository and build previous assignments inside the VM.

4. Run and test the Spring Boot tutorial and gradle_basic_demo projects.

5. Document and explain any limitations or issues (e.g., GUI-related tasks).

6. Access web applications from the host browser using the VM’s IP.

7. Run the chat server in the VM and connect to it using client applications on the host.

8. Use Git for version control, tagging the repository with CA2-part1.

9. Document the full process in a README file without committing the VM image.

### Part2
1. Use Vagrant to provision two virtual machines.

2. Understand and adapt the provided Vagrantfile.

3. Copy and include the Vagrantfile in my repository.

4. Modify the Vagrantfile to run my custom SpringBoot app.

5. Document all steps and configuration.

6. Tag the repository with CA2-part2.

[⬆ Back to Top](#Table-of-contents)

## Environment Setup
### Part1
A virtual machine was created using VirtualBox (or UTM for Apple Silicon) running Ubuntu Server. Inside the VM, the following tools were installed:

* Git

* OpenJDK 17

* Maven

* Gradle

The Git repository containing the necessary projects was cloned into the VM, and all builds and executions were performed within this environment.

### Part2
For Part 2, a development environment was set up using Vagrant to automate the provisioning of two separate Ubuntu Server virtual machines, using the following tools:

* Vagrant

* VirtualBox (or UTM for Apple Silicon)

* Ubuntu Server 18.04 LTS

* Spring Boot (Gradle-based)

* H2 Database


[⬆ Back to Top](#Table-of-contents)

## Key Developments
### Part1
#### Installed and configured all required development tools inside the Ubuntu Server VM.  
**Create a VM**
- The first step was to download VirtualBox from https://www.virtualbox.org/wiki/Downloads and install it.
- I launched VirtualBox and clicked New to begin setting up a new virtual machine. I named the VM and selected the appropriate type and version to match the operating system I intended to install.
- I allocated enough memory to ensure smooth operation and created a virtual hard disk to accommodate the virtual machine’s requirements.
- In the VM settings under the storage section, I mounted the ISO file of the OS to the virtual CD/DVD drive. I started the VM and followed the on-screen instructions to install the OS.  
  <img src="https://i.postimg.cc/W1B0nCT0/Captura-de-ecr-2025-04-01-110928.png" width="500">

- After the installation was complete, I installed the VirtualBox Guest Additions.
- I configured the virtual machine settings to prepare for the Ubuntu 18.04 minimal installation. I connected the VM to the Ubuntu installation media available via the provided minimal CD link. I allocated 5000 MB of RAM to ensure adequate performance. For networking, I set Network Adapter 1 to NAT for internet access and configured Network Adapter 2 as a Host-only Adapter (vboxnet0), facilitating isolated communication with the host machine.  
  <img src="https://i.postimg.cc/bN8nskL9/Captura-de-ecr-2025-04-01-110657.png" width="500">  
  <img src="https://i.postimg.cc/tgPR3m4Z/Captura-de-ecr-2025-04-01-110710.png" width="500">  

**Configure Network and Services**  
Once the basic virtual machine setup was completed, I focused on configuring the network and essential services to enhance functionality and accessibility of the VM.

- I opened the VirtualBox Host Network Manager from the main menu by selecting File -> Host Network Manager.
- I clicked the Create button, which added a new Host-only network to the list. This setup allowed me to specify a name for the network within the virtual machine's network settings.
- After setting up the Host-only Adapter (vboxnet0), I checked the IP address range, which was 192.168.56.1/24. I chose 192.168.56.5 as the IP for the second adapter of the VM, ensuring it fell within the designated subnet.
- Upon booting the virtual machine, I proceeded to update the package repositories using sudo apt update.
- I installed the network tools package with sudo apt install net-tools to facilitate network configuration.
- To assign the chosen IP address, I edited the network configuration file by executing sudo nano /etc/netplan/01-netcfg.yaml. I ensured the file reflected the following settings for network configuration:
```yaml
network:
  version: 2
  renderer: networkd
  ethernets:
    enp0s3:
      dhcp4: yes
    enp0s8:
      addresses:
        - 192.168.56.5/24
```

After editing, I applied the changes with sudo netplan apply.
To remotely manage the VM, I installed and configured the OpenSSH server with sudo apt install openssh-server followed by enabling password authentication by editing /etc/ssh/sshd_config to uncomment the line PasswordAuthentication yes. I then restarted the SSH service with `sudo service ssh restart.
I also set up an FTP server to transfer files to and from the VM by installing vsftpd using sudo apt install vsftpd. I enabled write access within the FTP server configuration by editing /etc/vsftpd.conf to uncomment the line write_enable=YES and then restarted the service with sudo service vsftpd restart.

**Clone the Repository**
- To clone my individual repository inside the virtual machine, I first needed to set up secure SSH access between the VM and my GitHub repository. Here’s how I accomplished this:

- I generated a new SSH key pair in the VM to ensure secure communication with GitHub. Using the terminal, I executed the following command:
```bash
ssh-keygen -t ed25519 -C "1241923@isep.ipp.pt"
```
- To add the newly created SSH key to my GitHub account, I first accessed the SSH key content by displaying it in the terminal:
```bash
cat ~/.ssh/id_ed25519.pub
```
- I then logged into my GitHub account, navigated to Settings -> SSH and GPG keys, and clicked on New SSH key. I pasted the key into the field provided and saved it, which allowed my VM to authenticate with GitHub securely.
With SSH configured, I cloned my repository into the desired directory within the VM using the following command:
```bash
mkdir CA2
git@github.com:RicardoMarques21/devops-24-25-1241923.git
```

[⬆ Back to Top](#Table-of-contents)

#### Successfully built and ran the Spring Boot tutorial project and the gradle_basic_demo project inside the VM.
**Set Up Development Environment**
After setting up the virtual machine and ensuring it was properly configured for network access, I proceeded to install
the necessary tools required for the projects.

- I began by updating and upgrading the installed packages to ensure all
  software on the VM was up-to-date. This was accomplished using the following commands:

```bash
sudo apt update
sudo apt upgrade
```

- Next, I installed Git, for version control and source code management:

```bash
sudo apt install git
```

- I also installed both the JDK (Java Development Kit) and JRE (Java Runtime Environment) for Java-based projects:

```bash
sudo apt install openjdk-17-jdk openjdk-17-jre
```

- Maven, which is needed for project dependency management and building Java projects, was installed next:

```bash
sudo apt install maven
```

- Installing Gradle required a few more steps due to its packaging:

```bash
wget https://services.gradle.org/distributions/gradle-8.6-bin.zip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-8.6-bin.zip
```

- To ensure Gradle could be executed from any location within the terminal, I added its bin directory to the system
  PATH by modifying the `.bashrc` file:

```bash
echo "export GRADLE_HOME=/opt/gradle/gradle-8.6" >> ~/.bashrc
echo "export PATH=\$GRADLE_HOME/bin:\$PATH" >> ~/.bashrc
source ~/.bashrc
```

These installations equipped the virtual machine with the necessary tools to build and manage Java applications
effectively, allowing me to proceed with executing and testing the projects.
To confirm that all tools were installed correctly and are functioning as expected, I executed the following commands to
check their versions:

```bash
git --version
java --version
mvn --version
gradle --version
```

**Execute the spring boot tutorial basic project**
In this section, I executed the **Spring Boot tutorial basic project**, which was part of the prerequisites from
previous
assignments. The goal was to build and run the project within the virtual machine environment set up previously.

1. I navigated to the `basic` directory where the project files are located. This directory contains the Spring Boot
   application setup.
2. To launch the Spring Boot application, I executed the following command in the terminal within the project directory:

```bash
./mvnw spring-boot:run
```
In this section, I describe the process of building and running the **gradle_basic_demo project**. This project required
execution across two environments: the virtual machine and my host machine.

1. I navigated to the `gradle_basic_demo` directory within the virtual machine. To build the project, I executed the
   following command:

```bash
./gradlew build
```

In this part of the assignment, I focused on building and executing another component of the **gradle_basic_demo
project** using the virtual machine.

1. I navigated to the `basic` folder within the `gradle_basic_demo` directory.
2. The following commands were executed to build the application and then start the Spring Boot server, making the
   application accessible via the web.

```bash
./gradlew build
./gradlew bootRun
```
[⬆ Back to Top](#Table-of-contents)

#### Accessed Spring Boot web applications from the host machine’s browser using the VM’s IP address.
1. To ensure the application was accessible from external devices such as the host machine or other devices on the same
   network, I specified the VM's IP address when accessing it. To determine the IP address, I used the `ifconfig`
   command. Here is the URL I used to access the application:

```
http://192.168.56.5:8080/
```
<img src=https://i.postimg.cc/zv05psh7/Captura-de-ecr-2025-04-06-101013.png width="500">  

2. Since the virtual machine setup was based on Ubuntu Server without a desktop environment, running GUI applications
   like the project’s chat client was not feasible on the VM. To address this, I opened a terminal on my host machine,
   navigated to the `gradle_basic_demo` directory (which also required a clone on the host), and ran the client component
   using the command below. This setup allowed the client on my host machine to communicate with the server running in
   the VM by specifying the VM's IP address and the port number:

```bash
./gradlew runClient --args="192.168.56.5 59001"
```
<img src=https://i.postimg.cc/ydWFW4HR/Captura-de-ecr-2025-04-06-101931.png width="500">  
<img src=https://i.postimg.cc/h4z53Ndh/Captura-de-ecr-2025-04-06-102117.png width="500">

I successfully opened three chat windows from the host machine, demonstrating the functionality of the client-server
communication. The chat application functioned as expected, with messages being sent and received.
A screenshot captured this interaction, illustrating the active connection and data exchange facilitated by the network
setup.

<img src="https://i.postimg.cc/zXFJQysd/Captura-de-ecr-2025-04-06-102459.png" width="500">


3. Once the server was running, I accessed the application by entering the following URL into a web browser. This URL
   directed me to the landing page of the Spring Boot application, hosted within the virtual machine, confirming that
   the server was operational and could handle client requests over the network.

```bash
http://192.168.56.5:8080/
```

<img src="https://i.postimg.cc/zv05psh7/Captura-de-ecr-2025-04-06-101013.png" width="500">

#### Tagging the repository with CA2-part1 after pushing README.md.

After all tests were completed and the documentation was written, I created the tag CA2-part1 and pushed it to the repository.

### Part2
#### Used Bitbucket Repository as Initial Solution
To set up the multi-VM environment required for this assignment, I used the Bitbucket repository pssmatos/vagrant-multi-spring-tut-demo as the initial solution. This repository provided a foundational Vagrant setup with two virtual machines:

* web VM – configured to run the Spring Boot application

* db VM – configured to host the H2 database

```bash
git clone https://bitbucket.org/pssmatos/vagrant-multi-spring-tut-demo/
```
Then I copy the Vagrantfile to CA2-part2 directory.
```bash
cp -r vagrant-multi-spring-tut-demo/Vagrantfile C:\Users\ricar\Documents\Switch\devops-24-25-1241923\CA2
```
#### Setup Vagrant and Create VMs

I began by installing Vagrant from https://developer.hashicorp.com/vagrant/downloads and VirtualBox (or UTM on Apple Silicon).

I created a new project folder and initialized a Vagrant configuration file with the following command:

```bash
vagrant init
```
I then edited the Vagrantfile to define and provision two separate virtual machines:

* One for hosting the Spring Boot web application.

* Another for running the H2 database server.

The updated Vagrantfile included:

* config.vm.define blocks for web and db VMs.

* Private network IPs (192.168.56.10 and 192.168.56.11).

* Port forwarding from 8080 on the web VM to the host.

After all configurations to the Vagrantfile, my final version is as follows:

```ruby
Vagrant.configure("2") do |config|
  # Enable SSH agent forwarding for GitHub authentication
  config.ssh.forward_agent = true

  # Use Ubuntu 22.04 (Jammy Jellyfish) base box
  config.vm.box = "ubuntu/jammy64"

  # =====================================================
  # Common provisioning for all VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
        openjdk-17-jdk-headless
  SHELL

  # =====================================================
  # Database VM configuration
  config.vm.define "db" do |db|
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.56.11"
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    # Download H2 database JAR
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    # Start the H2 server in the background (always run)
    db.vm.provision "shell", run: 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
  end

  # =====================================================
  # Webserver VM configuration
  config.vm.define "web" do |web|
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.56.10"
    web.vm.network "forwarded_port", guest: 8080, host: 8080

    # Share the host's SSH directory into the VM (read-only)
    web.vm.synced_folder "~/.ssh", "/home/vagrant/.ssh_host", type: "virtualbox"

    # Enable SSH agent forwarding inside this VM
    web.ssh.forward_agent = true

    # Web VM provisioning steps
    web.vm.provision "shell", privileged: false, inline: <<-SHELL
      # Copy the SSH private key into place and secure it
      cp /home/vagrant/.ssh_host/id_ed25519 ~/.ssh/id_ed25519
      chmod 600 ~/.ssh/id_ed25519

      # Trust GitHub host to prevent SSH prompt
      ssh-keyscan github.com >> ~/.ssh/known_hosts

      # Clone the GitHub repository (only if it doesn’t exist yet)
      if [ ! -d "devops-24-25-1241923" ]; then
        git clone git@github.com:RicardoMarques21/devops-24-25-1241923.git
      fi

      # Navigate to the project directory and build the app
      cd devops-24-25-1241923/CA1/part3/react-and-spring-data-rest-basic
      chmod u+x gradlew
      ./gradlew clean build
      ./gradlew bootRun

      # Deploy the generated WAR file to Tomcat
      sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
    SHELL
  end
end
```
#### Provision Web and DB Machines
I created two provisioning scripts to automate the setup of the environment:

provision-web.sh installed:

* OpenJDK 17

* Gradle

* Git

* Spring Boot application dependencies

provision-db.sh installed:

* Java

* H2 database server

* Configured H2 to accept TCP connections

#### Spring Boot Configuration

I modified the application.properties file of the Spring Boot project to connect to the external H2 database hosted on the db VM:

```bash
spring.datasource.url=jdbc:h2:tcp://192.168.56.11:1521/~/test
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```

Update React App.js: The src/App.js needed adjustments to match the new backend path:
```js
client({method: 'GET', path: '/basic-0.0.1-SNAPSHOT/api/employees'}).done(response => {
```
After booting both machines with vagrant up, the web VM could successfully connect to the db VM over the private network.

#### Built and launched the Spring Boot application in the web VM with a remote database.
As part of the provisioning process for the web VM, the Spring Boot application was automatically prepared and launched using Gradle.
1. Clone the repository and navigate to the project directory.
* During provisioning, the GitHub repository is cloned into the VM automatically using the following commands inside the provision-web.sh script:
```bash
git clone git@github.com:RicardoMarques21/devops-24-25-1241923.git
cd devops-24-25-1241923
```
2. Build and run the application
* Once inside the project directory, the application is built and started using Gradle. These steps are also automated by the provisioning script:
```bash
./gradlew build
./gradlew bootRun
```
Once the server was running, I accessed the application from the host browser using:

```text
 http://localhost:8080/basic-0.0.1-SNAPSHOT/
```
<img src="https://i.postimg.cc/7LnD5qr7/Captura-de-ecr-2025-04-09-170443.png" width="500">  

I also accessed the H2 console by visiting http://localhost:8082/h2-console and connected to the H2 database using the JDBC URL jdbc:h2:tcp://192.168.56.11:9092/./jpadb. Here's a snapshot of the H2 Login page, where I entered the connection details:

<img src="https://i.postimg.cc/g2V82t45/Captura-de-ecr-2025-04-09-170752.png" width="500">

#### Tagging the repository with CA2-part2 after final testing.
After successfully running the system using the two-VM setup and verifying everything was functional, I committed all final changes and tagged the repository:

```bash
git add .
git commit -m "Final updates for CA2 Part 2"
git push
git tag CA2-part2
git push origin CA2-part2
```
This marks the completion of the CA2 Part 2 submission.

[⬆ Back to Top](#Table-of-contents)

## Alternative Solution:Using Hyper-V with Vagrant

In this section, I explore Hyper-V as an alternative virtualization tool to VirtualBox. Below is a detailed comparison between Hyper-V and VirtualBox, followed by instructions on how Hyper-V can be used with Vagrant to achieve the goals outlined for this assignment.

### Comparison of Hyper-V and VirtualBox

#### VirtualBox:

* **Overview**: A free, open-source hypervisor from Oracle, favored for its ease of use and support for various OSes.

* **Pros**:

  * Free and open-source.

  * User-friendly GUI.

  * Supports many guest operating systems.

* **Cons**:

  * Limited advanced features.

  * Can be slower with 3D graphics and larger VMs.

#### Hyper-V:
* **Overview**: A virtualization platform from Microsoft, included with Windows Pro and Enterprise editions, providing efficient virtualization and integration with Windows environments.

* **Pros**:

   * High performance and stability, especially with Windows-based virtual machines.
    
   * Native integration with Windows OS.
    
   * Provides advanced features like live migration, snapshots, and virtual machine replication.
    
   * Built-in support for containerization and cloud workloads.

* **Cons**:

  * Not available on Windows Home edition.

  * Steeper learning curve for configuration and management.

  * Limited cross-platform support compared to VirtualBox.

### Using Hyper-V with Vagrant

Integrating Hyper-V with Vagrant involves a few steps:

1. **Enable Hyper-V**: Open PowerShell as Administrator and run:
```shell
  dism.exe /Online /Enable-Feature /FeatureName:Microsoft-Hyper-V-All /All /LimitAccess /Restart
```

2. **Install the Vagrant Hyper-V Plugin**:This plugin allows Vagrant to manage virtual machines on Hyper-V.
```bash
vagrant plugin install vagrant-hyperv
```

3.**Configure the Vagrantfile**:Update your Vagrantfile to use Hyper-V as the provider.
```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/bionic64"
  config.vm.provider "hyperv" do |h|
    h.memory = 1024
    h.cpus = 2
  end
end
```

Switching to **Hyper-V** with Vagrant provides a robust solution for Windows-based environments, enhancing the virtualization capabilities. It integrates well with native Windows features and offers strong performance, especially for Windows-centric workloads.

This alternative solution aligns with the objective of improving the development environment and streamlining transitions to production-like settings, particularly for users on Windows platforms.

[⬆ Back to Top](#Table-of-contents)

## Conclusion
CA2 provided valuable hands-on experience in configuring and working within a server-based virtual environment. Through the setup of a virtual machine running Ubuntu Server, I was able to replicate real-world conditions for building and deploying Java applications. The process involved installing and configuring essential development tools such as Git, Maven, Gradle, and Java, as well as ensuring smooth communication between the virtual machine and the host machine.

In Part 2, I focused on extending the project by launching and testing a Spring Boot application on the web VM, with a remote database. I successfully cloned the repository, built the project using Gradle, and started the application. I also enabled seamless communication between the host and the virtual machine by accessing the application via the VM’s IP address. This process reinforced key concepts related to environment configuration, application deployment, and database integration within a virtualized system.

Through both parts of the assignment, I gained a deeper understanding of server-side execution, the importance of environment configuration, and the hands-on aspects of deploying and managing Java applications in a virtualized environment.

[⬆ Back to Top](#Table-of-contents)