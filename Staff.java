/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HospitalModel;

/**
 *
 * @author Lenovo
 */


public abstract class Staff {

    private final String employeeId;
    private final String fullName;
    private final double salary;

    public Staff(String employeeId, String fullName, double salary) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.salary = salary;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public double getSalary() {
        return salary;
    }

    public abstract String getRole();

    public abstract String toCsv();

    @Override
    public String toString() {
        return employeeId + " - " + fullName + " (" + getRole() + ")";
    }
}
