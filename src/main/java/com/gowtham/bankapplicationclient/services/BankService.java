package com.gowtham.bankapplicationclient.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gowtham.bankapplicationclient.resources.BankAccount;
import org.hyperledger.fabric.gateway.*;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeoutException;

@Service
public class BankService {

    private static final String CONNECTION_YAML = "/home/gowthamparamasivam/Fabric-samples_1.4/fabric-samples/basic-network/connection.yaml";
    private static final String WALLET_PATH = "/home/gowthamparamasivam//Fabric-samples_1.4/CustomWallet";
    private static final String CHANNEL = "mychannel";
    private static final String CHAINCODE_ID = "bank";
    private static final String CHAINCODE_NAME = "BankSmartContract";

    public BankAccount initialize(final BankAccount bankAccount1) throws IOException {
        Wallet wallet = Wallet.createFileSystemWallet(Paths.get(WALLET_PATH));
        Path connectionProfile = Paths.get(CONNECTION_YAML);
        Gateway.Builder builder =  Gateway.createBuilder().identity(wallet, bankAccount1.getBankAccountHolderName()).networkConfig(connectionProfile).discovery(false);
        try(Gateway gateway = builder.connect()) {
            Network network = gateway.getNetwork(CHANNEL);
            Contract contract = network.getContract(CHAINCODE_ID, CHAINCODE_NAME);
            byte[] response = contract.submitTransaction("addBankAccount", bankAccount1.getBankAccountNumber(),bankAccount1.getBankAccountHolderName(),
                    bankAccount1.getBankAccountHolderAge(),bankAccount1.getBankAccountGuardianName(),bankAccount1.getBalanceAmount(),bankAccount1.getMinimumBalanceAmount());
            ObjectMapper objectMapper = new ObjectMapper();
            BankAccount bankAccount = objectMapper.readValue(response,BankAccount.class);
//            String jsonoutput = objectMapper.writeValueAsString(bankAccount);
            return bankAccount;
        } catch (InterruptedException | TimeoutException | ContractException e) {
            e.printStackTrace();
        }
        return null;

    }
}
