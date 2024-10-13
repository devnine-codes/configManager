package com.aice.controller;

import com.aice.dao.ResponseApi;
import com.aice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/{apiVer}/companies")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/{companySeq}/customers")
    public ResponseEntity<ResponseApi> findCustomers(
            @PathVariable String apiVer,
            @PathVariable Long companySeq,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String customerEmail,
            @RequestParam(required = false) String customerMobile,
            @RequestParam(required = false) String customerPhone,
            Pageable pageable
    ) {
        return customerService.findCustomers(companySeq, customerName, customerEmail, customerMobile, customerPhone, pageable);
    }
}
