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

public class HospitalService {

    private final CentralHealthAuthority authority;

    public HospitalService(CentralHealthAuthority authority) {
        this.authority = authority;
    }

    public CentralHealthAuthority getAuthority() {
        return authority;
    }

    public List<Hospital> getAllHospitals() {
        return authority.getHospitals();
    }

    public Hospital addHospital(String hospitalId, String name, String location, String departments) throws HospitalException {
        requireText(hospitalId, "Hospital ID");
        requireText(name, "Hospital name");
        requireText(location, "Location");
        if (authority.findHospital(hospitalId) != null) {
            throw new HospitalException("Hospital ID already exists.");
        }

        Hospital hospital = new Hospital(hospitalId, name, location);
        if (departments != null && !departments.isBlank()) {
            for (String department : departments.split(",")) {
                hospital.addDepartment(department);
            }
        }
        authority.addHospital(hospital);
        saveAllData();
        return hospital;
    }

    public Staff hireStaff(String hospitalId, String role, String employeeId, String fullName,
                           double salary, String extraOne, String extraTwo) throws HospitalException {
        Hospital hospital = requireHospital(hospitalId);
        requireText(employeeId, "Employee ID");
        requireText(fullName, "Employee name");
        if (salary < 0) {
            throw new HospitalException("Salary cannot be negative.");
        }
        if (hospital.findStaff(employeeId) != null) {
            throw new HospitalException("Employee ID already exists in this hospital.");
        }

        Staff staff;
        if (null == role) {
            requireText(extraOne, "Specialization");
            requireText(extraTwo, "License number");
            staff = new Doctor(employeeId, fullName, salary, extraOne, extraTwo);
        } else switch (role) {
            case "Senior Doctor":
                requireText(extraOne, "Specialization");
                requireText(extraTwo, "License number");
                staff = new SeniorDoctor(employeeId, fullName, salary, extraOne, extraTwo);
                break;
            case "Administrative Staff":
                requireText(extraOne, "Department");
                staff = new AdministrativeStaff(employeeId, fullName, salary, extraOne);
                break;
            default:
                requireText(extraOne, "Specialization");
                requireText(extraTwo, "License number");
                staff = new Doctor(employeeId, fullName, salary, extraOne, extraTwo);
                break;
        }

        hospital.addStaff(staff);
        saveAllData();
        return staff;
    }

    public Patient registerPatient(String hospitalId, String patientId, String fullName,
                                   String contact, String plan, String symptoms) throws HospitalException {
        Hospital hospital = requireHospital(hospitalId);
        requireText(patientId, "Patient ID");
        requireText(fullName, "Patient name");
        if (hospital.findPatient(patientId) != null) {
            throw new HospitalException("Patient ID already exists in this hospital.");
        }

        Patient patient = new Patient(patientId, fullName, contact, plan, symptoms);
        hospital.addPatient(patient);
        saveAllData();
        return patient;
    }

    public MedicalItem addMedicalItem(String hospitalId, String type, String itemId, String name,
                                      String manufactureDate, int stock, String extraOne, String extraTwo)
            throws HospitalException {
        Hospital hospital = requireHospital(hospitalId);
        requireText(itemId, "Item ID");
        requireText(name, "Item name");
        if (stock < 0) {
            throw new HospitalException("Stock cannot be negative.");
        }
        if (hospital.findItem(itemId) != null) {
            throw new HospitalException("Item ID already exists in this hospital.");
        }

        MedicalItem item;
        if (null == type) {
            requireText(extraOne, "Dosage form");
            requireText(extraTwo, "Expiry date");
            item = new Medicine(itemId, name, manufactureDate, stock, extraOne, extraTwo);
        } else switch (type) {
            case "Surgical Equipment":
                requireText(extraOne, "Sterilization method");
                requireText(extraTwo, "Usage note");
                item = new Surgicalequipment(itemId, name, manufactureDate, stock, extraOne, extraTwo);
                break;
            case "Diagnostic Device":
                requireText(extraOne, "Device type");
                requireText(extraTwo, "Calibration date");
                item = new Diagnosticdevice(itemId, name, manufactureDate, stock, extraOne, extraTwo);
                break;
            default:
                requireText(extraOne, "Dosage form");
                requireText(extraTwo, "Expiry date");
                item = new Medicine(itemId, name, manufactureDate, stock, extraOne, extraTwo);
                break;
        }

        hospital.addItem(item);
        saveAllData();
        return item;
    }

    public String dispenseItem(String hospitalId, String itemId, String patientId) throws HospitalException {
        Hospital hospital = requireHospital(hospitalId);
        MedicalItem item = hospital.findItem(itemId);
        Patient patient = hospital.findPatient(patientId);

        if (item == null) {
            throw new HospitalException("Item not found.");
        }
        if (patient == null) {
            throw new HospitalException("Patient not found.");
        }

        try {
            String result = item.dispenseTo(patient);
            saveAllData();
            return result;
        } catch (IllegalStateException e) {
            throw new HospitalException(e.getMessage(), e);
        }
    }

    public String allocateBudget(String hospitalId, double amount) throws HospitalException {
        Hospital hospital = requireHospital(hospitalId);
        String result = authority.allocateBudget(hospital, amount);
        saveAllData();
        return result;
    }

    public List<String> getPatientHistory(String hospitalId, String patientId) throws HospitalException {
        Patient patient = requireHospital(hospitalId).findPatient(patientId);
        if (patient == null) {
            throw new HospitalException("Patient not found.");
        }
        return patient.getTreatmentHistory();
    }

    public String runComplianceCheck() {
        return authority.generateComplianceReport();
    }

    public void saveAllData() throws HospitalException {
        FileHandler.saveAll(authority);
    }

    public void exportReport(String fileName) throws HospitalException {
        FileHandler.exportReport(authority, fileName);
    }

    public String buildHospitalSummary() {
        if (authority.getHospitals().isEmpty()) {
            return "No hospitals added yet.";
        }
        StringBuilder builder = new StringBuilder();
        for (Hospital hospital : authority.getHospitals()) {
            builder.append(hospital.getHospitalId())
                    .append(" | ")
                    .append(hospital.getName())
                    .append(" | ")
                    .append(hospital.getLocation())
                    .append(" | Staff: ")
                    .append(hospital.getStaff().size())
                    .append(" | Patients: ")
                    .append(hospital.getPatients().size())
                    .append(" | Items: ")
                    .append(hospital.getInventory().size())
                    .append(System.lineSeparator());
        }
        return builder.toString().trim();
    }

    public String buildStaffSummary() {
        List<String> lines = new ArrayList<>();
        for (Hospital hospital : authority.getHospitals()) {
            for (Staff staff : hospital.getStaff()) {
                lines.add(hospital.getHospitalId() + " | " + staff.getEmployeeId() + " | "
                        + staff.getFullName() + " | " + staff.getRole());
            }
        }
        return lines.isEmpty() ? "No staff added yet." : String.join(System.lineSeparator(), lines);
    }

    public String buildPatientSummary() {
        List<String> lines = new ArrayList<>();
        for (Hospital hospital : authority.getHospitals()) {
            for (Patient patient : hospital.getPatients()) {
                lines.add(hospital.getHospitalId() + " | " + patient.getPatientId() + " | "
                        + patient.getFullName() + " | " + patient.getInsurancePlan());
            }
        }
        return lines.isEmpty() ? "No patients registered yet." : String.join(System.lineSeparator(), lines);
    }

    public String buildItemSummary() {
        List<String> lines = new ArrayList<>();
        for (Hospital hospital : authority.getHospitals()) {
            for (MedicalItem item : hospital.getInventory()) {
                lines.add(hospital.getHospitalId() + " | " + item.getItemId() + " | "
                        + item.getName() + " | " + item.getType() + " | Stock: " + item.getStock());
            }
        }
        return lines.isEmpty() ? "No medical items added yet." : String.join(System.lineSeparator(), lines);
    }

    private Hospital requireHospital(String hospitalId) throws HospitalException {
        Hospital hospital = authority.findHospital(hospitalId);
        if (hospital == null) {
            throw new HospitalException("Hospital not found.");
        }
        return hospital;
    }

    private void requireText(String value, String fieldName) throws HospitalException {
        if (value == null || value.isBlank()) {
            throw new HospitalException(fieldName + " is required.");
        }
    }
}
