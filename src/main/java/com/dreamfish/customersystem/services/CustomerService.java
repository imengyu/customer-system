package com.dreamfish.customersystem.services;

import com.dreamfish.customersystem.entity.Customer;
import com.dreamfish.customersystem.utils.Result;

public interface CustomerService {
    Result getCustomerIndustry();
    Result getCustomersPageable(Integer pageIndex, Integer pageSize);
    Result deleteCustomer(Integer customerId);
    Result newCustomer(Customer customer);
    Result updateCustomer(Integer customerId, Customer customer);
    Result getCustomer(Integer customerId);
}
