package com.test.firstproject.dto.response;

import lombok.Data;

@Data
public class CustomerAccountInfo {

    private String customerName;

    private String fatherName;

    private String motherMaidenName;

    private String mailingAddress;

    private String availableBalance;

    private String accountStatus;

    private String cnic;

    private String email;

    private String accountOpeningDate;

    private String iban;

    private String contactNo;

    private String lastTransactionDateAndTime;

    private String lastTransactionAmount;

    private String accountType;

    private String registrationState;

}