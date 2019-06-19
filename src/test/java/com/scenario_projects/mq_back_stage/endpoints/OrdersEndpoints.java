package com.scenario_projects.mq_back_stage.endpoints;

public class OrdersEndpoints extends BaseEndpoints {
    public static final String CreateOrder = baseURL + "orders";
    public static final String transactionStatus = "https://ropsten.etherscan.io/tx/";

    public static String findUserOrdersByMarketId(int marketId) {
        return baseURL + "orders/user/" + marketId;
    }

    public static String findOrdersToMatchByOrderId(int orderId) {
        return baseURL + "orders/match/" + orderId;
    }

    public static String findOrdersByMarketId(int marketId) {
        return baseURL + "orders/market/" + marketId;
    }

    public static String getOrderByOrderId(int orderId) {
        return baseURL + "orders/order/" + orderId;
    }
}
