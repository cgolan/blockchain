package com.blockchain.client.constants;

import com.blockchain.client.builder.ClientURLBuilder;

public interface UrlConstants {

    ClientURLBuilder.Spec UNSPENT_TRANSACTION = new ClientURLBuilder.Spec("#{baseUrl}/#{rawblock}/#{blockHash}");

}
