/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HospitalModel;

/**
 *
 * @author Lenovo
 */


import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MainUI {

    private final HospitalService service;
    private final Label headerLabel = new Label();
    private final TextArea hospitalArea = createOutputArea();
    private final TextArea staffArea = createOutputArea();
    private final TextArea patientArea = createOutputArea();
    private final TextArea itemArea = createOutputArea();

    public MainUI(HospitalService service) {
        this.service = service;
    }

    public void show(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setTop(headerLabel);
        root.setCenter(buildTabs());

        refreshAll();

        stage.setTitle("Hospital Management System");
        stage.setScene(new Scene(root, 950, 650));
        stage.show();
    }

    private TabPane buildTabs() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().add(createHospitalTab());
        tabPane.getTabs().add(createStaffTab());
        tabPane.getTabs().add(createPatientTab());
        tabPane.getTabs().add(createItemTab());
        tabPane.getTabs().add(createActionTab());
        return tabPane;
    }

    private Tab createHospitalTab() {
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField locationField = new TextField();
        TextField departmentField = new TextField();
        Label messageLabel = new Label();

        GridPane form = createForm();
        addRow(form, 0, "Hospital ID", idField);
        addRow(form, 1, "Name", nameField);
        addRow(form, 2, "Location", locationField);
        addRow(form, 3, "Departments", departmentField);

        Button addButton = new Button("Add Hospital");
        addButton.setOnAction(event -> {
            try {
                service.addHospital(idField.getText(), nameField.getText(), locationField.getText(), departmentField.getText());
                messageLabel.setText("Hospital added.");
                clear(idField, nameField, locationField, departmentField);
                refreshAll();
            } catch (HospitalException e) {
                messageLabel.setText(e.getMessage());
            }
        });

        VBox box = new VBox(10, form, addButton, messageLabel, hospitalArea);
        box.setPadding(new Insets(10));
        return new Tab("Hospitals", box);
    }

    private Tab createStaffTab() {
        ComboBox<String> roleBox = new ComboBox<>();
        roleBox.getItems().addAll("Doctor", "Senior Doctor", "Administrative Staff");
        roleBox.setValue("Doctor");

        TextField hospitalField = new TextField();
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField salaryField = new TextField();
        TextField extraOneField = new TextField();
        TextField extraTwoField = new TextField();
        Label messageLabel = new Label();

        roleBox.setOnAction(event -> {
            if ("Administrative Staff".equals(roleBox.getValue())) {
                extraOneField.setPromptText("Department");
                extraTwoField.clear();
                extraTwoField.setDisable(true);
            } else {
                extraOneField.setPromptText("Specialization");
                extraTwoField.setPromptText("License number");
                extraTwoField.setDisable(false);
            }
        });
        extraOneField.setPromptText("Specialization");
        extraTwoField.setPromptText("License number");

        GridPane form = createForm();
        addRow(form, 0, "Hospital ID", hospitalField);
        addRow(form, 1, "Role", roleBox);
        addRow(form, 2, "Employee ID", idField);
        addRow(form, 3, "Name", nameField);
        addRow(form, 4, "Salary", salaryField);
        addRow(form, 5, "Extra 1", extraOneField);
        addRow(form, 6, "Extra 2", extraTwoField);

        Button addButton = new Button("Add Staff");
        addButton.setOnAction(event -> {
            try {
                service.hireStaff(
                        hospitalField.getText(),
                        roleBox.getValue(),
                        idField.getText(),
                        nameField.getText(),
                        Double.parseDouble(salaryField.getText()),
                        extraOneField.getText(),
                        extraTwoField.getText()
                );
                messageLabel.setText("Staff added.");
                clear(hospitalField, idField, nameField, salaryField, extraOneField, extraTwoField);
                refreshAll();
            } catch (NumberFormatException e) {
                messageLabel.setText("Salary must be a number.");
            } catch (HospitalException e) {
                messageLabel.setText(e.getMessage());
            }
        });

        VBox box = new VBox(10, form, addButton, messageLabel, staffArea);
        box.setPadding(new Insets(10));
        return new Tab("Staff", box);
    }

    private Tab createPatientTab() {
        TextField hospitalField = new TextField();
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField contactField = new TextField();
        TextField planField = new TextField();
        TextField symptomsField = new TextField();
        TextField historyHospitalField = new TextField();
        TextField historyPatientField = new TextField();
        TextArea historyArea = createOutputArea();
        Label messageLabel = new Label();

        GridPane form = createForm();
        addRow(form, 0, "Hospital ID", hospitalField);
        addRow(form, 1, "Patient ID", idField);
        addRow(form, 2, "Name", nameField);
        addRow(form, 3, "Contact", contactField);
        addRow(form, 4, "Plan", planField);
        addRow(form, 5, "Symptoms", symptomsField);

        Button addButton = new Button("Register Patient");
        addButton.setOnAction(event -> {
            try {
                service.registerPatient(
                        hospitalField.getText(),
                        idField.getText(),
                        nameField.getText(),
                        contactField.getText(),
                        planField.getText(),
                        symptomsField.getText()
                );
                messageLabel.setText("Patient registered.");
                clear(hospitalField, idField, nameField, contactField, planField, symptomsField);
                refreshAll();
            } catch (HospitalException e) {
                messageLabel.setText(e.getMessage());
            }
        });

        GridPane historyForm = createForm();
        addRow(historyForm, 0, "History Hospital ID", historyHospitalField);
        addRow(historyForm, 1, "History Patient ID", historyPatientField);

        Button historyButton = new Button("Load History");
        historyButton.setOnAction(event -> {
            try {
                List<String> history = service.getPatientHistory(historyHospitalField.getText(), historyPatientField.getText());
                historyArea.setText(history.isEmpty() ? "No history found." : String.join(System.lineSeparator(), history));
            } catch (HospitalException e) {
                historyArea.setText(e.getMessage());
            }
        });

        VBox box = new VBox(10, form, addButton, messageLabel, patientArea, historyForm, historyButton, historyArea);
        box.setPadding(new Insets(10));
        return new Tab("Patients", box);
    }

    private Tab createItemTab() {
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Medicine", "Surgical Equipment", "Diagnostic Device");
        typeBox.setValue("Medicine");

        TextField hospitalField = new TextField();
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField dateField = new TextField();
        TextField stockField = new TextField();
        TextField extraOneField = new TextField();
        TextField extraTwoField = new TextField();
        Label messageLabel = new Label();

        typeBox.setOnAction(event -> {
            String type = typeBox.getValue();
            if ("Surgical Equipment".equals(type)) {
                extraOneField.setPromptText("Sterilization method");
                extraTwoField.setPromptText("Usage note");
            } else if ("Diagnostic Device".equals(type)) {
                extraOneField.setPromptText("Device type");
                extraTwoField.setPromptText("Calibration date");
            } else {
                extraOneField.setPromptText("Dosage form");
                extraTwoField.setPromptText("Expiry date");
            }
        });
        extraOneField.setPromptText("Dosage form");
        extraTwoField.setPromptText("Expiry date");

        GridPane form = createForm();
        addRow(form, 0, "Hospital ID", hospitalField);
        addRow(form, 1, "Type", typeBox);
        addRow(form, 2, "Item ID", idField);
        addRow(form, 3, "Name", nameField);
        addRow(form, 4, "Manufacture Date", dateField);
        addRow(form, 5, "Stock", stockField);
        addRow(form, 6, "Extra 1", extraOneField);
        addRow(form, 7, "Extra 2", extraTwoField);

        Button addButton = new Button("Add Item");
        addButton.setOnAction(event -> {
            try {
                service.addMedicalItem(
                        hospitalField.getText(),
                        typeBox.getValue(),
                        idField.getText(),
                        nameField.getText(),
                        dateField.getText(),
                        Integer.parseInt(stockField.getText()),
                        extraOneField.getText(),
                        extraTwoField.getText()
                );
                messageLabel.setText("Item added.");
                clear(hospitalField, idField, nameField, dateField, stockField, extraOneField, extraTwoField);
                refreshAll();
            } catch (NumberFormatException e) {
                messageLabel.setText("Stock must be a whole number.");
            } catch (HospitalException e) {
                messageLabel.setText(e.getMessage());
            }
        });

        VBox box = new VBox(10, form, addButton, messageLabel, itemArea);
        box.setPadding(new Insets(10));
        return new Tab("Inventory", box);
    }

    private Tab createActionTab() {
        TextField budgetHospitalField = new TextField();
        TextField budgetAmountField = new TextField();
        TextField dispenseHospitalField = new TextField();
        TextField dispenseItemField = new TextField();
        TextField dispensePatientField = new TextField();
        TextArea resultArea = createOutputArea();
        Label messageLabel = new Label();

        GridPane budgetForm = createForm();
        addRow(budgetForm, 0, "Hospital ID", budgetHospitalField);
        addRow(budgetForm, 1, "Budget Amount", budgetAmountField);

        Button budgetButton = new Button("Allocate Budget");
        budgetButton.setOnAction(event -> {
            try {
                messageLabel.setText(service.allocateBudget(
                        budgetHospitalField.getText(),
                        Double.parseDouble(budgetAmountField.getText())
                ));
                clear(budgetHospitalField, budgetAmountField);
                refreshAll();
            } catch (NumberFormatException e) {
                messageLabel.setText("Budget must be a number.");
            } catch (HospitalException e) {
                messageLabel.setText(e.getMessage());
            }
        });

        GridPane dispenseForm = createForm();
        addRow(dispenseForm, 0, "Hospital ID", dispenseHospitalField);
        addRow(dispenseForm, 1, "Item ID", dispenseItemField);
        addRow(dispenseForm, 2, "Patient ID", dispensePatientField);

        Button dispenseButton = new Button("Dispense Item");
        dispenseButton.setOnAction(event -> {
            try {
                resultArea.setText(service.dispenseItem(
                        dispenseHospitalField.getText(),
                        dispenseItemField.getText(),
                        dispensePatientField.getText()
                ));
                clear(dispenseHospitalField, dispenseItemField, dispensePatientField);
                refreshAll();
            } catch (HospitalException e) {
                resultArea.setText(e.getMessage());
            }
        });

        Button complianceButton = new Button("Show Compliance");
        complianceButton.setOnAction(event -> resultArea.setText(service.runComplianceCheck()));

        Button saveButton = new Button("Save Data");
        saveButton.setOnAction(event -> {
            try {
                service.saveAllData();
                messageLabel.setText("Data saved.");
            } catch (HospitalException e) {
                messageLabel.setText(e.getMessage());
            }
        });

        Button reportButton = new Button("Export Report");
        reportButton.setOnAction(event -> {
            try {
                service.exportReport("report.txt");
                messageLabel.setText("Report exported to data/report.txt");
            } catch (HospitalException e) {
                messageLabel.setText(e.getMessage());
            }
        });

        HBox buttons = new HBox(10, budgetButton, dispenseButton, complianceButton, saveButton, reportButton);
        VBox box = new VBox(10, budgetForm, dispenseForm, buttons, messageLabel, resultArea);
        box.setPadding(new Insets(10));
        return new Tab("Actions", box);
    }

    private GridPane createForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        return grid;
    }

    private void addRow(GridPane grid, int row, String labelText, Node node) {
        grid.add(new Label(labelText + ":"), 0, row);
        grid.add(node, 1, row);
    }

    private TextArea createOutputArea() {
        TextArea area = new TextArea();
        area.setEditable(false);
        area.setPrefRowCount(12);
        return area;
    }

    private void refreshAll() {
        headerLabel.setText(service.getAuthority().getAuthorityName()
                + " | Remaining Budget: " + service.getAuthority().getRemainingBudget());
        hospitalArea.setText(service.buildHospitalSummary());
        staffArea.setText(service.buildStaffSummary());
        patientArea.setText(service.buildPatientSummary());
        itemArea.setText(service.buildItemSummary());
    }

    private void clear(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
}
