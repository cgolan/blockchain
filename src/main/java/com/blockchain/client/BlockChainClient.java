package com.blockchain.client;

import com.blockchain.client.model.Transactions;

public interface BlockChainClient {

    Transactions getAllTransactions(String blockHash);
}
