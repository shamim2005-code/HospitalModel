/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HospitalModel;

/**
 *
 * @author Lenovo
 */


public class Medicine extends MedicalItem {

    private final String dosageForm;
    private final String expiryDate;

    public Medicine(String itemId, String name, String manufactureDate, int stock, String dosageForm, String expiryDate) {
        super(itemId, name, manufactureDate, stock);
        this.dosageForm = dosageForm;
        this.expiryDate = expiryDate;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    @Override
    public String getType() {
        return "Medicine";
    }

    @Override
    public String dispenseTo(Patient patient) {
        return recordDispense(patient, "Medicine given: " + getName() + " to " + patient.getFullName());
    }

    @Override
    public String toCsv() {
        return String.join(",",
                "MEDICINE",
                getItemId(),
                getName(),
                getManufactureDate(),
                String.valueOf(getStock()),
                dosageForm,
                expiryDate
        );
    }
}
