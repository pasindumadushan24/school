package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainForm2Controller {

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnProgram;

    @FXML
    private JFXButton btnSetting;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private JFXButton btnView;

    @FXML
    private JFXButton btnInstructor;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnLessons;

    @FXML
    private AnchorPane changeForm;

    @FXML
    private AnchorPane dashboardFrom;

    public void initialize() {
        try {
            // Default load dashboard
            changeForm.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/dashboard.fxml")));

            // Add zoom effect to all buttons
            addZoomEffect(btnDashboard);


            addZoomEffect(btnStudent);


            addZoomEffect(btnPayment);
            addZoomEffect(btnLessons);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        loadForm("/dashboard.fxml", btnDashboard);
    }

    @FXML
    void btnProgramOnAction(ActionEvent event) {
        loadForm("/programForm.fxml", btnProgram);
    }

    @FXML
    void btnStudentOnAction(ActionEvent event) {
        loadForm("/studentForm.fxml", btnStudent);
    }

    @FXML
    void btnInstructorOnAction(ActionEvent event) {
        loadForm("/instructorForm.fxml", btnInstructor);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        loadForm("/paymentTableForm.fxml", btnPayment);
    }

    @FXML
    void btnLessonsOnAction(ActionEvent event) {
        loadForm("/lessonForm.fxml", btnLessons);
    }

    @FXML
    void btnViewOnAction(ActionEvent event) {
        loadForm("/viewAllForm.fxml", btnView);
    }

    @FXML
    void btnSettingOnAction(ActionEvent event) {
        loadForm("/settingForm.fxml", btnSetting);
    }

    @FXML
    void logOutAction(MouseEvent event) {
        try {
            Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/loginForm.fxml")));
            Stage stage = (Stage) dashboardFrom.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Utility method to load forms */
    private void loadForm(String fxmlPath, JFXButton activeButton) {
        try {
            changeForm.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource(fxmlPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Add zoom effect to JFXButton */
    private void addZoomEffect(JFXButton button) {
        button.setOnMouseEntered(event -> {
            button.setScaleX(1.1);
            button.setScaleY(1.1);
        });

        button.setOnMouseExited(event -> {
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });
    }
}
