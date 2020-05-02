package com.gowtham.bankapplicationclient.resources;

import lombok.*;


import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccount {
    @NotNull
    private String BankAccountNumber;
    @NotNull
    private String BankAccountHolderName;
    @NotNull
    private String BankAccountHolderAge;
    private String BankAccountGuardianName;
    private String BalanceAmount;
    private String MinimumBalanceAmount;
}
