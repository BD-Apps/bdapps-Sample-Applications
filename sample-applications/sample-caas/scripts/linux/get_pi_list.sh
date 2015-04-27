#########################################################################################
#   (C) Copyright 2010-2013 hSenid Mobile Solutions (Pvt) Limited
#   All Rights Reserved.
#
#   These materials are unpublished, proprietary, confidential source code of
#   hSenid Mobile Solutions (Pvt) Limited and constitute a TRADE SECRET
#   of hSenid Mobile Solutions (Pvt) Limited.
#
#   hSenid Mobile Solutions (Pvt) Limited retains all title to and intellectual
#   property rights in these materials.
#
#########################################################################################
#   Description  : Get Payment Instrument List.
#########################################################################################

#!/bin/bash
PI_LIST_URL="http://localhost:7000/caas/list/pi"
APP_ID="APP_000001"
PASSWORD="8edb0c038b3295fdd271de"
SUBSCRIBER_ID="8801866742387"
TYPE="all"

cd ../../target
echo "########################### Sending Payment Instrument List Request ###################################"
java -jar sample-caas.jar pay-ins-list $PI_LIST_URL $APP_ID $PASSWORD $SUBSCRIBER_ID $TYPE