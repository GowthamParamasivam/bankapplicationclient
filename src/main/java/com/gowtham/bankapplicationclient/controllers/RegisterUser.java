package com.gowtham.bankapplicationclient.controllers;

import com.gowtham.bankapplicationclient.services.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/register")
public class RegisterUser {
    @Autowired
    private RegisterUserService registerUserService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String addWallet(String user) throws Exception {
        return registerUserService.addUserToWallet(user);
    }

}
