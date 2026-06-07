package com.nhom6.utils;

import javax.swing.*;

public class ValidationUtils {
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean requireTextField(JFrame frame, JTextField field, String fieldName) {
        if (isEmpty(field.getText())) {
            JOptionPane.showMessageDialog(frame, "Vui long nhap " + fieldName);
            field.requestFocus();
            return false;
        }
        return true;
    }
}