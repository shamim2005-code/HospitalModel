/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HospitalModel;

/**
 *
 * @author Lenovo
 */


public class Surgicalequipment extends MedicalItem {

    private final String sterilizationMethod;
    private final String usageNote;

    public Surgicalequipment(String itemId, String name, String manufactureDate, int stock,
                             String sterilizationMethod, String usageNote) {
        super(itemId, name, manufactureDate, stock);
        this.sterilizationMethod = sterilizationMethod;
        this.usageNote = usageNote;
    }

    public String getSterilizationMethod() {
        return sterilizationMethod;
    }

    public String getUsageNote() {
        return usageNote;
    }

    @Override
    public String getType() {
        return "Surgical Equipment";
    }

    @Override
    public String dispenseTo(Patient patient) {
        return recordDispense(patient, "Surgical equipment used: " + getName() + " for " + patient.getFullName());
    }

    @Override
    public String toCsv() {
        return String.join(",",
                "SURGICAL",
                getItemId(),
                getName(),
                getManufactureDate(),
                String.valueOf(getStock()),
                sterilizationMethod,
                usageNote
        );
    }
}
