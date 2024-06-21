package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.dto.courseDTO;

import java.util.List;

public interface PaymentBO extends SuperBO {
    boolean savePayment(PaymentDTO dto);
    boolean updatePayment(PaymentDTO dto);
    boolean deletePayment(String id);
    PaymentDTO getPayment(String id);
    List<PaymentDTO> getAllPayments();
    String generatePaymentId();

    double getTotalPaidAmount(String studentId, String programId);


    List<StudentDTO> getAllStudents();
    List<courseDTO> getAllPrograms();
    boolean updateStatus(String paymentId, String newStatus) throws Exception;
}
