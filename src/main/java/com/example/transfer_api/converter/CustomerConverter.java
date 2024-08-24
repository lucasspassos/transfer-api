package com.example.transfer_api.converter;

import com.example.transfer_api.entity.CustomerEntity;
import com.example.transfer_api.util.Util;
import com.example.transfer_api.v1.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerConverter {

    public final Util util;

    public CustomerEntity toEntity(final Customer customer){
        return util.parseToType(customer, CustomerEntity.class);
    }

    public Customer toClass(final CustomerEntity customerEntity){
        return util.parseToType(customerEntity, Customer.class);
    }


    public List<Customer> toResponse(
            final List<CustomerEntity> entities) {

        List<Customer> customers = new ArrayList<>();

        try {
            if (!CollectionUtils.isEmpty(List.of(entities))) {
                customers = (List.of(util.parseToType(entities, Customer[].class)));
            }

        } catch (Exception e) {

        }

        return customers;
    }
}
