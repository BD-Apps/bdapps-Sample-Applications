# mChoice TAP API PHP Library
This is a simple guide on how to use mChoice TAP API PHP Library.


## PHP Library Components
### SMS
   - SmsSnder.php               - To send SMS MT(Mobile Terminated) messages.
   - SmsReceiver.php            - To receive SMS MO(Mobile Originated) messages.
   - SmsDeliveryReport.php      - To receive SMS DR(Delivery Reports) messages.

### USSD
    - MtUssdSender.php          - To send USSD MT messages.
    - MoUssdReceiver.php        - To receive USSD MO messages.

## Using Libraries
- To use  mChoice TAP API PHP library just import the proper library file(sms) in top of your own PHP source file.
    eg:- for ussd applications
    <?php
    include_once 'ussd/MoUssdReceiver.php';
    include_once 'ussd/MtUssdSender.php';
    ?>


## Testing Your Application
Once you finished writing your application you can test that using mChoice TAP Simulator. Simulator will start on port
10001. You can use the URLs listed below as request sending URLs. For more information on TAP Simulator please refer to
the Readme file provided with it.


SMS
: http://localhost:7000/sms/send/
USSD
: http://localhost:7000/ussd/send/

Note:To use these libraries,cURL library should have installed with PHP.