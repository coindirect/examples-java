import ch.qos.logback.classic.Level
import groovy.util.logging.Slf4j
import org.coindirect.api.PaymentsApi
import org.coindirect.api.WalletsApi
import org.coindirect.api.invoker.ApiException
import org.coindirect.api.invoker.CoindirectApiClient
import org.coindirect.api.model.PayRequest
import org.coindirect.api.model.Payment
import org.coindirect.api.model.Wallet

@Slf4j
class PayInExample {
    public static Boolean USE_SANDBOX = true;
    public static String SANDBOX_URL = "https://api.sandbox.coindirect.com";

    static {
        Logger.setLevel(Level.TRACE)
    }

    public static void main(String[] args) {
        if(args.size() < 2) {
            log.error(usage());
            return;
        }

        String merchantId = null;
        if(args.size() >= 3) {
            merchantId = args[2];
        }

        String apiKey = args[0];
        String apiSecret = args[1];

        CoindirectApiClient coindirectApiClient = new CoindirectApiClient(apiKey, apiSecret);
        if(USE_SANDBOX) {
            coindirectApiClient.setBasePath(SANDBOX_URL);
//            coindirectApiClient.setDebugging(true);
        }

        PaymentsApi paymentsApi = new PaymentsApi(coindirectApiClient);

        String myUniqueReference = UUID.randomUUID().toString();

        PayRequest payRequest = new PayRequest();
        payRequest.setMerchantId(merchantId);
        payRequest.setType(PayRequest.TypeEnum.IN);
        payRequest.setCurrency("ZAR");
        payRequest.setReference(myUniqueReference);
        payRequest.setExpiryMinutes(90);
        payRequest.setAmount(new BigDecimal("250"));
        payRequest.setReturnUrl("https://www.google.com/?myPaymentRef=${myUniqueReference}");

        try {
            Payment payment = paymentsApi.requestPayment(payRequest);

            log.debug("Created payment ${payment}");
            log.debug("-- now redirect to ${payment.getPayInstruction().getRedirectUrl()}");

            payment = paymentsApi.readPayment(payment.getUuid(), null);

            log.debug("Payment status is now ${payment.getStatus()}");

        } catch(ApiException apiException) {
            log.error("Received exception from api ${apiException.getCode()} ${apiException.getResponseBody()}");
        }
    }

    static String usage() {
        return "Usage: java ${getClass().getName()} <apiKey> <apiSecret>";
    }

}
