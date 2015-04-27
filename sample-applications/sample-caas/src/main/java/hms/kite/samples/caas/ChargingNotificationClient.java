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


import hms.kite.samples.api.caas.CaasNotificationListener;
import hms.kite.samples.api.caas.messages.ChargingNotificationRequest;

import javax.servlet.ServletConfig;
import java.util.logging.Logger;

/**
 * Handle Charging Notification
 */
public class ChargingNotificationClient implements CaasNotificationListener {

    private final static Logger LOGGER = Logger.getLogger(ChargingNotificationClient.class.getName());

    /**
     * Main Initialization
     * @param servletConfig
     */
    @Override
    public void init(ServletConfig servletConfig) {
    }

    /**
     * Process Receive Chraging Notification.
     * @param chargingNotificationReq
     */
    @Override
    public void onReceiveCaasNotification(ChargingNotificationRequest chargingNotificationReq) {
        LOGGER.info("\nCaas Charging Notification Received for generate request ====>>: " + chargingNotificationReq.toString());
    }

}
