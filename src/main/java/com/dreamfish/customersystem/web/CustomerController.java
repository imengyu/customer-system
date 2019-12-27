package com.dreamfish.customersystem.web;

import com.dreamfish.customersystem.annotation.RequestAuth;
import com.dreamfish.customersystem.entity.Customer;
import com.dreamfish.customersystem.services.CustomerService;
import com.dreamfish.customersystem.services.UserService;
import com.dreamfish.customersystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService = null;

    @RequestAuth
    @ResponseBody
    @GetMapping("/customer/{pageIndex}/{pageSize}")
    public Result getCustomersPageable(@PathVariable("pageIndex") Integer pageIndex, @PathVariable("pageSize") Integer pageSize) {
        return customerService.getCustomersPageable(pageIndex, pageSize);
    }
    @RequestAuth
    @ResponseBody
    @GetMapping("/customer/industry")
    public Result getCustomerIndustry(){
        return customerService.getCustomerIndustry();
    }
    @RequestAuth
    @ResponseBody
    @DeleteMapping("/customer/{customerId}")
    public Result deleteCustomer(@PathVariable("customerId") Integer customerId) {
        return customerService.deleteCustomer(customerId);
    }
    @RequestAuth
    @ResponseBody
    @PutMapping("/customer")
    public Result newCustomer(@RequestBody Customer customer) {
        return customerService.newCustomer(customer);
    }
    @RequestAuth
    @ResponseBody
    @PostMapping("/customer/{customerId}")
    public Result updateCustomer(@PathVariable("customerId") Integer customerId, @RequestBody Customer customer) {
        return customerService.updateCustomer(customerId, customer);
    }
    @RequestAuth
    @ResponseBody
    @GetMapping("/customer/{customerId}")
    public Result getCustomer(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomer(customerId);
    }
}
