package com.blockchain.server.controller;

import com.blockchain.server.mapper.ResponseMapper;
import com.blockchain.server.model.UnspentTransactionResponse;
import com.blockchain.server.model.UnspentTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {

    private static final String HASH = "0000000000000bae09a7a393a8acded75aa67e46cb81f7acaa5ad94f9eacd103";
    private static final String JSON = "{\"outputs\":[{\"value\":500000,\"tx_hash\":\"834b5547cfa4557c8a94a2208c679ea0d82ff905fa8d70f054f0e428b71e8905\",\"output_idx\":\"1939113\"}]}";

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private ResponseMapper mapper;

    @Test
    public void getFilteredTransactionsFromTheClient() throws Exception {
        given(mapper.getFilteredResponse(HASH)).willReturn(createResponse());
        mockMvc.perform(get("/address/" + HASH))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().encoding("UTF-8"))
                .andExpect(content().string(JSON));
        verify(mapper).getFilteredResponse(HASH);
    }

    private UnspentTransactionResponse createResponse() {
        UnspentTransactionResponse restResponse = new UnspentTransactionResponse();

        List<UnspentTransaction> list = new ArrayList<>();

        UnspentTransaction transaction = new UnspentTransaction();
        transaction.setTx_hash("834b5547cfa4557c8a94a2208c679ea0d82ff905fa8d70f054f0e428b71e8905");
        transaction.setValue(500000);
        transaction.setOutput_idx("1939113");

        list.add(transaction);

        restResponse.setOutputs(list);
        return restResponse;
    }

}