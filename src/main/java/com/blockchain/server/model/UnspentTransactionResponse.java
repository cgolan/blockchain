package com.blockchain.server.model;

import java.util.List;

public class UnspentTransactionResponse {

    private List<UnspentTransaction> outputs;

    public List<UnspentTransaction> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<UnspentTransaction> outputs) {
        this.outputs = outputs;
    }
}
