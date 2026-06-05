package com.nhom6.model;

public class Customer extends User {

    private CustomerType customerType;

    public Customer() {
    }

    public Customer(String userId,
                    String fullName,
                    String phone,
                    String email,
                    CustomerType customerType) {

        super(userId, fullName, phone, email);
        this.customerType = customerType;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerType=" + customerType +
                "} " + super.toString();
    }
}