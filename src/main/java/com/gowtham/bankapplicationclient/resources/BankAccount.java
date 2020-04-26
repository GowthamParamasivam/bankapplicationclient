package com.gowtham.bankapplicationclient.resources;

public class BankAccount {
    private String BankAccountNumber;
    private String BankAccountHolderName;
    private String BankAccountHolderAge;
    private String BankAccountGuardianName;
    private String BalanceAmount;
    private String MinimumBalanceAmount;

    public BankAccount() {
    }

    public BankAccount(String bankAccountNumber, String bankAccountHolderName, String bankAccountHolderAge, String bankAccountGuardianName, String balanceAmount, String minimumBalanceAmount) {
        BankAccountNumber = bankAccountNumber;
        BankAccountHolderName = bankAccountHolderName;
        BankAccountHolderAge = bankAccountHolderAge;
        BankAccountGuardianName = bankAccountGuardianName;
        BalanceAmount = balanceAmount;
        MinimumBalanceAmount = minimumBalanceAmount;
    }

    public String getBankAccountNumber() {
        return BankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        BankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountHolderName() {
        return BankAccountHolderName;
    }

    public void setBankAccountHolderName(String bankAccountHolderName) {
        BankAccountHolderName = bankAccountHolderName;
    }

    public String getBankAccountHolderAge() {
        return BankAccountHolderAge;
    }

    public void setBankAccountHolderAge(String bankAccountHolderAge) {
        BankAccountHolderAge = bankAccountHolderAge;
    }

    public String getBankAccountGuardianName() {
        return BankAccountGuardianName;
    }

    public void setBankAccountGuardianName(String bankAccountGuardianName) {
        BankAccountGuardianName = bankAccountGuardianName;
    }

    public String getBalanceAmount() {
        return BalanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        BalanceAmount = balanceAmount;
    }

    public String getMinimumBalanceAmount() {
        return MinimumBalanceAmount;
    }

    public void setMinimumBalanceAmount(String minimumBalanceAmount) {
        MinimumBalanceAmount = minimumBalanceAmount;
    }
}
