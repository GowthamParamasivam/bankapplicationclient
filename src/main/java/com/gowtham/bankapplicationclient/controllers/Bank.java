package com.gowtham.bankapplicationclient.controllers;

import com.gowtham.bankapplicationclient.resources.BankAccount;
import com.gowtham.bankapplicationclient.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/transaction")
public class Bank {

    @Autowired
    private BankService bankService;

    @RequestMapping("/initialize")
    public ResponseEntity initialize(@Valid @RequestBody BankAccount bankAccount) throws IOException {
        return new ResponseEntity(bankService.initialize(bankAccount), HttpStatus.CREATED);
    }

    //TODO transfer of money from one account to another
}
