import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class FeeManagementSystemGUI extends JFrame implements ActionListener {
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private JTextField studentIdField, studentNameField, feeAmountField, paymentField;
    private JTextArea outputArea;

    public FeeManagementSystemGUI() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fees_db?characterEncoding=utf8", "root", "");
            st = con.createStatement();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        setTitle("Fee Management System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setBounds(20, 20, 80, 25);
        add(studentIdLabel);

        studentIdField = new JTextField();
        studentIdField.setBounds(120, 20, 200, 25);
        add(studentIdField);

        JLabel studentNameLabel = new JLabel("Student Name:");
        studentNameLabel.setBounds(20, 50, 100, 25);
        add(studentNameLabel);

        studentNameField = new JTextField();
        studentNameField.setBounds(120, 50, 200, 25);
        add(studentNameField);

        JLabel feeAmountLabel = new JLabel("Fee Amount:");
        feeAmountLabel.setBounds(20, 80, 80, 25);
        add(feeAmountLabel);

        feeAmountField = new JTextField();
        feeAmountField.setBounds(120, 80, 200, 25);
        add(feeAmountField);

        JButton addButton = new JButton("Add Fee Record");
        addButton.setBounds(20, 110, 150, 25);
        addButton.addActionListener(this);
        add(addButton);

        JLabel paymentLabel = new JLabel("Payment Amount:");
        paymentLabel.setBounds(20, 140, 120, 25);
        add(paymentLabel);

        paymentField = new JTextField();
        paymentField.setBounds(150, 140, 170, 25);
        add(paymentField);

        JButton payButton = new JButton("Make Payment");
        payButton.setBounds(20, 170, 150, 25);
        payButton.addActionListener(this);
        add(payButton);

        outputArea = new JTextArea();
        outputArea.setBounds(20, 200, 350, 80);
        add(outputArea);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Fee Record")) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                String studentName = studentNameField.getText();
                double feeAmount = Double.parseDouble(feeAmountField.getText());

                String insertQuery = "INSERT INTO fees (student_id, student_name, fee_amount) VALUES (" + studentId + ", '" + studentName + "', " + feeAmount + ")";
                st.executeUpdate(insertQuery);

                outputArea.setText("Fee Record Added Successfully.");
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        } else if (e.getActionCommand().equals("Make Payment")) {
            try {
                int studentId = Integer.parseInt(studentIdField.getText());
                double paymentAmount = Double.parseDouble(paymentField.getText());

                String updateQuery = "UPDATE fees SET fee_amount = fee_amount - " + paymentAmount + " WHERE student_id = " + studentId;
                int rowsUpdated = st.executeUpdate(updateQuery);

                if (rowsUpdated > 0) {
                    outputArea.setText("Payment Successful.");
                } else {
                    outputArea.setText("Payment Failed. Invalid Student ID or Insufficient Balance.");
                }
            } catch (Exception ex) {
                outputArea.setText("Error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new FeeManagementSystemGUI();
    }
}
