/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HospitalModel;

/**
 *
 * @author Lenovo
 */


public class Doctor extends Staff {

    private final String specialization;
    private final String licenseNumber;

    public Doctor(String employeeId, String fullName, double salary, String specialization, String licenseNumber) {
        super(employeeId, fullName, salary);
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    @Override
    public String getRole() {
        return "Doctor";
    }

    @Override
    public String toCsv() {
        return String.join(",",
                "DOCTOR",
                getEmployeeId(),
                getFullName(),
                String.valueOf(getSalary()),
                specialization,
                licenseNumber
        );
    }
}
