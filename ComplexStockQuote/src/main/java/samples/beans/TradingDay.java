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

public class TradingDay {
    int day = 0;
    GetQuoteResponse quote = null;

    public TradingDay(int day, GetQuoteResponse quote) {
        this.day = day;
        this.quote = quote;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public GetQuoteResponse getQuote() {
        return quote;
    }

    public void setQuote(GetQuoteResponse quote) {
        this.quote = quote;
    }
}