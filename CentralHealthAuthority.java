package HospitalModel;

import java.util.ArrayList;
import java.util.List;

public class CentralHealthAuthority {

    private final String authorityId;
    private final String authorityName;
    private final double totalBudget;
    private double allocatedBudget;
    private final List<Hospital> hospitals;

    public CentralHealthAuthority(String authorityId, String authorityName, double totalBudget) {
        this.authorityId = authorityId;
        this.authorityName = authorityName;
        this.totalBudget = totalBudget;
        this.hospitals = new ArrayList<>();
    }

    public void addHospital(Hospital hospital) {
        hospitals.add(hospital);
    }

    public Hospital findHospital(String hospitalId) {
        for (Hospital hospital : hospitals) {
            if (hospital.getHospitalId().equalsIgnoreCase(hospitalId)) {
                return hospital;
            }
        }
        return null;
    }

    public List<Hospital> getHospitals() {
        return List.copyOf(hospitals);
    }

    public String allocateBudget(Hospital hospital, double amount) throws HospitalException {
        if (amount <= 0) {
            throw new HospitalException("Budget amount must be positive.");
        }
        if (allocatedBudget + amount > totalBudget) {
            throw new HospitalException("Not enough authority budget remaining.");
        }
        allocatedBudget += amount;
        hospital.addBudget(amount);
        return "Budget allocated to " + hospital.getName() + ": " + amount;
    }

    public String generateComplianceReport() {
        StringBuilder builder = new StringBuilder("Compliance Report\n");
        for (Hospital hospital : hospitals) {
            boolean ok = !hospital.getStaff().isEmpty()
                    && !hospital.getPatients().isEmpty()
                    && !hospital.getInventory().isEmpty();
            builder.append(hospital.getName())
                    .append(" - ")
                    .append(ok ? "Ready" : "Needs data")
                    .append(System.lineSeparator());
        }
        return builder.toString().trim();
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public double getAllocatedBudget() {
        return allocatedBudget;
    }

    public double getRemainingBudget() {
        return totalBudget - allocatedBudget;
    }
}
