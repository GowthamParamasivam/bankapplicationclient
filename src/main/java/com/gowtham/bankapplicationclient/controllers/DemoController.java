package com.gowtham.bankapplicationclient.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gowtham.bankapplicationclient.resources.BankAccount;
import com.gowtham.bankapplicationclient.resources.BankApplicationUser;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.*;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.fabric.gateway.Wallet.Identity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

public class DemoController {
    private static final String CONNECTION_YAML = "/home/gowthamparamasivam/Fabric-samples_1.4/fabric-samples/basic-network/connection.yaml";
    private static final String FABRIC_CA_SERVER = "http://localhost:7054";
    @RequestMapping(value = "/demo",method = RequestMethod.POST)
    public String demoMethod(@RequestParam(name = "user") String user) throws Exception {

        //Hyperledger fabric client
        HFClient hfClient = HFClient.createNewInstance();
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        hfClient.setCryptoSuite(cryptoSuite);
        // Hyperledger Fabric CA Client
        HFCAClient caClient = HFCAClient.createNewInstance(FABRIC_CA_SERVER, null);
        caClient.setCryptoSuite(cryptoSuite);
        Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");
        BankApplicationUser normaluser = new BankApplicationUser(user,"org1", "Org1MSP", adminEnrollment);
        RegistrationRequest registrationRequest = new RegistrationRequest(user);
        registrationRequest.setAffiliation("org1");
        registrationRequest.setEnrollmentID(user);
        String enrollmentSecret = caClient.register(registrationRequest,normaluser);
        System.out.println(enrollmentSecret);
        Enrollment enrollment = caClient.enroll(user, enrollmentSecret);
        Identity newUser = Identity.createIdentity("Org1MSP", enrollment.getCert(), enrollment.getKey());
        //Wallet
        Wallet wallet = Wallet.createFileSystemWallet(Paths.get(CONNECTION_YAML));
        wallet.put(user, newUser);
        return "Success";
    }
    @RequestMapping("/demotransaction")
    public String transact(String number, String name, String age, @RequestParam(required = false) String guardian,
                           String balance, String minBal) throws IOException {
        Wallet wallet = Wallet.createFileSystemWallet(Paths.get("/home/gowthamparamasivam/Fabric-samples_1.4/CustomWallet"));
        Path connectionProfile = Paths.get(CONNECTION_YAML);
        Gateway.Builder builder =  Gateway.createBuilder().identity(wallet, name).networkConfig(connectionProfile).discovery(false);
        try(Gateway gateway = builder.connect()) {
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("bank", "BankSmartContract");
            byte[] response = contract.submitTransaction("addBankAccount", number,name,age,"null",balance,minBal);
            // Process response
            ObjectMapper objectMapper = new ObjectMapper();
            BankAccount bankAccount = objectMapper.readValue(response,BankAccount.class);
            System.out.println(bankAccount.getBankAccountNumber());
            String jsonoutput = objectMapper.writeValueAsString(bankAccount);
        } catch (InterruptedException | TimeoutException | ContractException e) {
            e.printStackTrace();
        }
        return "Success";
    }

    @RequestMapping("demo")
    public void demo(){


    }
}
