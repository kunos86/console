package pl.sda.view;

import pl.sda.EmployeeEntityManager;
import pl.sda.dto.Employee;
import pl.sda.view.table.TablePrinter;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class EmployeeManager {


    public void printList() {
        printList(getAllEmployees());
    }

    private void printList(List<Employee> employees) {
        TablePrinter<Employee> tablePrinter = new TablePrinter<Employee>()
                .withData(employees)
                .withColumn("Imię", employee -> ((Employee) employee).getFirstName())
                .withColumn("Nazwisko", employee -> ((Employee) employee).getLastName())
                .withColumn("Stanowisko", employee -> ((Employee) employee).getPosition())
                .withColumn("Rok ur.", employee -> ((Employee) employee).getBirthYear().toString())
                .withColumn("Wynagrodzenie", employee -> ((Employee) employee).getSalary().toString());

        tablePrinter.printTable();

    }

    public void printEmployee() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj id:");
        long id = Long.parseLong(scanner.nextLine());

        try {
            printList(List.of(getEmployee(id)));
        }catch (NullPointerException e){
            System.err.println("There is no employee with given id!");
        }
    }

    private List<Employee> getAllEmployees() {

        EntityManager entityManager = EmployeeEntityManager.getEntityManagerFactory().createEntityManager();

        List<Employee> employeeList = entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();


        entityManager.close();

        return employeeList;
    }

    private Employee getEmployee(long id) {

        EntityManager entityManager = EmployeeEntityManager.getEntityManagerFactory().createEntityManager();

        Employee employee = entityManager.find(Employee.class, id);


        entityManager.close();

        return employee;
    }

    public void addEmployee() {
        Scanner scanner = new Scanner(System.in);

        EntityManager entityManager = EmployeeEntityManager.getEntityManagerFactory().createEntityManager();

        Employee employee = new Employee();

        System.out.println("Podaj imię:");
        employee.setFirstName(scanner.nextLine());

        System.out.println("Podaj nazwisko:");
        employee.setLastName(scanner.nextLine());

        System.out.println("Podaj stanowisko:");
        employee.setPosition(scanner.nextLine());

        System.out.println("Podaj rok urodzenia w formacie dd-MM-RRRR:");
        employee.setBirthYear(LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        System.out.println("Podaj wyngrodzenie:");
        employee.setSalary(Double.parseDouble(scanner.nextLine()));

        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();


        entityManager.close();
    }

    public void removeEmployee() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj id:");

        long id = Long.parseLong(scanner.nextLine());

        EntityManager entityManager = EmployeeEntityManager.getEntityManagerFactory().createEntityManager();

        entityManager.getTransaction().begin();

        Employee employee = getEmployee(id);

        if (!entityManager.contains(employee)) {
            employee = entityManager.getReference(employee.getClass(), employee.getId());
        }

        entityManager.remove(employee);
        entityManager.getTransaction().commit();


        entityManager.close();
    }

    public void updateEmployee(){
        Scanner scanner = new Scanner(System.in);

        EntityManager entityManager = EmployeeEntityManager.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        System.out.println("Podaj id:");
        long id = Long.parseLong(scanner.nextLine());

        Employee employee = getEmployee(id);

        if (!entityManager.contains(employee)) {
            employee = entityManager.getReference(employee.getClass(), employee.getId());
        }

        System.out.println("Podaj nowe stanowisko:");
        employee.setPosition(scanner.nextLine());

        System.out.println("Podaj nowe wyngrodzenie:");
        employee.setSalary(Double.parseDouble(scanner.nextLine()));

        entityManager.getTransaction().commit();
    }
}
