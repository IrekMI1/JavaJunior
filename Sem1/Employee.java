package Sem1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Employee {
    private String name;
    private int age;
    private String department;
    private double salary;

    public Employee(String name, int age, String department, double salary) {
        this.name = name;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.age;
    }

    public static void main(String[] args) {
        List<Employee> employees = List.of(
                new Employee("Андрей", 32, "Технологи", 80000),
                new Employee("Дмитрий", 44, "Монтажники", 77000),
                new Employee("Женя", 37, "Конструкторы", 63000),
                new Employee("Илья", 26, "Монтажники", 50000),
                new Employee("Марс", 53, "Конструкторы", 90000),
                new Employee("Аня", 33, "Технологи", 76000),
                new Employee("Лена", 52, "Бухгалтерия", 55000),
                new Employee("Надя", 45, "Кадры", 45000),
                new Employee("Игорь", 38, "Менеджеры", 62000),
                new Employee("Саша", 42, "Менеджеры", 71000),
                new Employee("Коля", 31, "Менеджеры", 71000),
                new Employee("Гена", 32, "ГИПы", 99000)
        );

        System.out.println("Список отделов по сотрудникам: " +
                employees.stream()
                        .map(Employee::getDepartment)
                        .distinct()
                        .toList()
        );

        employees.stream()
                .filter(it -> it.getSalary() < 70000)
                .forEach(it -> it.setSalary(it.getSalary() * 1.2));

        System.out.println("Зарплаты после повышения на 20% :" +
                employees.stream()
                        .map(it -> String.format("Name: %s, Salary: %s", it.getName(), it.getSalary()))
                        .toList());

        Map<String, List<Employee>> departments = new HashMap<>();

        employees.stream()
                .map(Employee::getDepartment)
                .distinct()
                .forEach(dep -> {
                    List<Employee> employees1 = new ArrayList<>();
                    for (Employee emp : employees) {
                        if (emp.getDepartment().equals(dep)) {
                            employees1.add(emp);
                        }
                    }
                    departments.put(dep, employees1);
                });

        System.out.println("Отделы со списком сотрудников: " + departments);

        Map<String, Double> depAverageSalaries = new HashMap<>();

        employees.stream()
                .map(Employee::getDepartment)
                .forEach(dep -> {
                    int i = 0;
                    double salary = 0.0;
                    for (Employee employee: employees) {
                        if (dep.equals(employee.getDepartment())) {
                            salary += employee.getSalary();
                            i++;
                        }
                    }
                    depAverageSalaries.put(dep, salary / i);
                });

        System.out.println("Средняя зарплата по отделам: " + depAverageSalaries);
    }
}
