package com.blockchain.server.controller;

import com.blockchain.server.mapper.ResponseMapper;
import com.blockchain.server.model.UnspentTransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address/")
public class MainController {

    @Autowired
    private ResponseMapper mapper;

    @GetMapping("{hashValue}")
    @ResponseBody
    public UnspentTransactionResponse getInformation(@PathVariable String hashValue) {
        return mapper.getFilteredResponse(hashValue);
    }

}
