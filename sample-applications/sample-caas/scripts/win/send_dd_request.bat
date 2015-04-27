@echo off
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::   (C) Copyright 2010-2013 hSenid Mobile Solutions (Pvt) Limited
::   All Rights Reserved.
::
::   These materials are unpublished, proprietary, confidential source code of
::   hSenid Mobile Solutions (Pvt) Limited and constitute a TRADE SECRET
::   of hSenid Mobile Solutions (Pvt) Limited.
::
::   hSenid Mobile Solutions (Pvt) Limited retains all title to and intellectual
::   property rights in these materials.
::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::   Description  : Send Direct Debit Request. (Payment Instrument - Mobile Account)
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

set DD_URL="http://localhost:7000/caas/direct/debit"
set APP_ID="APP_000001"
set PASSWORD="8edb0c038b3295fdd271de"
set SUBSCRIBER_ID="tel:8801866742387"
set PAYMENT_INS_NAME="Mobile Account"
set ACCOUNT_ID="741258963"
set AMOUNT="500.00"
set CURRENCY="BDT"
set EXTERNAL_TRX_ID="456123"


:: Send Direct Debit request with above parameters
cd ..\..\target
echo "########################### Sending Direct Debit request ###################################"
java -jar sample-caas.jar direct-debit-mobile-acc %DD_URL% %APP_ID% %PASSWORD% %SUBSCRIBER_ID% %PAYMENT_INS_NAME% %ACCOUNT_ID% %CURRENCY% %AMOUNT% %EXTERNAL_TRX_ID%
cd ..\scripts\win