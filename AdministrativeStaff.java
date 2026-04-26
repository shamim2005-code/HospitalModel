/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HospitalModel;

/**
 *
 * @author Lenovo
 */


public class AdministrativeStaff extends Staff {

    private final String department;

    public AdministrativeStaff(String employeeId, String fullName, double salary, String department) {
        super(employeeId, fullName, salary);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String getRole() {
        return "Administrative Staff";
    }

    @Override
    public String toCsv() {
        return String.join(",",
                "ADMIN",
                getEmployeeId(),
                getFullName(),
                String.valueOf(getSalary()),
                department
        );
    }
}
