package com.gowtham.bankapplicationclient.controllers;

import com.gowtham.bankapplicationclient.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequestMapping("/transaction")
public class Bank {

    @Autowired
    private BankService bankService;

    @RequestMapping("/initialize")
    public String initialize(String number, String name, String age, @RequestParam(required = false) String guardian,
                           String balance, String minBal) throws IOException {
        return bankService.initialize(number,name,age, guardian, balance, minBal);
    }

}
