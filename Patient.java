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

public class Patient {

    private final String patientId;
    private final String fullName;
    private final String contact;
    private final String insurancePlan;
    private final String symptoms;
    private final List<String> treatmentHistory;

    public Patient(String patientId, String fullName, String contact, String insurancePlan, String symptoms) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.contact = contact == null ? "" : contact;
        this.insurancePlan = insurancePlan == null ? "" : insurancePlan;
        this.symptoms = symptoms == null ? "" : symptoms;
        this.treatmentHistory = new ArrayList<>();
    }

    public void addTreatmentRecord(String record) {
        if (record != null && !record.isBlank()) {
            treatmentHistory.add(record);
        }
    }

    public String getPatientId() {
        return patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContact() {
        return contact;
    }

    public String getInsurancePlan() {
        return insurancePlan;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public List<String> getTreatmentHistory() {
        return List.copyOf(treatmentHistory);
    }

    public String toCsv() {
        return String.join(",",
                patientId,
                fullName,
                contact,
                insurancePlan,
                symptoms
        );
    }

    @Override
    public String toString() {
        return patientId + " - " + fullName;
    }
}
