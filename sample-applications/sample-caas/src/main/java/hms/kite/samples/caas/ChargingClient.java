/*
 *   (C) Copyright 2010-2013 hSenid Mobile Solutions (Pvt) Limited
 *   All Rights Reserved.
 *
 *   These materials are unpublished, proprietary, confidential source code of
 *   hSenid Mobile Solutions (Pvt) Limited and constitute a TRADE SECRET
 *   of hSenid Mobile Solutions (Pvt) Limited.
 *
 *   hSenid Mobile Solutions (Pvt) Limited retains all title to and intellectual
 *   property rights in these materials.
 *
 */

package hms.kite.samples.caas;

import hms.kite.samples.api.TapException;
import hms.kite.samples.api.caas.ChargingRequestSender;
import hms.kite.samples.api.caas.messages.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import java.net.MalformedURLException;
import java.net.URL;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Charging Client for send direct-debit request/ send query-balance request/ get payment-instrument-list
 */
public class ChargingClient {

    private static final String QUERY_BALANCE_KEYWORD = "query-balance";
    private static final String PAYMENT_INS_LIST_KEYWORD = "pay-ins-list";
    private static final String DIRECT_DEBIT_MOBILE_ACC_KEYWORD = "direct-debit-mobile-acc";

    private static final String DESCRIPTOR_PATH = "/WEB-INF/web.xml";
    private static final String APPLICATION_RESOURCE_PATH = "/sample-caas";

    private static String requestUrl;
    private static String appId;
    private static String password;
    private static String subscriberId;
    private static String type;
    private static String payInsName;
    private static String accountId;
    private static String amount;
    private static String currency;
    private static String extTrxId;
    private static String notificationListenerPort;

    public static void main(String[] args) {
        ChargingClient chargingClient = new ChargingClient();

        try {
            if (DIRECT_DEBIT_MOBILE_ACC_KEYWORD.equals(args[0])) {
                initializeMobileAccDirectDebitRequestParams(args);
                chargingClient.sendMobileAccountDirectDebitRequest(requestUrl, appId, password, subscriberId, payInsName,
                        accountId, currency, amount, extTrxId);

            } else if (PAYMENT_INS_LIST_KEYWORD.equals(args[0])) {
                initializePaymentInsListRequestParams(args);
                chargingClient.sendPaymentInstrumentListRequest(requestUrl, appId, password, subscriberId, type);

            } else if (QUERY_BALANCE_KEYWORD.equals(args[0])) {
                initializeQueryBalanceRequestParams(args);
                chargingClient.sendQueryBalanceRequest(requestUrl, appId, password, subscriberId, accountId, payInsName, currency);
            }

        } catch (MalformedURLException e){
            System.out.println("Error Occurred due to :" + e);

        } catch (TapException e){
            System.out.println("Error Occurred due to :" + e);

        } catch (Exception e){
            System.out.println("Error Occurred due to :" + e);

        }
    }


    /**
     * Send Direct Debit Request for Mobile Account Payment Instrument.
     * @param requestUrl - direct debit request URL
     * @param appId - application id
     * @param password - Password of the application
     * @param subscriberId - subscriber id
     * @param payInsName - payment instrument name
     * @param accountId - account Id
     * @param currency - specific currency
     * @param amount - request amount
     * @param extTrxId - external transaction id
     * @throws MalformedURLException
     * @throws TapException
     */
    private void sendMobileAccountDirectDebitRequest(String requestUrl, String appId, String password, String subscriberId, String payInsName,
                                                     String accountId, String currency, String amount, String extTrxId) throws Exception {

        ChargingRequestSender chargingRequestSender = null;
        try {
            chargingRequestSender = new ChargingRequestSender(new URL(requestUrl));

            // Create Direct Debit Request.
            DirectDebitRequest debitRequest = getDirectDebitRequest(appId, password, subscriberId, payInsName, accountId, currency, amount, extTrxId);

            // Send Charging Request.
            System.out.println("\n\nSending Direct Debit Request : " + printDirectDebitReqParamsforMobileAcc());
            ChargingRequestResponse chargingRequestResponse = chargingRequestSender.sendChargingRequest(debitRequest);
            System.out.println("\n\nReceived Response : " + chargingRequestResponse.toString());

        } catch (Exception ex) {
            System.out.println("Error occurred due to :" + ex);
        }

    }

    /**
     * Generate Direct Debit Request.
     * @param appId
     * @param password
     * @param subscriberId
     * @param payInsName
     * @param accountId
     * @param currency
     * @param amount
     * @param extTrxId
     * @return
     */
    private DirectDebitRequest getDirectDebitRequest(String appId, String password, String subscriberId, String payInsName, String accountId, String currency, String amount, String extTrxId) {
        DirectDebitRequest debitRequest = new DirectDebitRequest();
        debitRequest.setApplicationId(appId);
        debitRequest.setPassword(password);
        debitRequest.setSubscriberId(subscriberId);
        debitRequest.setPaymentInstrumentName(payInsName);
        debitRequest.setAccountId(accountId);
        debitRequest.setAmount(amount);
        debitRequest.setExternalTrxId(extTrxId);
        debitRequest.setCurrency(currency);
        return debitRequest;
    }

    /**
     * Send Payment Instrument List with following parameters.
     * @param url payment instrument list url
     * @param appId - application id
     * @param password - Password of the application
     * @param subscriberId - subscriber id
     * @param type - payment instrument query type (possible values- All/Individual)
     * @throws MalformedURLException
     * @throws TapException
     */
    private void sendPaymentInstrumentListRequest(String url, String appId, String password, String subscriberId, String type)
            throws MalformedURLException, TapException {

        ChargingRequestSender paymentInReqSender = new ChargingRequestSender(new URL(url));

        // Create Payment Instrument Request
        PaymentInstrumentListRequest paymentInsListReq = new PaymentInstrumentListRequest();
        paymentInsListReq.setApplicationId(appId);
        paymentInsListReq.setPassword(password);
        paymentInsListReq.setSubscriberId(subscriberId);
        paymentInsListReq.setType(type);

        // Send Payment Instrument List Request
        System.out.println("\n\nSending Payment Instrument Request : " + printPayInsListReqParams());
        PaymentInstrumentListResponse paymentInsListRes = paymentInReqSender.sendPaymentInstrumentListReq(paymentInsListReq);
        System.out.println("\n\nReceived Response  : " + paymentInsListRes);

    }

    /**
     * Send Check Balance Request
     * @param url query balance request url
     * @param accountId - account id
     * @param appId -Id of the application
     * @param password - Password of the application
     * @param subscriberId - subscriber id
     * @param paymentInsName - payment Instrument Name
     * @param currency - currency
     * @throws MalformedURLException
     * @throws TapException
     */
    private void sendQueryBalanceRequest(String url, String appId, String password, String subscriberId, String accountId,
                                         String paymentInsName, String currency)
            throws MalformedURLException, TapException {

        ChargingRequestSender chargingRequestSender =  new ChargingRequestSender(new URL(url));

        // Create Query Balance request
        QueryBalanceRequest queryBalanceRequest = new QueryBalanceRequest();
        queryBalanceRequest.setAccountId(accountId);
        queryBalanceRequest.setApplicationId(appId);
        queryBalanceRequest.setPassword(password);
        queryBalanceRequest.setSubscriberId(subscriberId);
        queryBalanceRequest.setPaymentInstrumentName(paymentInsName);
        queryBalanceRequest.setCurrency(currency);

        // Send Query Balance Request
        System.out.println("\n\nSending Query Balance Request : " + printQueryBalanceReqParams());
        QueryBalanceResponse balanceResponse = chargingRequestSender.sendQueryBalanceRequest(queryBalanceRequest);
        System.out.println("\n\nReceived Response " + balanceResponse.toString());
    }

    /**
     * Initialization of Direct Debit Request Parameters for mobile account payment instrument.
     * @param args
     */
    private static void initializeMobileAccDirectDebitRequestParams(String [] args) {
        requestUrl = args[1];
        appId = args[2];
        password = args[3];
        subscriberId = args[4];
        payInsName = args[5];
        accountId = args[6];
        currency = args[7];
        amount = args[8];
        extTrxId = args[9];
    }

    /**
     * This method with start the embedded jetty server to listen Charging notification.
     */
    private void startJettyServer(String notificationListenerPort) throws Exception {
        ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();

        // Creating web app context handler to configure web context
        WebAppContext webAppContextHandler = new WebAppContext();

        // Setting web context descriptor path
        webAppContextHandler.setDescriptor(webAppContextHandler + DESCRIPTOR_PATH);

        // Setting application resource.
        webAppContextHandler.setResourceBase("." + APPLICATION_RESOURCE_PATH);
        webAppContextHandler.setContextPath("/");

        // Setting web app context handler to context.
        contextHandlerCollection.setHandlers(new Handler[]{webAppContextHandler});

        //Configure server with port.
        Server server = new Server(Integer.parseInt(notificationListenerPort));
        server.setHandler(contextHandlerCollection);

        // Start jetty server.
        server.start();
    }

    /**
     * Initialization of Payment Instrument List Request Parameters.
     * @param args
     */
    private static void initializePaymentInsListRequestParams(String[] args) {
        requestUrl = args[1];
        appId = args[2];
        password = args[3];
        subscriberId = args[4];
        type = args[5];
    }

    /**
     * Initialization of Query Balance Request Parameters.
     * @param args
     */
    private static void initializeQueryBalanceRequestParams(String[] args) {
        requestUrl = args[1];
        appId = args[2];
        password = args[3];
        subscriberId = args[4];
        accountId = args[5];
        payInsName = args[6];
        currency = args[7];
    }

    private String printDirectDebitReqParamsforMobileAcc() {
        return new StringBuilder()
                .append("ChargingRequest{")
                .append("applicationId='").append(appId).append('\'')
                .append(", password='").append(password).append('\'')
                .append(", subscriberId='").append(subscriberId).append('\'')
                .append(", accountId='").append(accountId).append('\'')
                .append(", externalTrxId='").append(extTrxId).append('\'')
                .append(", paymentInstrumentName='").append(payInsName).append('\'')
                .append(", amount='").append(amount).append('\'')
                .append(", currency='").append(currency).append('\'')
                .append('}').toString();
    }


    private String printPayInsListReqParams() {
        return new StringBuilder()
                .append("PaymentInstrumentListRequest{")
                .append("applicationId='").append(appId).append('\'')
                .append(", password='").append(password).append('\'')
                .append(", subscriberId='").append(subscriberId).append('\'')
                .append(", type='").append(type).append('\'')
                .append('}').toString();
    }

    private String printQueryBalanceReqParams() {
        return new StringBuilder()
                .append("QueryBalanceRequest{")
                .append("applicationId='").append(appId).append('\'')
                .append(", password='").append(password).append('\'')
                .append(", subscriberId='").append(subscriberId).append('\'')
                .append("accountId='").append(accountId).append('\'')
                .append(", paymentInstrumentName='").append(payInsName).append('\'')
                .append(", currency='").append(currency).append('\'')
                .append('}').toString();
    }

}
