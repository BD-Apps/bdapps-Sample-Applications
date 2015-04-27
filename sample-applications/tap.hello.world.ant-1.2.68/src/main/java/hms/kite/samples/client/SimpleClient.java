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

import hms.kite.samples.api.EncodingType;
import hms.kite.samples.api.StatusCodes;
import hms.kite.samples.api.sms.MoSmsListener;
import hms.kite.samples.api.sms.SmsRequestSender;
import hms.kite.samples.api.sms.messages.MoSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsReq;
import hms.kite.samples.api.sms.messages.MtSmsResp;

import javax.servlet.ServletConfig;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleClient implements MoSmsListener {

    private final static Logger LOGGER = Logger.getLogger(SimpleClient.class.getName());

    @Override
    public void init(ServletConfig servletConfig) {

    }

    @Override
    public void onReceivedSms(MoSmsReq moSmsReq) {
        try {
            LOGGER.info("Sms Received for generate request : " + moSmsReq);

            SmsRequestSender smsMtSender = new SmsRequestSender(new URL("http://localhost:7000/sms/send"));

            MtSmsReq mtSmsReq;
//            mtSmsReq = createSubmitMultipleSms(moSmsReq);
            mtSmsReq = createSimpleMtSms(moSmsReq);

            mtSmsReq.setApplicationId(moSmsReq.getApplicationId());
            mtSmsReq.setPassword("d3d8c7fb7cd87659a6003e50fb1ba042");
            mtSmsReq.setSourceAddress("mykeyword");// default sender address or aliases
//            mtSmsReq.setVersion(moSmsReq.getVersion());
//            mtSmsReq.setEncoding("0");
//            mtSmsReq.setChargingAmount("5");

            String deliveryReq = moSmsReq.getDeliveryStatusRequest();
            if (deliveryReq != null) {
                if (deliveryReq.equals("1")) {
                    mtSmsReq.setDeliveryStatusRequest("1");
                }
            } else {
                mtSmsReq.setDeliveryStatusRequest("0");
            }

            MtSmsResp mtSmsResp = smsMtSender.sendSmsRequest(mtSmsReq);
            String statusCode = mtSmsResp.getStatusCode();
            String statusDetails = mtSmsResp.getStatusDetail();
            if (StatusCodes.SuccessK.equals(statusCode)) {
                LOGGER.info("MT SMS message successfully sent");
            } else {
                LOGGER.info("MT SMS message sending failed with status code [" + statusCode + "] "+statusDetails);
            }


        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Unexpected error occurred", e);
        }
    }

    private MtSmsReq createSimpleMtSms(MoSmsReq moSmsReq) {
        MtSmsReq mtSmsReq = new MtSmsReq();

        mtSmsReq.setMessage("Welcome to my application");
        List<String> addressList = new ArrayList<String>();
        addressList.add(moSmsReq.getSourceAddress());
        mtSmsReq.setDestinationAddresses(addressList);

        return mtSmsReq;
    }

    private MtSmsReq createSubmitMultipleSms(MoSmsReq moSmsReq) {
        MtSmsReq mtSmsReq = new MtSmsReq();

        mtSmsReq.setMessage("This message will receive to multiple users");
        List<String> addressList = new ArrayList<String>();

        addressList.add("tel:123456789");
        addressList.add("tel:456789123");

        mtSmsReq.setDestinationAddresses(addressList);

        return mtSmsReq;
    }

    private MtSmsReq createBinarySm(MoSmsReq moSmsReq) {
        MtSmsReq mtSmsReq = new MtSmsReq();
        mtSmsReq.setMessage(
                "3000000002010000481c010000000000001c000000000007e000720000000000" +
                        "3c1001c10000000001e010fe03e00000001f000bff8ff8000000f00007ffdffc" +
                        "00000080000fedffdc000000602a0f7fefce0000001fd41e7ff7ee0000000060" +
                        "1cfff7ee00000007801cfff3fe00000004001cfffbfe00000003051cfffbfe00" +
                        "07f800fa9cfffbfe007800000c1c7ffffe00000000300e7ffffc0000000021ce" +
                        "7ffffc00001ffe1e373ffff80007e000000fbffff8001800000003dffff00000" +
                        "00000001efffe0000000000000ffffc00000000fff007fff80000007f000003f" +
                        "ff000000380000001ffe0000000000000007fc0000000000000001f800000000" +
                        "00000000600000");
        mtSmsReq.setEncoding(EncodingType.BINARY.getCode());
        mtSmsReq.setBinaryHeader("060504158a0000");

        List<String> addressList = new ArrayList<String>();
        addressList.add(moSmsReq.getSourceAddress());
        mtSmsReq.setDestinationAddresses(addressList);

        return mtSmsReq;
    }

    private MtSmsReq createFlashSms(MoSmsReq moSmsReq) {
        MtSmsReq mtSmsReq = new MtSmsReq();

        mtSmsReq.setMessage("This is a flash SM");

        List<String> addressList = new ArrayList<String>();
        addressList.add(moSmsReq.getSourceAddress());
        mtSmsReq.setDestinationAddresses(addressList);

        mtSmsReq.setEncoding(EncodingType.FLASH.getCode());

        return mtSmsReq;
    }
}
