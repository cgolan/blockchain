package com.blockchain.client;

import com.blockchain.client.builder.ClientURLBuilder;
import com.blockchain.client.exception.BlockChainClientException;
import com.blockchain.client.model.Transactions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlockChainClientImplTest {

    private static final String HASH = "0000000000000bae09a7a393a8acded75aa67e46cb81f7acaa5ad94f9eacd103";


    @InjectMocks
    private BlockChainClientImpl testSubject;

    @Mock
    private ClientURLBuilder clientURLBuilder;

    @Mock
    private RestTemplate blockChainClient;

    @Test
    public void shouldThrowException() {

        //Given
        configureUrlBuilderMock("#{rawblock}", "#{blockHash}");
        when(blockChainClient.exchange(
                anyString(),
                eq(HttpMethod.GET),
                isNull(),
                eq(Transactions.class))
        ).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        //When
        try {
            testSubject.getAllTransactions(HASH);
        } catch (BlockChainClientException e) {
            assertEquals(e.getHttpStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Then
        verify(blockChainClient).exchange(anyString(),
                eq(HttpMethod.GET),
                isNull(),
                eq(Transactions.class));
    }

    @Test
    public void getAllTransactions() {

        //Given
        configureUrlBuilderMock("#{rawblock}", "#{blockHash}");
        Transactions expected = new Transactions();
        ResponseEntity<Transactions> responseEntity = new ResponseEntity<>(expected, HttpStatus.OK);
        when(blockChainClient.exchange(
                anyString(),
                eq(HttpMethod.GET),
                isNull(),
                eq(Transactions.class))
        ).thenReturn(responseEntity);

        //When
        Transactions actual = testSubject.getAllTransactions(HASH);

        //Then
        verify(blockChainClient).exchange(anyString(),
                eq(HttpMethod.GET),
                isNull(),
                eq(Transactions.class));
        assertNotNull(actual);
        assertSame(expected, actual);
    }

    private void configureUrlBuilderMock(String... args) {
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
            builder.append("/");
            builder.append(arg);
        }

        ClientURLBuilder.Spec spec = new ClientURLBuilder.Spec("baseUrl" + builder.toString());
        ClientURLBuilder.LinkBuilder linkBuilder = new ClientURLBuilder.LinkBuilder(spec);
        when(clientURLBuilder.aLink(any(ClientURLBuilder.Spec.class))).thenReturn(linkBuilder);
    }

}