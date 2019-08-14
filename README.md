# Coindirect examples using Java

To facilitate easier integration into the Coindirect API's examples will be continually added to this repository.

## Setup your API client
```
String apiKey = "myApiKey";
String apiSecret = "myApiSecret";

CoindirectApiClient coindirectApiClient = new CoindirectApiClient(apiKey, apiSecret);
if(USE_SANDBOX) {
    coindirectApiClient.setBasePath("https://api.sandbox.coindirect.com");
    coindirectApiClient.setDebugging(true);
}
```



### Pay in example
```
PayRequest payRequest = new PayRequest();
payRequest.setMerchantId(merchantId);
payRequest.setType(PayRequest.TypeEnum.IN);
payRequest.setCurrency("ZAR");
payRequest.setReference(myUniqueReference);
payRequest.setExpiryMinutes(90);
payRequest.setAmount(new BigDecimal("250"));
payRequest.setReturnUrl("https://www.google.com/?myPaymentRef=${myUniqueReference}");

Payment payment = paymentsApi.requestPayment(payRequest);

response.setHeader("Location", payment.getPayInstruction().getRedirectUrl());
```

### Read payment
```
Payment payment = paymentsApi.readPayment(payment.getUuid(), null);
```

