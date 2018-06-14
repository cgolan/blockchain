package com.blockchain.client;

import com.blockchain.client.builder.ClientURLBuilder;
import com.blockchain.client.exception.BlockChainClientException;
import com.blockchain.client.model.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static com.blockchain.client.constants.UrlConstants.UNSPENT_TRANSACTION;

@Component
public class BlockChainClientImpl implements BlockChainClient {

    @Autowired
    private ClientURLBuilder clientURLBuilder;

    @Autowired
    @Qualifier(value = "blockChainClient")
    private RestTemplate blockChainClient;

    @Override
    public Transactions getAllTransactions(String blockHash) {
        String url = clientURLBuilder.aLink(UNSPENT_TRANSACTION)
                .withPathVariable("rawblock", "rawblock")
                .withPathVariable("blockHash", blockHash)
                .build();

        ResponseEntity<Transactions> response;
        try {
            response = blockChainClient.exchange(url, HttpMethod.GET, null, Transactions.class);
        }catch (HttpServerErrorException exception){
            throw new BlockChainClientException(exception.getResponseBodyAsString(), url, exception.getStatusCode());
        }
        return response.getBody();
    }
}