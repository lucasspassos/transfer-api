package com.example.transfer_api.converter;

import com.example.transfer_api.entity.CustomerEntity;
import com.example.transfer_api.util.Util;
import com.example.transfer_api.v1.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CustomerConverterTest {

    @InjectMocks
    private CustomerConverter customerConverter;

    @Mock
    private Util util;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToEntity() {
        Customer customer = new Customer();
        CustomerEntity customerEntity = new CustomerEntity();
        when(util.parseToType(customer, CustomerEntity.class)).thenReturn(customerEntity);

        CustomerEntity result = customerConverter.toEntity(customer);

        assertEquals(customerEntity, result);
    }

    @Test
    void testToClass() {
        CustomerEntity customerEntity = new CustomerEntity();
        Customer customer = new Customer();
        when(util.parseToType(customerEntity, Customer.class)).thenReturn(customer);

        Customer result = customerConverter.toClass(customerEntity);

        assertEquals(customer, result);
    }

    @Test
    void testToResponse() {
        CustomerEntity customerEntity = new CustomerEntity();
        List<CustomerEntity> entities = new ArrayList<>();
        entities.add(customerEntity);

        Customer customer = new Customer();
        when(util.parseToType(entities, Customer[].class)).thenReturn(new Customer[]{customer});

        List<Customer> result = customerConverter.toResponse(entities);

        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
    }

    @Test
    void testToResponseEmptyList() {
        List<CustomerEntity> entities = new ArrayList<>();

        List<Customer> result = customerConverter.toResponse(entities);

        assertEquals(0, result.size());
    }

    @Test
    void testToResponseException() {
        List<CustomerEntity> entities = new ArrayList<>();
        when(util.parseToType(entities, Customer[].class)).thenThrow(new RuntimeException("Error"));

        List<Customer> result = customerConverter.toResponse(entities);

        assertEquals(0, result.size());
    }
}
