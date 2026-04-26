/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HospitalModel;

/**
 *
 * @author Lenovo
 */
public abstract class MedicalItem {

    private final String itemId;
    private final String name;
    private final String manufactureDate;
    private int stock;

    public MedicalItem(String itemId, String name, String manufactureDate, int stock) {
        this.itemId = itemId;
        this.name = name;
        this.manufactureDate = manufactureDate == null ? "" : manufactureDate;
        this.stock = stock;
    }

    public abstract String getType();

    public abstract String dispenseTo(Patient patient);

    public abstract String toCsv();

    protected String recordDispense(Patient patient, String message) {
        useOne();
        patient.addTreatmentRecord(message);
        return message;
    }

    protected void useOne() {
        if (stock <= 0) {
            throw new IllegalStateException(name + " is out of stock.");
        }
        stock--;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return itemId + " - " + name + " (" + getType() + ")";
    }
}