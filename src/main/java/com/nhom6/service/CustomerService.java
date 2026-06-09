package com.nhom6.service;

import com.nhom6.model.Customer;
import com.nhom6.repository.FileRepository;

import java.util.List;

public class CustomerService {
    private FileRepository fileRepository = new FileRepository();

    public List<Customer> getAllCustomers() {
        return fileRepository.loadCustomers();
    }

    public void addCustomer(Customer customer) {
        fileRepository.saveCustomer(customer);
    }

    public boolean isDuplicateCustomerId(String customerId) {
        for (Customer customer : fileRepository.loadCustomers()) {
            if (customer.getUserId().equalsIgnoreCase(customerId)) {
                return true;
            }
        }
        return false;
    }
}
