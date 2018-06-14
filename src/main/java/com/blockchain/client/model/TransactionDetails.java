package com.blockchain.client.model;

public class TransactionDetails {

    private Boolean spent;
    private long value;
    private long tx_index;

    public Boolean isSpent() {
        return spent;
    }

    public void setSpent(Boolean spent) {
        this.spent = spent;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getTx_index() {
        return tx_index;
    }

    public void setTx_index(long tx_index) {
        this.tx_index = tx_index;
    }
}
