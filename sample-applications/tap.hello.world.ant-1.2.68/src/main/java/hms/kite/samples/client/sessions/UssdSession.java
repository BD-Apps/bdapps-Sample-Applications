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
package hms.kite.samples.client.sessions;

import hms.kite.samples.api.ussd.OperationType;
import hms.kite.samples.api.ussd.messages.MoUssdReq;

public class UssdSession {

    private String msisdn;
    private String lastMessage;
    private String operationType;
    private long lastAccessTime;
    private boolean close = false;

    private static final String code = "*213*99#";

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public boolean isClose() {
        return close;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getResponse(MoUssdReq moUssdReq) {

        String message = moUssdReq.getMessage();
        String reMsg = "";
        
        if(lastMessage == null) {
            lastMessage = message;
            if(code.equals(message)) {
                reMsg = "1. Press One\n2. Press two\n3. Press three\n4. Exit";
                operationType = OperationType.MT_CONT.getName();
            } else {
                reMsg = "Invalid service code, Please enter correct one *141#";
                operationType = OperationType.MT_CONT.getName();
                close = true;
            }
        } else if (message.equals("94")) {
            reMsg = "1. Press One\n2. Press two\n3. Press three\n4. Exit";  //optype mt-cont
            operationType = OperationType.MT_CONT.getName();
        } else if (message.equals("1")) {
            reMsg = "You have Pressed 1\n94. Back\n4. Exit";
            operationType = OperationType.MT_CONT.getName();
        } else if (message.equals("2")) {
            reMsg = "You have Pressed 2\n94. Back\n4. Exit";
            operationType = OperationType.MT_CONT.getName();
        } else if (message.equals("3")) {
            reMsg = "You have Pressed 3\n94. Back\n4. Exit";
            operationType = OperationType.MT_CONT.getName();
        } else if (message.equals("4")) {
            reMsg = "Thank you for using sample APP for USSD ";
            operationType = OperationType.MT_FIN.getName();
            close = true;
        } else {
            reMsg = "Invalid Entry\n94. Back\n4. Exit";
            operationType = OperationType.MT_CONT.getName();
        }
        return reMsg;
    }
}