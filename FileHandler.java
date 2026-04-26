/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HospitalModel;

/**
 *
 * @author Lenovo
 */


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final Path DATA_DIR = Path.of("data");
    private static final Path HOSPITALS_FILE = DATA_DIR.resolve("hospitals.csv");
    private static final Path STAFF_FILE = DATA_DIR.resolve("staff.csv");
    private static final Path PATIENTS_FILE = DATA_DIR.resolve("patients.csv");
    private static final Path ITEMS_FILE = DATA_DIR.resolve("items.csv");
    private static final Path HISTORY_FILE = DATA_DIR.resolve("history.csv");

    private FileHandler() {
    }

    public static void initDataDirectory() throws HospitalException {
        try {
            Files.createDirectories(DATA_DIR);
        } catch (IOException e) {
            throw new HospitalException("Could not create data folder.", e);
        }
    }

    public static void saveAll(CentralHealthAuthority authority) throws HospitalException {
        initDataDirectory();

        List<String> hospitalLines = new ArrayList<>();
        List<String> staffLines = new ArrayList<>();
        List<String> patientLines = new ArrayList<>();
        List<String> itemLines = new ArrayList<>();
        List<String> historyLines = new ArrayList<>();

        for (Hospital hospital : authority.getHospitals()) {
            hospitalLines.add(hospital.toCsv());

            for (Staff staff : hospital.getStaff()) {
                staffLines.add(hospital.getHospitalId() + "," + staff.toCsv());
            }

            for (Patient patient : hospital.getPatients()) {
                patientLines.add(hospital.getHospitalId() + "," + patient.toCsv());
                for (String record : patient.getTreatmentHistory()) {
                    historyLines.add(hospital.getHospitalId() + "," + patient.getPatientId() + "," + clean(record));
                }
            }

            for (MedicalItem item : hospital.getInventory()) {
                itemLines.add(hospital.getHospitalId() + "," + item.toCsv());
            }
        }

        writeFile(HOSPITALS_FILE, hospitalLines);
        writeFile(STAFF_FILE, staffLines);
        writeFile(PATIENTS_FILE, patientLines);
        writeFile(ITEMS_FILE, itemLines);
        writeFile(HISTORY_FILE, historyLines);
    }

    public static CentralHealthAuthority loadAll(String authorityId, String authorityName, double totalBudget)
            throws HospitalException {
        initDataDirectory();
        CentralHealthAuthority authority = new CentralHealthAuthority(authorityId, authorityName, totalBudget);

        for (String line : readFile(HOSPITALS_FILE)) {
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split(",", -1);
            if (parts.length < 5) {
                continue;
            }
            Hospital hospital = new Hospital(parts[0], parts[1], parts[2], parseDouble(parts[3]));
            if (!parts[4].isBlank()) {
                for (String department : parts[4].split("\\|")) {
                    hospital.addDepartment(department);
                }
            }
            authority.addHospital(hospital);
        }

        for (String line : readFile(STAFF_FILE)) {
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split(",", -1);
            if (parts.length < 6) {
                continue;
            }
            Hospital hospital = authority.findHospital(parts[0]);
            if (hospital == null) {
                continue;
            }

            Staff staff = null;
            if ("DOCTOR".equals(parts[1]) && parts.length >= 7) {
                staff = new Doctor(parts[2], parts[3], parseDouble(parts[4]), parts[5], parts[6]);
            } else if ("SENIOR_DOCTOR".equals(parts[1]) && parts.length >= 7) {
                staff = new SeniorDoctor(parts[2], parts[3], parseDouble(parts[4]), parts[5], parts[6]);
            } else if ("ADMIN".equals(parts[1]) && parts.length >= 6) {
                staff = new AdministrativeStaff(parts[2], parts[3], parseDouble(parts[4]), parts[5]);
            }

            if (staff != null) {
                hospital.addStaff(staff);
            }
        }

        for (String line : readFile(PATIENTS_FILE)) {
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split(",", -1);
            if (parts.length < 6) {
                continue;
            }
            Hospital hospital = authority.findHospital(parts[0]);
            if (hospital == null) {
                continue;
            }
            hospital.addPatient(new Patient(parts[1], parts[2], parts[3], parts[4], parts[5]));
        }

        for (String line : readFile(ITEMS_FILE)) {
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split(",", -1);
            if (parts.length < 7) {
                continue;
            }
            Hospital hospital = authority.findHospital(parts[0]);
            if (hospital == null) {
                continue;
            }

            MedicalItem item = null;
            if ("MEDICINE".equals(parts[1]) && parts.length >= 8) {
                item = new Medicine(parts[2], parts[3], parts[4], parseInt(parts[5]), parts[6], parts[7]);
            } else if ("SURGICAL".equals(parts[1]) && parts.length >= 8) {
                item = new Surgicalequipment(parts[2], parts[3], parts[4], parseInt(parts[5]), parts[6], parts[7]);
            } else if ("DEVICE".equals(parts[1]) && parts.length >= 8) {
                item = new Diagnosticdevice(parts[2], parts[3], parts[4], parseInt(parts[5]), parts[6], parts[7]);
            }

            if (item != null) {
                hospital.addItem(item);
            }
        }

        for (String line : readFile(HISTORY_FILE)) {
            if (line.isBlank()) {
                continue;
            }
            String[] parts = line.split(",", 3);
            if (parts.length < 3) {
                continue;
            }
            Hospital hospital = authority.findHospital(parts[0]);
            if (hospital == null) {
                continue;
            }
            Patient patient = hospital.findPatient(parts[1]);
            if (patient != null) {
                patient.addTreatmentRecord(parts[2]);
            }
        }

        return authority;
    }

    public static void exportReport(CentralHealthAuthority authority, String fileName) throws HospitalException {
        initDataDirectory();
        List<String> lines = new ArrayList<>();
        lines.add("Hospital Management Report");
        lines.add("Authority: " + authority.getAuthorityName());
        lines.add("Total budget: " + authority.getTotalBudget());
        lines.add("Remaining budget: " + authority.getRemainingBudget());
        lines.add("");
        for (Hospital hospital : authority.getHospitals()) {
            lines.add(hospital.getName() + " - " + hospital.getLocation());
            lines.add("Budget: " + hospital.getBudget());
            lines.add("Staff: " + hospital.getStaff().size());
            lines.add("Patients: " + hospital.getPatients().size());
            lines.add("Items: " + hospital.getInventory().size());
            lines.add("");
        }
        writeFile(DATA_DIR.resolve(fileName), lines);
    }

    private static void writeFile(Path path, List<String> lines) throws HospitalException {
        try {
            Files.write(path, lines);
        } catch (IOException e) {
            throw new HospitalException("Could not write file: " + path.getFileName(), e);
        }
    }

    private static List<String> readFile(Path path) throws HospitalException {
        try {
            return Files.exists(path) ? Files.readAllLines(path) : List.of();
        } catch (IOException e) {
            throw new HospitalException("Could not read file: " + path.getFileName(), e);
        }
    }

    private static int parseInt(String value) {
        return Integer.parseInt(value);
    }

    private static double parseDouble(String value) {
        return Double.parseDouble(value);
    }

    private static String clean(String value) {
        return value.replace(",", ";");
    }
}
