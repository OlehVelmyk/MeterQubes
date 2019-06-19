package com.scenario_projects.mq_back_stage.dataProvider;

public class ExchangeOrderId {
    private static int exchangeOrderID;

    public static int getExchangeOrderID() {
        return exchangeOrderID;
    }

    public void setExchangeOrderID(int exchangeOrderID) {
        ExchangeOrderId.exchangeOrderID = exchangeOrderID;
    }
}
