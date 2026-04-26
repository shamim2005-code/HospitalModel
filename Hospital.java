/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HospitalModel;

/**
 *
 * @author Lenovo
 */


import java.util.ArrayList;
import java.util.List;

public class Hospital {

    private final String hospitalId;
    private final String name;
    private final String location;
    private double budget;
    private final List<String> departments;
    private final List<Staff> staff;
    private final List<Patient> patients;
    private final List<MedicalItem> inventory;

    public Hospital(String hospitalId, String name, String location) {
        this(hospitalId, name, location, 0);
    }

    public Hospital(String hospitalId, String name, String location, double budget) {
        this.hospitalId = hospitalId;
        this.name = name;
        this.location = location;
        this.budget = budget;
        this.departments = new ArrayList<>();
        this.staff = new ArrayList<>();
        this.patients = new ArrayList<>();
        this.inventory = new ArrayList<>();
    }

    public void addDepartment(String department) {
        if (department != null && !department.isBlank()) {
            departments.add(department.trim());
        }
    }

    public void addStaff(Staff member) {
        staff.add(member);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void addItem(MedicalItem item) {
        inventory.add(item);
    }

    public void addBudget(double amount) {
        budget += amount;
    }

    public Staff findStaff(String employeeId) {
        for (Staff member : staff) {
            if (member.getEmployeeId().equalsIgnoreCase(employeeId)) {
                return member;
            }
        }
        return null;
    }

    public Patient findPatient(String patientId) {
        for (Patient patient : patients) {
            if (patient.getPatientId().equalsIgnoreCase(patientId)) {
                return patient;
            }
        }
        return null;
    }

    public MedicalItem findItem(String itemId) {
        for (MedicalItem item : inventory) {
            if (item.getItemId().equalsIgnoreCase(itemId)) {
                return item;
            }
        }
        return null;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getBudget() {
        return budget;
    }

    public List<String> getDepartments() {
        return List.copyOf(departments);
    }

    public List<Staff> getStaff() {
        return List.copyOf(staff);
    }

    public List<Patient> getPatients() {
        return List.copyOf(patients);
    }

    public List<MedicalItem> getInventory() {
        return List.copyOf(inventory);
    }

    public String toCsv() {
        return String.join(",",
                hospitalId,
                name,
                location,
                String.valueOf(budget),
                String.join("|", departments)
        );
    }

    @Override
    public String toString() {
        return hospitalId + " - " + name + " (" + location + ")";
    }
}
