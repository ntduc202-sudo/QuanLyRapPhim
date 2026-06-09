package com.nhom6.user;

import com.nhom6.model.Customer;
import com.nhom6.model.CustomerType;
import com.nhom6.service.CustomerService;
import com.nhom6.utils.ValidationUtils;

import javax.swing.*;
import java.awt.*;

public class RegisterCustomerFrame extends JFrame {
    private CustomerService customerService = new CustomerService();

    private JTextField customerIdField = new JTextField();
    private JTextField nameField = new JTextField();
    private JTextField phoneField = new JTextField();
    private JTextField emailField = new JTextField();
    private JComboBox<CustomerType> customerTypeBox = new JComboBox<>(CustomerType.values());

    public RegisterCustomerFrame() {
        setTitle("Dang Ky Khach Hang");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        JButton saveButton = new JButton("Dang ky");

        add(new JLabel("Ma khach hang"));
        add(customerIdField);
        add(new JLabel("Ho ten"));
        add(nameField);
        add(new JLabel("So dien thoai"));
        add(phoneField);
        add(new JLabel("Email"));
        add(emailField);
        add(new JLabel("Loai khach hang"));
        add(customerTypeBox);
        add(new JLabel());
        add(saveButton);

        saveButton.addActionListener(e -> saveCustomer());
    }

    private void saveCustomer() {
        if (!validateForm()) {
            return;
        }

        if (customerService.isDuplicateCustomerId(customerIdField.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Ma khach hang da ton tai");
            return;
        }

        Customer customer = new Customer(
                customerIdField.getText().trim(),
                nameField.getText().trim(),
                phoneField.getText().trim(),
                emailField.getText().trim(),
                (CustomerType) customerTypeBox.getSelectedItem()
        );

        customerService.addCustomer(customer);
        JOptionPane.showMessageDialog(this, "Dang ky thanh cong");
    }

    private boolean validateForm() {
        return ValidationUtils.requireTextField(this, customerIdField, "ma khach hang") &&
                ValidationUtils.requireTextField(this, nameField, "ho ten") &&
                ValidationUtils.requireTextField(this, phoneField, "so dien thoai") &&
                ValidationUtils.requireTextField(this, emailField, "email");
    }
}