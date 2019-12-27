package com.dreamfish.customersystem.services.impl;

import com.dreamfish.customersystem.entity.Customer;
import com.dreamfish.customersystem.mapper.CustomerMapper;
import com.dreamfish.customersystem.repository.CustomerRepository;
import com.dreamfish.customersystem.services.CustomerService;
import com.dreamfish.customersystem.utils.Result;
import com.dreamfish.customersystem.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper = null;
    @Autowired
    private CustomerRepository customerRepository = null;


    @Override
    public Result getCustomersPageable(Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.ASC, "name"));
        return Result.success(customerRepository.findAll(pageable));
    }

    @Override
    public Result getCustomerIndustry() {
        return Result.success(customerMapper.getCustomerIndustry());
    }

    @Override
    public Result deleteCustomer(Integer customerId) {
        if(!customerRepository.existsById(customerId)) return Result.failure(ResultCodeEnum.NOT_FOUNT);
        customerRepository.deleteById(customerId);
        return Result.success();
    }

    @Override
    public Result newCustomer(Customer customer) {
        return Result.success(customerRepository.saveAndFlush(customer));
    }

    @Override
    public Result updateCustomer(Integer customerId, Customer customer) {

        if(!customerRepository.existsById(customerId)) return Result.failure(ResultCodeEnum.NOT_FOUNT);


        customer = customerRepository.saveAndFlush(customer);

        return Result.success(customer);
    }

    @Override
    public Result getCustomer(Integer customerId) {

        if(!customerRepository.existsById(customerId)) return Result.failure(ResultCodeEnum.NOT_FOUNT);

        return Result.success(customerMapper.getCustomerById(customerId));
    }

}
