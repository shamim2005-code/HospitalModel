/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HospitalModel;

/**
 *
 * @author Lenovo
 */

public class SeniorDoctor extends Doctor {

    public SeniorDoctor(String employeeId, String fullName, double salary, String specialization, String licenseNumber) {
        super(employeeId, fullName, salary, specialization, licenseNumber);
    }

    @Override
    public String getRole() {
        return "Senior Doctor";
    }

    @Override
    public String toCsv() {
        return String.join(",",
                "SENIOR_DOCTOR",
                getEmployeeId(),
                getFullName(),
                String.valueOf(getSalary()),
                getSpecialization(),
                getLicenseNumber()
        );
    }
}
