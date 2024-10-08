package com.example.transfer_api.controller;

import com.example.transfer_api.service.CustomerService;
import com.example.transfer_api.v1.api.CustomersApi;
import com.example.transfer_api.v1.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class CustomerController implements CustomersApi {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<List<Customer>> listCustomers() {
        List<Customer> customers = customerService.getAllCustomers();

        if (customers == null || customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Customer> getCustomerByAccountNumber(Integer accountNumber) {
        Customer customer = customerService.getCustomerByAccountNumber(accountNumber);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> registerCustomer(Customer customer) {
        try{
            customerService.saveCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

}
