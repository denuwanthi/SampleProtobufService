/*
* Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* WSO2 Inc. licenses this file to you under the Apache License,
* Version 2.0 (the "License"); you may not use this file except
* in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*
*/
package samples.beans;

public class GetFullQuoteResponse {

    private static final int COUNT = 1000;
    TradingDay[] tradeHistory = null;

    public GetFullQuoteResponse() {
    }

    public GetFullQuoteResponse(String symbol) {
        tradeHistory = new TradingDay[COUNT];
        for (int i=0; i<COUNT; i++) {
            tradeHistory[i] = new TradingDay(i, new GetQuoteResponse(symbol));
        }
    }

    public TradingDay[] getTradeHistory() {
        return tradeHistory;
    }

    public void setTradeHistory(TradingDay[] tradeHistory) {
        this.tradeHistory = tradeHistory;
    }

}
