package com.blockchain.server.mapper;

import com.blockchain.client.BlockChainClient;
import com.blockchain.client.model.Transaction;
import com.blockchain.client.model.TransactionDetails;
import com.blockchain.client.model.Transactions;
import com.blockchain.server.model.UnspentTransactionResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResponseMapperTest {

    @InjectMocks
    private ResponseMapper testSubject;

    @Mock
    private BlockChainClient client;


    @Test
    public void shouldMapDataFromClientToSever() {

        Transactions clientResponse = buildResponse();

        //Given
        when(client.getAllTransactions(anyString())).thenReturn(clientResponse);

        //When
        UnspentTransactionResponse restResponse = testSubject.getFilteredResponse("hash");

        //Then
        verify(client).getAllTransactions(anyString());

        assertNotNull(restResponse);
        assertNotNull(restResponse.getOutputs());
        assertNotNull(restResponse.getOutputs().get(0));
        assertEquals(restResponse.getOutputs().get(0).getValue(), clientResponse.getTx().get(0).getOut().get(1).getValue());
    }

    private Transactions buildResponse() {
        Transactions transactions = new Transactions();
        transactions.setTx(createTransactionList());
        return transactions;
    }

    private List<Transaction> createTransactionList() {
        List<Transaction> transactionList = new ArrayList<>();
        Transaction transaction = new Transaction();
        transaction.setHash("hash");
        transaction.setOut(createTransactionDetails());
        transactionList.add(transaction);
        return transactionList;
    }

    private List<TransactionDetails> createTransactionDetails() {
        List<TransactionDetails> transactionDetails = new ArrayList<>();

        TransactionDetails tx1 = new TransactionDetails();
        tx1.setSpent(true);
        tx1.setTx_index(1L);
        tx1.setValue(1L);

        TransactionDetails tx2 = new TransactionDetails();
        tx2.setSpent(false);
        tx2.setTx_index(2L);
        tx2.setValue(2L);

        transactionDetails.add(tx1);
        transactionDetails.add(tx2);
        return transactionDetails;
    }
}
