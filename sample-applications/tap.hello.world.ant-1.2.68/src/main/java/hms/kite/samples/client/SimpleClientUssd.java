/*
 *   (C) Copyright 1996-${year} hSenid Software International (Pvt) Limited.
 *   All Rights Reserved.
 *
 *   These materials are unpublished, proprietary, confidential source code of
 *   hSenid Software International (Pvt) Limited and constitute a TRADE SECRET
 *   of hSenid Software International (Pvt) Limited.
 *
 *   hSenid Software International (Pvt) Limited retains all title to and intellectual
 *   property rights in these materials.
 *
 */
package hms.kite.samples.client;

import hms.kite.samples.api.StatusCodes;
import hms.kite.samples.api.ussd.MoUssdListener;
import hms.kite.samples.api.ussd.UssdRequestSender;
import hms.kite.samples.api.ussd.messages.MoUssdReq;
import hms.kite.samples.api.ussd.messages.MtUssdReq;
import hms.kite.samples.api.ussd.messages.MtUssdResp;
import hms.kite.samples.client.sessions.UssdSession;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleClientUssd implements MoUssdListener {

    private final static Logger LOGGER = Logger.getLogger(SimpleClientUssd.class.getName());

    private boolean running = true;
    private int sessionTimeOut = 1000*60; //remove session in 1 minutes if not response

    private Map<String, UssdSession> ussdSessionMap = new HashMap();

    @Override
    public void init() {
        Thread sessionMonitorThread = new Thread("session-monitor-thread") {

            @Override
            public void run() {
                while (running) {
                    try {
                        removeTimeOutSessions();
                        Thread.sleep(10000);
                    } catch (InterruptedException e){
                        System.out.println("Interrupted exception occurred. Session monitor is going to stop");
                        running = false;
                    }catch (Exception e) {
                        System.err.println("Unexpected error occurred in session monitor thread");
                    }
                }
            }

            private void removeTimeOutSessions() {
                for (UssdSession ussdSession : ussdSessionMap.values()) {
                    if ((ussdSession.getLastAccessTime() + sessionTimeOut) < System.currentTimeMillis()) {
                        closeUssdSession(ussdSession.getMsisdn());
                    }
                }
            }
        };
        sessionMonitorThread.start();
    }

    public void onReceivedUssd(MoUssdReq moUssdReq) {
        try {
            System.out.println("USSD Received " + moUssdReq);

            UssdRequestSender mtSender = new UssdRequestSender(new URL("http://localhost:7000/ussd/send"));

            MtUssdReq mtUssdReq;
            mtUssdReq = createResponses(moUssdReq);

            mtUssdReq.setApplicationId(moUssdReq.getApplicationId());
            mtUssdReq.setPassword("password");
            mtUssdReq.setVersion(moUssdReq.getVersion());
//            mtUssdReq.setChargingAmount("5");
            mtUssdReq.setUssdOperation("1"); //operation codes need to be changed
            mtUssdReq.setSessionId(String.valueOf(System.currentTimeMillis()));

            MtUssdResp mtUssdResp = mtSender.sendUssdRequest(mtUssdReq);
            String statusCode = mtUssdResp.getStatusCode();
            if (StatusCodes.SuccessK.equals(statusCode)) {
                System.out.println("MT USSD message sent successfully");
            } else {
                System.out.println("MT USSD message sending failed with status code [" + statusCode +"] "+mtUssdResp.getStatusDetail());
            }

        } catch (Exception e) {
            LOGGER.log(Level.INFO,"Unexpected error occurred", e);
        }
    }

    private MtUssdReq createResponses(MoUssdReq moUssdReq) {
        MtUssdReq mtUssdReq = new MtUssdReq();

        mtUssdReq.setMessage(processResponse(moUssdReq));
        mtUssdReq.setDestinationAddress(moUssdReq.getSourceAddress());
        mtUssdReq.setEncoding("440");

        return mtUssdReq;
    }

    private String processResponse(MoUssdReq moUssdReq) {
        UssdSession ussdSession = findUssdSession(moUssdReq);

        String response = ussdSession.getResponse(moUssdReq);
        if (ussdSession.isClose()) {
            closeUssdSession(moUssdReq.getSourceAddress());
        }
        return response;
    }

    private UssdSession findUssdSession(MoUssdReq moUssdReq) {
        UssdSession ussdSession = ussdSessionMap.get(moUssdReq.getSourceAddress());
        if (ussdSession == null) {
            ussdSession = new UssdSession();
            ussdSession.setMsisdn(moUssdReq.getSourceAddress());
            ussdSessionMap.put(moUssdReq.getSourceAddress(), ussdSession);
        }
        ussdSession.setLastAccessTime(System.currentTimeMillis());
        return ussdSession;
    }

    private void closeUssdSession(String msisdn) {
        ussdSessionMap.remove(msisdn);
    }

}