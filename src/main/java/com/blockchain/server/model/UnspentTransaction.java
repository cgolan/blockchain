package com.blockchain.server.model;

public class UnspentTransaction {

    private long value;
    private String tx_hash;
    private String output_idx;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getTx_hash() {
        return tx_hash;
    }

    public void setTx_hash(String tx_hash) {
        this.tx_hash = tx_hash;
    }

    public String getOutput_idx() {
        return output_idx;
    }

    public void setOutput_idx(String output_idx) {
        this.output_idx = output_idx;
    }
}
