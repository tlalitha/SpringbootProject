package com.example.customerAccount.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.customerAccount.dataModel.CustomerAccount;
import com.example.customerAccount.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	 @RequestMapping(path="accountId/{accId}")
	    public List<CustomerAccount> getCustomerDetails(@PathVariable("accId") int accId) {
		List<CustomerAccount> list= customerService.getCustomerDetailsByAccId(accId);
		
		if(CollectionUtils.isEmpty(list) || list==null) {
			return null;
		}
	    return list;
	 }
	 
	 @RequestMapping(path="customerId/{cusId}")
	    public List<CustomerAccount> getAccountDetails(@PathVariable("cusId") int cusId) {
		List<CustomerAccount> list= customerService.getAccountDetailsByCusId(cusId);
		if(CollectionUtils.isEmpty(list) || list==null) {
			return null;
		}
	        return list;
	 }
	 
	 @PostMapping(path = "/customer", consumes = "application/json", produces = "application/json")
		public String createCustomer(@RequestBody CustomerAccount customerDetails) {
		 customerService.createNewCustomer(customerDetails);
	        return "customer added";
	 }
	 
	 @PostMapping(path="/customerDetails", consumes = "application/json", produces = "application/json")
	    public String validateCustomer(@RequestBody CustomerAccount customerDetails) {
	        return customerService.validateCustomer(customerDetails);
	 }
}
