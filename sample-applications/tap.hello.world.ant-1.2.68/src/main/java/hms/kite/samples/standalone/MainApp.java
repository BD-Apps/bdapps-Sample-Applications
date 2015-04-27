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
package hms.kite.samples.standalone;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.logging.Logger;

public class MainApp {

    private final static Logger LOGGER = Logger.getLogger(MainApp.class.getName());

    public static void main(String[] args) throws Exception {
        Server server = new Server(5555); //todo : malith make this configurable
		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath("/");
		webapp.setWar("../lib/tap-hello-world-app.war");
		server.setHandler(webapp);
        LOGGER.info("Application starting ...");
		server.start();
		server.join();
    }
}
