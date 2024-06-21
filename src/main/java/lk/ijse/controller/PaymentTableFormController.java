package lk.ijse.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.tdm.PaymentTM;

import java.io.IOException;
import java.util.List;

public class PaymentTableFormController {

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<PaymentTM> tblPayments;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM, String> colStudent;

    @FXML
    private TableColumn<PaymentTM, String> colProgram;

    @FXML
    private TableColumn<PaymentTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentTM, String> colDate;

    @FXML
    private TableColumn<PaymentTM, String> colStatus;

    private final ObservableList<PaymentTM> paymentList = FXCollections.observableArrayList();
    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getBO(BOFactory.BOType.PAYMENT);

    @FXML
    public void initialize() {

        colPaymentId.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPaymentId()));
        colStudent.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStudentId()));
        colProgram.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getProgramId()));
        colAmount.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getAmount()));
        colDate.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDate()));
        colStatus.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStatus()));


        colStatus.setCellFactory(TextFieldTableCell.forTableColumn());
        colStatus.setOnEditCommit(event -> {
            PaymentTM payment = event.getRowValue();
            payment.setStatus(event.getNewValue());
            try {
                paymentBO.updateStatus(payment.getPaymentId(), event.getNewValue());
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to update status!").show();
            }
        });


        loadPayments();
    }

    private void loadPayments() {
        paymentList.clear();
        try {
            List<PaymentDTO> dtos = paymentBO.getAllPayments();
            for (PaymentDTO dto : dtos) {
                paymentList.add(new PaymentTM(
                        dto.getPaymentId(),
                        dto.getStudentId(),
                        dto.getProgramId(),
                        dto.getAmount(),
                        dto.getPaymentDate(),
                        dto.getStatus()
                ));
            }
            tblPayments.setItems(paymentList);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load payments!").show();
        }
    }

    @FXML
    public void txtSearchKeyReleased(KeyEvent keyEvent) {
        String searchText = txtSearch.getText().toLowerCase();

        ObservableList<PaymentTM> filteredList = FXCollections.observableArrayList();

        for (PaymentTM payment : paymentList) {
            if (payment.getPaymentId().toLowerCase().contains(searchText) ||
                    payment.getStudentId().toLowerCase().contains(searchText) ||
                    payment.getProgramId().toLowerCase().contains(searchText)) {

                filteredList.add(new PaymentTM(
                        payment.getPaymentId(),
                        payment.getStudentId(),
                        payment.getProgramId(),
                        payment.getAmount(),
                        payment.getDate(),
                        payment.getStatus()
                ));
            }
        }

        tblPayments.setItems(filteredList);
    }


    @FXML
    public void btnPaymentOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/paymentForm.fxml"));;
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Payment Form");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();


            loadPayments();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to open Payment Form!").show();
        }
    }
}
