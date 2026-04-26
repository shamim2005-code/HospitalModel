/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HospitalModel;

/**
 *
 * @author Lenovo
 */


import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        CentralHealthAuthority authority;
        try {
            authority = FileHandler.loadAll("CHA-01", "Dhaka Health Authority", 1_000_000);
        } catch (HospitalException e) {
            authority = new CentralHealthAuthority("CHA-01", "Dhaka Health Authority", 1_000_000);
        }

        HospitalService service = new HospitalService(authority);
        new MainUI(service).show(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

