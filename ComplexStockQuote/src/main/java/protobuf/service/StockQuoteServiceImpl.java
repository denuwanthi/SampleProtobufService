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
package protobuf.service;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import org.wso2.carbon.protobuf.annotation.ProtobufService;
import protobuf.service.StockQuoteService.SimpleStockQuoteService;
import samples.beans.GetFullQuoteResponse;
import samples.beans.GetMarketActivityResponse;
import samples.beans.GetQuoteResponse;
import samples.beans.PlaceOrder;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@ProtobufService( blocking = true)
public class StockQuoteServiceImpl implements SimpleStockQuoteService.BlockingInterface {

    private final AtomicInteger orderCount = new AtomicInteger();
    @Override
    public StockQuoteService.GetQuoteResponse getQuote(RpcController controller, StockQuoteService.GetQuote request)
            throws ServiceException {
        String symbol = request.getSymbol();

        System.out.println("Stock Quote Request Received : " +symbol);
        GetQuoteResponse quoteResponse = new GetQuoteResponse(symbol);
        StockQuoteService.GetQuoteResponse.Builder responseBuilder = StockQuoteService.GetQuoteResponse.newBuilder();

        responseBuilder.setSymbol(quoteResponse.getSymbol());
        responseBuilder.setLast(quoteResponse.getLast());
        responseBuilder.setLastTradeTimestamp(quoteResponse.getLastTradeTimestamp());
        responseBuilder.setChange(quoteResponse.getChange());
        responseBuilder.setOpen(quoteResponse.getOpen());
        responseBuilder.setHigh(quoteResponse.getHigh());
        responseBuilder.setLow(quoteResponse.getLow());
        responseBuilder.setVolume(quoteResponse.getVolume());
        responseBuilder.setMarketCap(quoteResponse.getMarketCap());
        responseBuilder.setPrevClose(quoteResponse.getPrevClose());
        responseBuilder.setPercentageChange(quoteResponse.getPercentageChange());
        responseBuilder.setEarnings(quoteResponse.getEarnings());
        responseBuilder.setPeRatio(quoteResponse.getPeRatio());
        responseBuilder.setName(quoteResponse.getName());

        StockQuoteService.GetQuoteResponse response = null;
        response = responseBuilder.build();
        System.out.println(new Date() + " " + this.getClass().getName() +
                " :: Generating quote for : " + request.getSymbol());
        return response;
    }

    @Override
    public StockQuoteService.GetFullQuoteResponse getFullQuote(RpcController controller, StockQuoteService.GetFullQuote request)
            throws ServiceException {
        String symbol = request.getSymbol();
        System.out.println("Stock Quote Request Received : " +symbol);

        GetFullQuoteResponse fullQuoteResponse=new GetFullQuoteResponse(symbol);
        StockQuoteService.GetFullQuoteResponse.Builder fullResponseBuilder=StockQuoteService.GetFullQuoteResponse.newBuilder();
        StockQuoteService.GetFullQuoteResponse.TradingDay[] tradingDays=new StockQuoteService.GetFullQuoteResponse.
                TradingDay[fullQuoteResponse.getTradeHistory().length];

        for(int i=0;i<fullQuoteResponse.getTradeHistory().length;i++){
            StockQuoteService.GetFullQuoteResponse.TradingDay.Builder tradingBuilder=StockQuoteService.GetFullQuoteResponse.
                    TradingDay.newBuilder();
            tradingBuilder.setDay(fullQuoteResponse.getTradeHistory()[i].getDay());
            StockQuoteService.GetQuoteResponse.Builder responseBuilder = StockQuoteService.GetQuoteResponse.newBuilder();
            responseBuilder.setSymbol(fullQuoteResponse.getTradeHistory()[i].getQuote().getSymbol());
            responseBuilder.setLast(fullQuoteResponse.getTradeHistory()[i].getQuote().getLast());
            responseBuilder.setLastTradeTimestamp(fullQuoteResponse.getTradeHistory()[i].getQuote().getLastTradeTimestamp());
            responseBuilder.setChange(fullQuoteResponse.getTradeHistory()[i].getQuote().getChange());
            responseBuilder.setOpen(fullQuoteResponse.getTradeHistory()[i].getQuote().getOpen());
            responseBuilder.setHigh(fullQuoteResponse.getTradeHistory()[i].getQuote().getHigh());
            responseBuilder.setLow(fullQuoteResponse.getTradeHistory()[i].getQuote().getLow());
            responseBuilder.setVolume(fullQuoteResponse.getTradeHistory()[i].getQuote().getVolume());
            responseBuilder.setMarketCap(fullQuoteResponse.getTradeHistory()[i].getQuote().getMarketCap());
            responseBuilder.setPrevClose(fullQuoteResponse.getTradeHistory()[i].getQuote().getPrevClose());
            responseBuilder.setPercentageChange(fullQuoteResponse.getTradeHistory()[i].getQuote().getPercentageChange());
            responseBuilder.setEarnings(fullQuoteResponse.getTradeHistory()[i].getQuote().getEarnings());
            responseBuilder.setPeRatio(fullQuoteResponse.getTradeHistory()[i].getQuote().getPeRatio());
            responseBuilder.setName(fullQuoteResponse.getTradeHistory()[i].getQuote().getName());
            StockQuoteService.GetQuoteResponse response = null;
            response = responseBuilder.build();

            tradingBuilder.setQuote(response);
            StockQuoteService.GetFullQuoteResponse.TradingDay tradingDay=null;
            tradingDay=tradingBuilder.build();
            tradingDays[i]=tradingDay;
        }

        for(int i=0;i<tradingDays.length;i++){
            fullResponseBuilder.addTradeHistory(i,tradingDays[i]);
        }

        StockQuoteService.GetFullQuoteResponse getFullQuoteResponse=null;
        getFullQuoteResponse=fullResponseBuilder.build();
        System.out.println(new Date() + " " + this.getClass().getName() +
                " :: Full quote for : " + request.getSymbol());
        return getFullQuoteResponse;
    }

    @Override
    public StockQuoteService.GetMarketActivityResponse getMarketActivity(RpcController controller,
                                                                         StockQuoteService.GetMarketActivity request)
            throws ServiceException {
        StringBuffer sb = new StringBuffer();
        String[] symbols = request.getSymbolList().toArray(new String[request.getSymbolList().size()]);

        GetMarketActivityResponse getMarketActivityResponse=new GetMarketActivityResponse(symbols);
        StockQuoteService.GetMarketActivityResponse.Builder getMarketActivityResponseBuilder=
                StockQuoteService.GetMarketActivityResponse.newBuilder();


        for(int i=0;i<getMarketActivityResponse.getQuotes().length;i++){
            StockQuoteService.GetQuoteResponse.Builder responseBuilder = StockQuoteService.GetQuoteResponse.newBuilder();
            responseBuilder.setSymbol(getMarketActivityResponse.getQuotes()[i].getSymbol());
            responseBuilder.setLast(getMarketActivityResponse.getQuotes()[i].getLast());
            responseBuilder.setLastTradeTimestamp(getMarketActivityResponse.getQuotes()[i].getLastTradeTimestamp());
            responseBuilder.setChange(getMarketActivityResponse.getQuotes()[i].getChange());
            responseBuilder.setOpen(getMarketActivityResponse.getQuotes()[i].getOpen());
            responseBuilder.setHigh(getMarketActivityResponse.getQuotes()[i].getHigh());
            responseBuilder.setLow(getMarketActivityResponse.getQuotes()[i].getLow());
            responseBuilder.setVolume(getMarketActivityResponse.getQuotes()[i].getVolume());
            responseBuilder.setMarketCap(getMarketActivityResponse.getQuotes()[i].getMarketCap());
            responseBuilder.setPrevClose(getMarketActivityResponse.getQuotes()[i].getPrevClose());
            responseBuilder.setPercentageChange(getMarketActivityResponse.getQuotes()[i].getPercentageChange());
            responseBuilder.setEarnings(getMarketActivityResponse.getQuotes()[i].getEarnings());
            responseBuilder.setPeRatio(getMarketActivityResponse.getQuotes()[i].getPeRatio());
            responseBuilder.setName(getMarketActivityResponse.getQuotes()[i].getName());
            StockQuoteService.GetQuoteResponse response = null;
            response = responseBuilder.build();

            getMarketActivityResponseBuilder.addQuotes(i,response);
        }

        //The MarketActivityResponse from the created binary service stub.
        StockQuoteService.GetMarketActivityResponse getProtobufMarketActivityResponse=null;
        getProtobufMarketActivityResponse=getMarketActivityResponseBuilder.build();

        sb.append("[");
        for (int i=0; i<symbols.length; i++) {
            sb.append(symbols[i]);
            if (i < symbols.length-1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        System.out.println(new Date() + " " + this.getClass().getName() +
                " :: Generating Market activity report for : "  + sb.toString());

        return getProtobufMarketActivityResponse;
    }

    @Override
    public StockQuoteService.Void placeOrder(RpcController controller, StockQuoteService.PlaceOrder request)
            throws ServiceException {

        String symbol=request.getSymbol();
        int quantity=request.getQuantity();
        double price=request.getPrice();

        PlaceOrder placeOrder=new PlaceOrder();
        placeOrder.setSymbol(symbol);
        placeOrder.setQuantity(quantity);
        placeOrder.setPrice(price);

        StockQuoteService.PlaceOrder.Builder placeOrderBuilder=StockQuoteService.PlaceOrder.newBuilder();
        placeOrderBuilder.setSymbol(symbol);
        placeOrderBuilder.setQuantity(quantity);
        placeOrderBuilder.setPrice(price);
        StockQuoteService.PlaceOrder placeOrder1=null;
        placeOrder1=placeOrderBuilder.build();


        System.out.println(new Date() + " " + this.getClass().getName() +
                "  :: Accepted order #" + orderCount.incrementAndGet() + " for : " +
                placeOrder1.getQuantity() + " stocks of " + placeOrder1.getSymbol() + " at $ " +
                placeOrder1.getPrice());

        StockQuoteService.Void.Builder simpleVoidBuilder=StockQuoteService.Void.newBuilder();
        //since protobuf does not support methods that does not return anything, we have to create a message named Void.
        StockQuoteService.Void simpleVoid=simpleVoidBuilder.build();
        return simpleVoid;
    }

}
