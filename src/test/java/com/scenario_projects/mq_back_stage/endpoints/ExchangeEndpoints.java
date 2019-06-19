package com.scenario_projects.mq_back_stage.endpoints;

public class ExchangeEndpoints extends BaseEndpoints {

    public static final String createExchangeOrder = baseURL + "exchange";
    public static final String handleExchangeOrderTransactionHash = baseURL + "exchange/tx";

    public static String getExchangeOrderDataByExchangeOrderId(int exchangeOrderId) {
        return baseURL + "exchange/order/" + exchangeOrderId;
    }

    public static String findExchangeTokenDataByTokenName(String tokenName) {
        return baseURL + "exchange/token?token=" + tokenName;
    }

    public static String getExchangeTokenDataByTokenId(int tokenId) {
        return baseURL + "exchange/token/" + tokenId;
    }

    public static String getExchangeOrderTokenDataByTokenId(int tokenId) {
        return baseURL + "exchange/order-token/" + tokenId;
    }

    public static String findExchangeOrdersByUserId(int userId) {
        return baseURL + "exchange/user?userId=" + userId;
    }
}
