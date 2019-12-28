package com.dreamfish.customersystem.web;

import com.dreamfish.customersystem.annotation.RequestAuth;
import com.dreamfish.customersystem.entity.Customer;
import com.dreamfish.customersystem.services.CustomerService;
import com.dreamfish.customersystem.services.UserService;
import com.dreamfish.customersystem.utils.Result;
import com.dreamfish.customersystem.utils.auth.PublicAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService = null;

    @Autowired
    private HttpServletRequest request = null;
    @Autowired
    private HttpServletResponse response = null;

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
        return customerService.deleteCustomer(PublicAuth.authGetUseId(request), customerId);
    }
    @RequestAuth
    @ResponseBody
    @PutMapping("/customer")
    public Result newCustomer(@RequestBody Customer customer) {
        return customerService.newCustomer(PublicAuth.authGetUseId(request), customer);
    }
    @ResponseBody
    @GetMapping("/customer/count")
    public Result getCustomerCount() {
        return customerService.getCustomerCount();
    }
    @ResponseBody
    @GetMapping("/customer/count/{userId}")
    public Result getCustomerCountByUserId(@PathVariable("userId") Integer userId) {
        return customerService.getCustomerCountByUserId(userId);
    }
    @RequestAuth
    @ResponseBody
    @PostMapping("/customer/{customerId}")
    public Result updateCustomer(@PathVariable("customerId") Integer customerId, @RequestBody Customer customer) {
        return customerService.updateCustomer(PublicAuth.authGetUseId(request), customerId, customer);
    }
    @RequestAuth
    @ResponseBody
    @GetMapping("/customer/{customerId}")
    public Result getCustomer(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomer(customerId);
    }
}
