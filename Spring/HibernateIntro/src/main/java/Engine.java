import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;

import javax.persistence.*;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private BufferedReader bufferedReader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }


    @Override
    public void run() {
        System.out.println("Select exercise number:");
        try {
            int exerciseNumber = Integer.parseInt(bufferedReader.readLine());

            switch (exerciseNumber) {
                case 2 -> exerciseTwo();
                case 3 -> exerciseThree();
                case 4 -> exerciseFour();
                case 5 -> exerciseFive();
                case 6 -> exerciseSix();
                case 7 -> exerciseSeven();
                case 8 -> exerciseEight();
                case 9 -> exerciseNine();
                case 10 -> exerciseTen();
                case 11 -> exerciseEleven();
                case 12 -> exerciseTwelve();
                case 13 -> exerciseThirteen();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void exerciseThirteen() throws IOException {
        System.out.println("Please enter town name:");
        String townName= bufferedReader.readLine();

        Town town = entityManager.createQuery("SELECT t FROM Town t " +
                        "WHERE t.name = :input", Town.class)
                .setParameter("input", townName)
                .getSingleResult();

        int affectedRows =removedAddressesByTownId(town.getId());

        entityManager.getTransaction().begin();
        entityManager.remove(town);
        entityManager.getTransaction().commit();
        System.out.printf("%d %s in %s deleted", affectedRows, affectedRows > 1 ? "addresses" : "address",
                townName);

    }

    private int removedAddressesByTownId(Integer id) {


        entityManager.getTransaction().begin();
        List<Address> result = entityManager.createQuery("Delete from Address a " +
                "where a.town = :id", Address.class)
                .setParameter("id",id)
                        .getResultList();

        entityManager.getTransaction().commit();
        return result.size();
    }

    @SuppressWarnings("unchecked")
    private void exerciseTwelve() {
        List<Object[]> departmentRows = entityManager.createNativeQuery("SELECT d.name, MAX(e.salary) AS maxSalary FROM employees e " +
                        "JOIN departments d on e.department_id = d.department_id " +
                        "GROUP BY e.department_id " +
                        "HAVING maxSalary NOT BETWEEN 30000 AND 70000")
                .getResultList();
        departmentRows.forEach(r -> System.out.printf("%s  %.2f%n", r[0], r[1]));
    }

    private void exerciseEleven() throws IOException {
        System.out.println("Please enter Pattern for Employee name:");
        String pattern = bufferedReader.readLine();
        List<Employee> employees = entityManager.createQuery("Select e FROM Employee e " +
                        "WHERE e.firstName LIKE :pattern", Employee.class)
                .setParameter("pattern",pattern + "%")
                .getResultList();

        employees.forEach(employee -> System.out.printf("%s %s - %s - $%.2f%n",employee.getFirstName()
        ,employee.getLastName(),employee.getJobTitle(),employee.getSalary()));


    }

    private void exerciseTen() {
        entityManager.getTransaction().begin();
        entityManager.createQuery("UPDATE FROM Employee e " +
                "SET e.salary = e.salary * 1.12 " +
                "WHERE e.department.id IN (1, 2, 4, 11) ").executeUpdate();

        entityManager.createQuery("Select e from Employee e " +
                "WHERE e.department.id IN (1, 2, 4, 11)",Employee.class)
                .getResultStream()
                .forEach(employee -> System.out.printf("%s %s ($%.2f)%n",employee.getFirstName()
                        ,employee.getLastName(),employee.getSalary()));

    }

    private void exerciseNine() {

        List<Project> projects = entityManager.createQuery("Select p FROM Project p " +
                        "ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList();


        for (Project project : projects) {
            System.out.printf("Project name - %s%n " +
                    "Project Description: %s%n\t" +
                    "Project Start Date: %s%n\t" +
                    "Project End Date: %s%n\t",project.getName(),project.getDescription()
            , project.getStartDate()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),project.getEndDate());
        }
    }

    private void exerciseEight() throws IOException {
        System.out.println("Please enter employee id:");
        int id = Integer.parseInt(bufferedReader.readLine());

        Employee employee = entityManager.createQuery("Select e " +
                "From Employee e " +
                "WHERE e.id = :id_value",Employee.class)
                .setParameter("id_value",id)
                .getSingleResult();

        StringBuilder sb = new StringBuilder();
        sb.append(employee.getFirstName())
                .append(" ")
                .append(employee.getLastName())
                .append(" - ")
                .append(employee.getJobTitle())
                .append("\n\t");
                employee.getProjects().stream().map(Project::getName).sorted()
                        .forEach(name -> sb.append(name).append("\n\t"));
        System.out.println(sb);

    }

    private void exerciseSeven() {
        List<Address> resultList = entityManager.createQuery("Select a From Address a " +
                        "ORDER BY a.employees.size DESC ", Address.class)
                .setMaxResults(10)
                .getResultList();

        resultList.forEach(a -> System.out.printf("%s, %s - %d employees%n"
                ,a.getText(),a.getTown().getName(),a.getEmployees().size()));


    }

    private void exerciseSix() throws IOException {
        System.out.println("Please enter employees last name:");
        String lastName = bufferedReader.readLine();

        Address address = new Address();
        address.setText("Vitoshka 15");

        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();

        Employee employee = entityManager.createQuery("Select e From Employee e " +
                        "Where e.lastName = :last_name ", Employee.class)
                .setParameter("last_name", lastName)
                .getSingleResult();
        entityManager.getTransaction().begin();
        employee.setAddress(address);
        entityManager.getTransaction().commit();


    }

    private void exerciseFive() {
        entityManager.createQuery("SELECT e FROM Employee e " +
                "WHERE e.department.name = :department_name " +
                "ORDER BY e.salary,e.id", Employee.class)
                .setParameter("department_name","Research and Development")
                .getResultStream()
                .forEach(e -> System.out.printf("%s %s from Research and Development - $%.2f%n"
                ,e.getFirstName(),e.getLastName(),e.getSalary()));

    }

    private void exerciseFour() throws IOException {
        System.out.println("Please enter salary for comparison ");
        BigDecimal bigDecimal = BigDecimal.valueOf(Long.parseLong(bufferedReader.readLine()));
        entityManager.getTransaction().begin();
        entityManager.createQuery("SELECT e.firstName FROM Employee e " +
                        "Where e.salary > :salary ", String.class)
                .setParameter("salary", bigDecimal)
                .getResultList().forEach(System.out::println);
        entityManager.getTransaction().commit();

    }


    private void exerciseThree() throws IOException {
        System.out.println("Please enter employee name:");
        String[] fullName = bufferedReader.readLine().split("\\s+");
        String firstName = fullName[0];
        String lastName = fullName[1];
        entityManager.getTransaction().begin();
        entityManager.getTransaction();
        Long singleResult = entityManager
                .createQuery("Select count(e) from Employee as e " +
                        " Where e.firstName = :f_name AND e.lastName = :l_name", Long.class)
                .setParameter("f_name", firstName)
                .setParameter("l_name", lastName)
                .getSingleResult();

        System.out.printf(singleResult == 1 ? "This employee is in the Database" :
                "This employee isn't in the Database");

        entityManager.getTransaction().commit();
    }

    private void exerciseTwo() {
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("UPDATE Town t " +
                "Set t.name = upper(t.name)" +
                "Where length(t.name)<= 5");
        System.out.println(query.executeUpdate());
        entityManager.getTransaction().commit();

    }
}
