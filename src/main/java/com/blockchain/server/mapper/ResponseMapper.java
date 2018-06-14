package com.blockchain.server.mapper;

import com.blockchain.client.BlockChainClient;
import com.blockchain.client.model.Transaction;
import com.blockchain.client.model.TransactionDetails;
import com.blockchain.client.model.Transactions;
import com.blockchain.server.model.UnspentTransactionResponse;
import com.blockchain.server.model.UnspentTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResponseMapper {

    @Autowired
    private BlockChainClient client;

    public UnspentTransactionResponse getFilteredResponse(String hashValue) {
        Transactions transactions = client.getAllTransactions(hashValue);
        return performFiltering(transactions);
    }

    private UnspentTransactionResponse performFiltering(Transactions transactions) {
        List<UnspentTransaction> outputs = new ArrayList<>();
        if (!transactions.getTx().isEmpty()) {
            for (Transaction transaction : transactions.getTx()) {
                if (!transaction.getOut().isEmpty()) {
                    for (TransactionDetails details : transaction.getOut()) {
                        if (!details.isSpent()) {
                            UnspentTransaction unspentTransaction = new UnspentTransaction();
                            unspentTransaction.setValue(details.getValue());
                            unspentTransaction.setOutput_idx(String.valueOf(details.getTx_index()));
                            unspentTransaction.setTx_hash(transaction.getHash());
                            outputs.add(unspentTransaction);
                        }
                    }

                }
            }
        }
        UnspentTransactionResponse restResponse = new UnspentTransactionResponse();
        restResponse.setOutputs(outputs);
        return restResponse;
    }

}
