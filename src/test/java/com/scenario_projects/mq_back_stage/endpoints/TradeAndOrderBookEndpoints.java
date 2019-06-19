package com.scenario_projects.mq_back_stage.endpoints;

public class TradeAndOrderBookEndpoints extends BaseEndpoints {

    //Trade endpoint
    public static String getTradeHistoryByMarketId(int marketId) {
        return baseURL + "trade/history/" + marketId;
    }

    //OrderBook endpoint
    public static final String getOrderBookHistoryForAllMarkets = baseURL + "order-book/history";

    public static String getUserTradeHistoryByMarketId(int marketId) {
        return baseURL + "trade/user-history/" + marketId;
    }

    public static String getOrderBookHistoryByMarketId(int marketId) {
        return baseURL + "order-book/history/" + marketId;
    }

}
