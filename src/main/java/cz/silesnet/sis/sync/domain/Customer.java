/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.sync.domain;

import java.util.ArrayList;
import java.util.Comparator;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * Customer data object with no logic. It contains only fields needed for
 * synchronization.
 * 
 * @author Richard Sikora
 */
public class Customer implements ItemIdentity {

    private long id;
    private String symbol;
    private String name;
    private String supplementaryName;
    private String contactName;
    private String city;
    private String street;
    private String zip;
    private String ico;
    private String dic;
    private String phone;
    private String email;
    private String contract;
    private String accountNo;
    private String bankCode;

    public Customer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupplementaryName() {
        return supplementaryName;
    }

    public void setSupplementaryName(String supplementaryName) {
        this.supplementaryName = supplementaryName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getDic() {
        return dic;
    }

    public void setDic(String dic) {
        this.dic = dic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getSpsContract() {
        if (getContract() == null)
            return null;
        // get all comma separated contract string's
        String[] contractStrings = getContract().split(",");
        ArrayList<Contract> contracs = new ArrayList<Contract>();
        for (String contract : contractStrings) {
            try {
                contracs.add(new Contract(contract));
            } catch (NumberFormatException e) {
                /* Ignore numbers that can not be converted */
            } catch (IndexOutOfBoundsException e) {
                /* Ignore missing contract number parts */
            }
        }
        // sort descending according contract numbers
        Collections.sort(contracs, new Comparator<Contract>() {
            public int compare(Contract o1, Contract o2) {
                return o2.getNumber() - o1.getNumber();
            }
        });
        return contracs.size() > 0 ? contracs.get(0).getSpsNumber() : null;
    }

    private class Contract {

        private final int number;
        private final int year;

        public Contract(String contract) {
            String[] parts = contract.split("/");
            number = getInteger(parts[0]);
            year = getInteger(parts[1]);
        }

        private int getInteger(String intString) {
            // ignore spaces
            return Integer.valueOf(intString.replace(" ", ""));
        }

        public int getNumber() {
            return number;
        }

        public String getSpsNumber() {
            return ("" + number) + year;
        }

    }
}
