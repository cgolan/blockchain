This WebApp will accept HTTP GET requests at 

`http://localhost:8080/address/{hashValue}`

that will use the Blockchain Data API at 

`https://blockchain.info/api/blockchain_api`

and respond with a JSON representation of all unspent transactions for the given address of a blockchain network:

`{  
   "outputs":[  
      {  
         "value":500000,
         "tx_hash":"834b5547cfa4557c8a94a2208c679ea0d82ff905fa8d70f054f0e428b71e8905",
         "output_idx":"1939113"
      }
   ]
}
`

Requirements :

 - JDK 1.8 or later

 - Maven 3.2+

Make the application executable

- use Spring’s Boot support for embedding the Tomcat servlet container as the HTTP runtime, instead of deploying to an external instance and run the main method
- run the application using maven command `./mvnw spring-boot:run`
- build a jar file using the command `./mvnw clean package` and run `java -jar target/demo-0.0.1-SNAPSHOT.jar`

Test the service

Request

`http://localhost:8080/address/0000000000000bae09a7a393a8acded75aa67e46cb81f7acaa5ad94f9eacd103
`

Response

`{  
   "outputs":[  
      {  
         "value":500000,
         "tx_hash":"834b5547cfa4557c8a94a2208c679ea0d82ff905fa8d70f054f0e428b71e8905",
         "output_idx":"1939113"
      }
   ]
}
`

