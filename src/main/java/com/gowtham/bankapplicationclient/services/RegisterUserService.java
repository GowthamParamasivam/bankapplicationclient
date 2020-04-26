package com.gowtham.bankapplicationclient.services;

import com.gowtham.bankapplicationclient.component.InitialComponent;
import com.gowtham.bankapplicationclient.resources.BankApplicationUser;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;

@Service
public class RegisterUserService {
    private static final String CONNECTION_YAML = "/home/gowthamparamasivam/Fabric-samples_1.4/fabric-samples/basic-network/connection.yaml";
    private static final String WALLET_PATH = "/home/gowthamparamasivam//Fabric-samples_1.4/CustomWallet";
    @Autowired
    private InitialComponent initialComponent;
    private HFClient hfClient;
    private HFCAClient caClient;
    private Enrollment adminEnrollment;

    @PostConstruct
    public void init() {
        hfClient = initialComponent.hfClient;
        caClient = initialComponent.caClient;
        adminEnrollment = initialComponent.adminEnrollment;
    }

    public String addUserToWallet(String user) throws Exception {
        //User Declaration
        BankApplicationUser normaluser = new BankApplicationUser(user, "org1", "Org1MSP", adminEnrollment);
        //Creating new Registration Request
        RegistrationRequest registrationRequest = new RegistrationRequest(user);
        registrationRequest.setAffiliation("org1");
        registrationRequest.setEnrollmentID(user); //Default Value will be from constructor of the Registration Request
        //Registering the user to the Certificate Authority
        String enrollmentSecret = caClient.register(registrationRequest, normaluser);
        //Enrolling the user with the Certificate Authority
        Enrollment enrollment = caClient.enroll(user, enrollmentSecret);
        // Creating an wallet Identity for the user we enrolled in the CA
        Wallet.Identity newUser = Wallet.Identity.createIdentity("Org1MSP", enrollment.getCert(), enrollment.getKey());
        //Registering the wallet System
        Wallet wallet = Wallet.createFileSystemWallet(Paths.get(WALLET_PATH));
        //Pushing the userIdentity with the unique ID of user
        wallet.put(user, newUser);
        //Returns String when the success message
        return user + " added successfully";

    }
}
