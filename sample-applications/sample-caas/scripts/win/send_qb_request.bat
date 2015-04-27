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
::   Description  : Send Query Balance Request.
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

set QUERY_BALANCE_URL="http://localhost:7000/caas/balance/query"
set APP_ID="APP_000001"
set PASSWORD="8edb0c038b3295fdd271de"
set SUBSCRIBER_ID="tel:8801866742387"
set ACCOUNT_ID="741258963"
set PAYMENT_INS_NAME="Mobile Account"
set CURRENCY="BDT"

cd ..\..\target
echo "########################### Sending Query Balance Request ###################################"
java -jar sample-caas.jar query-balance %QUERY_BALANCE_URL% %APP_ID% %PASSWORD% %SUBSCRIBER_ID% %ACCOUNT_ID% %PAYMENT_INS_NAME% %CURRENCY%
cd ..\scripts\win