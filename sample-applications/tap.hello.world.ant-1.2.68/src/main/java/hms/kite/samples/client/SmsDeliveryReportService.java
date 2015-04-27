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

import hms.kite.samples.api.sms.MoSmsDeliveryReportListener;
import hms.kite.samples.api.sms.messages.MoSmsDeliveryReportReq;

import java.util.logging.Logger;

public class SmsDeliveryReportService implements MoSmsDeliveryReportListener {

    private final static Logger LOGGER = Logger.getLogger(SmsDeliveryReportService.class.getName());

    @Override
    public void onReceivedDeliveryReport(MoSmsDeliveryReportReq moDeliveryReportReq) {

        LOGGER.info("==> Sms delivery report received from TAP : "+ moDeliveryReportReq);

    }
}