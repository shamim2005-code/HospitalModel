/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HospitalModel;

/**
 *
 * @author Lenovo
 */
    public class Diagnosticdevice extends MedicalItem {

    private final String deviceType;
    private final String calibrationDate;

    public Diagnosticdevice(String itemId, String name, String manufactureDate, int stock,
                            String deviceType, String calibrationDate) {
        super(itemId, name, manufactureDate, stock);
        this.deviceType = deviceType;
        this.calibrationDate = calibrationDate;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getCalibrationDate() {
        return calibrationDate;
    }

    @Override
    public String getType() {
        return "Diagnostic Device";
    }

    @Override
    public String dispenseTo(Patient patient) {
        return recordDispense(patient, "Diagnostic device used: " + getName() + " for " + patient.getFullName());
    }

    @Override
    public String toCsv() {
        return String.join(",",
                "DEVICE",
                getItemId(),
                getName(),
                getManufactureDate(),
                String.valueOf(getStock()),
                deviceType,
                calibrationDate
        );
    }
}


