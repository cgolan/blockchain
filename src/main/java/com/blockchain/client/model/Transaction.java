package com.blockchain.client.model;

import java.util.List;

public class Transaction {

    private String hash;
    private List<TransactionDetails> out;


    public String getHash() {
        return hash;
    }


    public void setHash(String hash) {
        this.hash = hash;
    }


    public List<TransactionDetails> getOut() {
        return out;
    }

    public void setOut(List<TransactionDetails> out) {
        this.out = out;
    }
}
