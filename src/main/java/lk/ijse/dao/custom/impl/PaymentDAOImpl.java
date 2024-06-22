package lk.ijse.dao.custom.impl;

import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.db.FactoryConfiguration;
import lk.ijse.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {



    @Override
    public boolean save(Payment payment) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.saveOrUpdate(payment); // Insert  Update automa karanawa

        transaction.commit();
        session.close();
        return true;
    }



    @Override
    public Payment findByStudentAndProgram(String studentId, String programId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Payment payment = session.createQuery(
                        "FROM Payment p WHERE p.student.studentId = :studentId AND p.program.programId = :programId",
                        Payment.class
                ).setParameter("studentId", studentId)
                .setParameter("programId", programId)
                .uniqueResult();
        session.close();
        return payment;
    }







    @Override
    public double getTotalPaidAmount(String studentId, String programId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Double total = session.createQuery(
                        "SELECT SUM(p.amount) FROM Payment p WHERE p.student.studentId = :studentId AND p.program.programId = :programId",
                        Double.class
                ).setParameter("studentId", studentId)
                .setParameter("programId", programId)
                .uniqueResult();

        transaction.commit();
        session.close();

        return total != null ? total : 0.0;
    }





    @Override
    public boolean update(Payment payment) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(payment);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Payment payment = session.get(Payment.class, id);
        if (payment != null) session.delete(payment);
        transaction.commit();
        session.close();
        return payment != null;
    }

    @Override
    public Payment findById(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Payment payment = session.get(Payment.class, id);
        session.close();
        return payment;
    }

    @Override
    public List<Payment> findAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<Payment> list = session.createQuery("from Payment", Payment.class).list();
        session.close();
        return list;
    }

    @Override
    public String generatePaymentId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<String> query = session.createQuery("SELECT p.paymentId FROM Payment p ORDER BY p.paymentId DESC", String.class);
        query.setMaxResults(1);
        String lastId = query.uniqueResult();
        session.close();

        if (lastId == null) {
            return "PAY001";
        } else {
            int num = Integer.parseInt(lastId.replace("PAY", "")) + 1;
            return String.format("PAY%03d", num);
        }
    }
}
