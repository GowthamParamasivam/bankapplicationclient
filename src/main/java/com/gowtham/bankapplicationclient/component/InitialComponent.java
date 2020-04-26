package com.gowtham.bankapplicationclient.component;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.sql.SQLOutput;

@Component
public class InitialComponent {

    private static final String FABRIC_CA_SERVER = "http://localhost:7054";
    public HFClient hfClient;
    public HFCAClient caClient;
    public Enrollment adminEnrollment;

    @PostConstruct
    public void init() throws IllegalAccessException, InvocationTargetException, InvalidArgumentException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException, MalformedURLException, EnrollmentException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException, InterruptedException {
        //Hyperledger fabric client
        hfClient = HFClient.createNewInstance();
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        hfClient.setCryptoSuite(cryptoSuite);
        caClient = HFCAClient.createNewInstance(FABRIC_CA_SERVER, null);
        caClient.setCryptoSuite(cryptoSuite);
        adminEnrollment = caClient.enroll("admin", "adminpw");
    }
}
